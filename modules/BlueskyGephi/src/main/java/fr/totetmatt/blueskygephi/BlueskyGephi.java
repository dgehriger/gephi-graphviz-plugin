package fr.totetmatt.blueskygephi;

import fr.totetmatt.blueskygephi.atproto.AtClient;
import fr.totetmatt.blueskygephi.atproto.response.AppBskyGraphGetFollowers;
import fr.totetmatt.blueskygephi.atproto.response.AppBskyGraphGetFollows;
import fr.totetmatt.blueskygephi.atproto.response.AppBskyGraphGetList;
import fr.totetmatt.blueskygephi.atproto.response.common.Identity;
import java.awt.Color;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.gephi.graph.api.Edge;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.Node;
import org.gephi.project.api.Project;
import org.gephi.project.api.ProjectController;
import org.gephi.utils.progress.Progress;
import org.gephi.utils.progress.ProgressTicket;
import org.gephi.utils.progress.ProgressTicketProvider;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.NbPreferences;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author totetmatt
 */
@ServiceProvider(service = BlueskyGephi.class)
public class BlueskyGephi {

    protected static final Logger logger = Logger.getLogger(BlueskyGephi.class.getName());
    private final static String NBPREF_BSKY_HANDLE = "bsky.handle";
    private final static String NBPREF_BSKY_PASSWORD = "bsky.password";
    private final static String NBPREF_QUERY = "query";
    private final static String NBPREF_QUERY_ISFOLLOWERSACTIVE = "query.isFollowersActive";
    private final static String NBPREF_QUERY_ISFOLLOWSACTIVE = "query.isFollowsActive";
    private final static String NBPREF_QUERY_ISDEEPSEARCH = "query.isDeepSearch";
    private final static String NBPREF_QUERY_ISLIMITCRAWLACTIVE = "query.isLimitCrawlActive";
    private final static String NBPREF_QUERY_LIMITCRAWL = "query.limitCrawl";

    private final Preferences nbPref = NbPreferences.forModule(BlueskyGephi.class);
    // If ATProto get released and decentralized, this will change to adapt to other instances
    final private AtClient client = new AtClient("bsky.social");
    private GraphModel graphModel;

    public BlueskyGephi() {
        initProjectAndWorkspace();

    }

    private void initProjectAndWorkspace() {
        ProjectController projectController = Lookup.getDefault().lookup(ProjectController.class);
        Project currentProject = projectController.getCurrentProject();
        if (currentProject == null) {
            projectController.newProject();
        }
    }

    public boolean connect(String handle, String password) {
        nbPref.put(NBPREF_BSKY_HANDLE, handle);
        nbPref.put(NBPREF_BSKY_PASSWORD, password);

        return client.comAtprotoServerCreateSession(handle, password);

    }

    public String getHandle() {
        return nbPref.get(NBPREF_BSKY_HANDLE, "");
    }

    public String getPassword() {
        return nbPref.get(NBPREF_BSKY_PASSWORD, "");
    }

    public void setQuery(String query) {
        nbPref.put(NBPREF_QUERY, query);
    }

    public String getQuery() {
        return nbPref.get(NBPREF_QUERY, "");
    }

    public void setIsFollowersActive(boolean isFollowersActive) {
        nbPref.putBoolean(NBPREF_QUERY_ISFOLLOWERSACTIVE, isFollowersActive);
    }

    public boolean getIsFollowersActive() {
        return nbPref.getBoolean(NBPREF_QUERY_ISFOLLOWERSACTIVE, true);
    }

    public void setIsFollowsActive(boolean isFollowsActive) {
        nbPref.putBoolean(NBPREF_QUERY_ISFOLLOWSACTIVE, isFollowsActive);
    }

    public boolean getIsFollowsActive() {
        return nbPref.getBoolean(NBPREF_QUERY_ISFOLLOWSACTIVE, true);
    }

    public void setIsDeepSearch(boolean setIsDeepSearch) {
        nbPref.putBoolean(NBPREF_QUERY_ISDEEPSEARCH, setIsDeepSearch);
    }

    public boolean getIsDeepSearch() {
        return nbPref.getBoolean(NBPREF_QUERY_ISDEEPSEARCH, true);
    }

    public void setIsLimitCrawlActive(boolean isLimitCrawlActive) {
        nbPref.putBoolean(NBPREF_QUERY_ISLIMITCRAWLACTIVE, isLimitCrawlActive);
    }

    public boolean getIsLimitCrawlActive() {
        return nbPref.getBoolean(NBPREF_QUERY_ISLIMITCRAWLACTIVE, true);
    }

    public void setLimitCrawl(int limitCrawl) {
        nbPref.putInt(NBPREF_QUERY_LIMITCRAWL, limitCrawl);
    }

    public int getLimitCrawl() {
        return nbPref.getInt(NBPREF_QUERY_LIMITCRAWL, 50);
    }

    private Node createNode(Identity i) {

        Node node = graphModel.getGraph().getNode(i.getDid());
        if (node == null) {
            node = graphModel.factory().newNode(i.getDid());
            node.setLabel(i.getHandle());
            node.setAttribute("Description", i.getDescription());
            node.setSize(10);
            node.setColor(Color.GRAY);
            node.setX((float) ((0.01 + Math.random()) * 1000) - 500);
            node.setY((float) ((0.01 + Math.random()) * 1000) - 500);
            graphModel.getGraph().addNode(node);
        }

        return node;
    }

    private Edge createEdge(Node source, Node target) {

        Edge edge = graphModel.getGraph().getEdge(source, target);
        if (edge == null) {
            edge = graphModel.factory().newEdge(source, target, true);
            edge.setWeight(1.0);
            edge.setColor(Color.GRAY);
            graphModel.getGraph().addEdge(edge);
        }

        return edge;
    }

    private void fetchFollowerFollowsFromActor(String actor, List<String> listInit, boolean isFollowsActive, boolean isFollowersActive, boolean isDeepSearch) {
        // To avoid locking Gephi UI
        Thread t = new Thread() {
            private ProgressTicket progressTicket;
            Set<String> foaf = new HashSet<>();

            private void process(String actor, boolean isDeepSearch, Optional<Integer> limitCrawl) {

                try {
                    if (isFollowsActive) {
                        List<AppBskyGraphGetFollows> responses = client.appBskyGraphGetFollows(actor, limitCrawl);

                        for (var response : responses) {
                            graphModel.getGraph().writeLock();
                            Identity subject = response.getSubject();
                            Node source = createNode(subject);
                            source.setColor(Color.GREEN);
                            for (var follow : response.getFollows()) {
                                if (isDeepSearch) {
                                    foaf.add(follow.getDid());
                                }
                                Node target = createNode(follow);
                                createEdge(source, target);
                            }
                            graphModel.getGraph().writeUnlock();

                        }

                    }

                    if (isFollowersActive) {
                        List<AppBskyGraphGetFollowers> responses = client.appBskyGraphGetFollowers(actor, limitCrawl);

                        for (var response : responses) {
                            graphModel.getGraph().writeLock();
                            Identity subject = response.getSubject();
                            Node target = createNode(subject);
                            target.setColor(Color.GREEN);
                            for (var follower : response.getFollowers()) {
                                if (isDeepSearch) {
                                    foaf.add(follower.getDid());
                                }
                                Node source = createNode(follower);
                                createEdge(source, target);
                            }
                            graphModel.getGraph().writeUnlock();
                        }

                    }
                } catch (Exception e) {
                    Exceptions.printStackTrace(e);
                } finally {
                }
            }

            @Override
            public void run() {

                if (actor != null) {
                    this.setName("[Bsky] fetching" + actor);
                } else {
                    this.setName("[Bsky] fetching List");
                }
                progressTicket = Lookup.getDefault()
                        .lookup(ProgressTicketProvider.class)
                        .createTicket(this.getName(), () -> {
                            interrupt();
                            Progress.finish(progressTicket);
                            return true;
                        });
                Progress.start(progressTicket);
                Progress.switchToIndeterminate(progressTicket);

                if (listInit != null) {
                    this.foaf.addAll(listInit);
                }
                if (actor != null) {
                    process(actor, isDeepSearch, Optional.empty());
                }
                if (listInit != null || isDeepSearch) {
                    Progress.switchToDeterminate(progressTicket, foaf.size());
                    for (var foafActor : foaf) {
                        Progress.setDisplayName(progressTicket, "[Bsky] fetching " + actor + " n+1 > " + foafActor);
                        if (getIsLimitCrawlActive()) {
                            process(foafActor, false, Optional.of(getLimitCrawl()));
                        } else {
                            process(foafActor, false, Optional.empty());
                        }
                        Progress.progress(progressTicket);

                    }
                }
                Progress.finish(progressTicket);
            }
        };
        t.start();

    }

    private Stream<String> manageList(String listId) {
        List<AppBskyGraphGetList> list = client.appBskyGraphGetList(listId);
        return list.stream().flatMap(x -> x.getItems().stream().map(y -> y.getSubject().getDid()));

    }

    private void initGraphTable() {
        // Create necessary model for the graph entities
        if (!graphModel.getNodeTable().hasColumn("Description")) {
            graphModel.getNodeTable().addColumn("Description", String.class);
        }
    }

    public void fetchFollowerFollowsFromActors(List<String> actors, boolean isFollowsActive, boolean isFollowersActive, boolean isBlocksActive) {
        graphModel = Lookup.getDefault().lookup(GraphController.class).getGraphModel();
        initGraphTable();
        actors.stream().forEach(actor -> fetchFollowerFollowsFromActor(actor, null, isFollowsActive, isFollowersActive, getIsDeepSearch()));
    }

    public void fetchFollowerFollowsFromActors(List<String> actors) {
        graphModel = Lookup.getDefault().lookup(GraphController.class).getGraphModel();
        initGraphTable();
        actors
                .stream()
                .filter(x -> !x.contains("app.bsky.graph.list"))
                .forEach(actor -> fetchFollowerFollowsFromActor(actor, null, getIsFollowsActive(), getIsFollowersActive(), getIsDeepSearch()));

        List<String> listActor = actors
                .stream()
                .filter(x -> x.contains("app.bsky.graph.list"))
                .flatMap(this::manageList)
                .collect(Collectors.toList());

        fetchFollowerFollowsFromActor(null, listActor, getIsFollowsActive(), getIsFollowersActive(), getIsDeepSearch());

    }
}
