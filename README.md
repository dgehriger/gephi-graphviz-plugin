# Gephi Plugins

This repository is an out-of-the-box development environment for Gephi plugins. Gephi plugins are implemented in Java and can extend [Gephi](https://gephi.org) in many different ways, adding or improving features. Getting started is easy with this repository but also checkout the [Bootcamp](https://github.com/gephi/gephi-plugins-bootcamp) for examples of plugins you can create. 

## Get started

### Requirements

Developing Gephi plugins requires JDK 11 or later and [Maven](http://maven.apache.org/).

### Create a plugin

The creation of a new plugin is simple thanks to our custom [Gephi Maven Plugin](https://github.com/gephi/gephi-maven-plugin). The `generate` goal asks a few questions and then configures everything for you.

- Fork and checkout the latest version of this repository:

        git clone git@github.com:username/gephi-plugins.git
- Run the following command and answer the questions:

        mvn org.gephi:gephi-maven-plugin:generate

This is an example of what this process will ask:

        Name of organization (e.g. my.company): org.foo
        Name of artifact (e.g my-plugin): my-plugin
        Version (e.g. 1.0.0): 1.0.0
        Directory name (e.g MyPlugin): MyPlugin
        Branding name (e.g My Plugin): My Plugin
        Category (e.g Layout, Filter, etc.): Layout
        Author: My Name
        Author email (optional):
        Author URL (optional):
        License (e.g Apache 2.0): Apache 2.0
        Short description (i.e. one sentence): Plugin catch-phrase
        Long description (i.e multiple sentences): Plugin features are great
        Would you like to add a README.md file (yes|no): yes

The plugin configuration is created. Now you can (in any order):

- Add some Java code in the `src/main/java` folder of your plugin
- Add some resources (e.g. Bundle.properties, images) into the `src/main/resources/` folder of your plugin
- Change the version, author or license information into the `pom.xml` file, which is in your plugin folder
- Edit the description or category details into the `src/main/nbm/manifest.mf` file in your plugin folder 

### Build a plugin

Run the following command to compile and build your plugin:

       mvn clean package

In addition to compiling and building the JAR and NBM, this command uses the `Gephi Maven Plugin` to verify the plugin's configuration. In case something is wrong it will fail and indicate the reason.

### Run Gephi with plugin

Run the following command to run Gephi with your plugin pre-installed. Make sure to run `mvn package` beforehand to rebuild.

       mvn org.gephi:gephi-maven-plugin:run

In Gephi, when you navigate to `Tools` > `Plugins` you should see your plugin listed in `Installed`.

## Submit a plugin

Submitting a Gephi plugin for approval is a simple process based on GitHub's [pull request](https://help.github.com/articles/using-pull-requests/) mechanism.

- First, make sure you're working on a fork of [gephi-plugins](https://github.com/gephi/gephi-plugins). You can check that by running `git remote -v` and look at the url, it should contain your GitHub username, for example `git@github.com:username/gephi-plugins.git`.

- Add and commit your work. It's recommended to keep your fork synced with the upstream repository, as explained [here](https://help.github.com/articles/syncing-a-fork/), so you can run `git merge upstream/master` beforehand.

- Push your commits to your fork with `git push origin master`.

- Navigate to your fork's URL and create a pull request. Select `master-forge` instead of `master` as base branch.

- Submit your pull request. If possible, before you submit make sure to [enable edits from maintainers](https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/working-with-forks/allowing-changes-to-a-pull-request-branch-created-from-a-fork) so that we can help you tweak the code and configuration when needed.

## Update a plugin

Updating a Gephi plugin has the same process as submitting it for the first time. Don't forget to merge from upstream's master branch.

Also, make sure to increment the version number of your plugin in your module's `pom.xml` file before submitting.

## IDE Support

### Netbeans IDE

- Start Netbeans and go to `File` and then `Open Project`. Navigate to your fork repository, Netbeans automatically recognizes it as Maven project. 
- Each plugin module can be found in the `Modules` folder.

To run Gephi with your plugin pre-installed, right click on the `gephi-plugins` project and select `Run`.

To debug Gephi with your plugin, right click on the `gephi-plugins` project and select `Debug`.

### IntelliJ IDEA

- Start IntelliJ and `Open` the project by navigating to your fork repository. IntelliJ may prompt you to import the Maven project, select yes.

To run Gephi with your plugin pre-installed when you click `Run`, create a `Maven` run configuration and enter `org.gephi:gephi-maven-plugin:run` in the command field. The working directory is simply the current project directory.

To debug Gephi with your plugin, create a `Remote` configuration and switch the `Debugger mode` option to `Listen`. Then create a `Maven` run configuration like above but add `-Drun.params.debug="-J-Xdebug -J-Xnoagent -J-Xrunjdwp:transport=dt_socket,suspend=n,server=n,address=5005"` into the `Runner` > `VM Options` field. Then, go to the `Run` menu and first run debug with the remote configuration and then only run debug with the Maven configuration.

When you make changes to your plugin and want to run Gephi with the changes, make sure to build the `gephi-plugins` root module, and not only your module. Otherwise, your changes won't be reflected.

## FAQ

#### What kind of plugins can I create?

Gephi can be extended in many ways but the major categories are `Layout`, `Export`, `Import`, `Data Laboratory`, `Filter`, `Generator`, `Metric`, `Preview`, `Tool` and `Appearance`. A good way to start is to look at examples with the [bootcamp](https://github.com/gephi/gephi-plugins-bootcamp).

#### In which language can plugins be created?

Plugins can use any JVM languages (e.g. Scala, Python, Groovy) but the default option is Java. 

#### Can native libraries be used?

Yes, native libraries can be used in modules.

#### How is this repository structured?

The `modules` folder is where plugin modules go. Each plugin is defined in a single folder in this directory. A plugin can be composed of multiple modules (it's called a suite then) but usually one is enough to do what you want.

A Maven pom can inherit configurations from a parent and that is something we use to keep each plugin's pom very simple. Notice that each plugin's pom (i.e. the `pom.xml` file in the plugin folder) has a `<parent>` defined.

The `pom.xml` file at the root folder makes everything fit together and notably lists the modules. No need to change anything there besides the `<modules>...</modules>` list.

#### How are the manifest settings defined?

There are two options. The first option is what the `generate` task does: it puts entries `OpenIDE-Module-Short-Description`, `OpenIDE-Module-Long-Description`, `OpenIDE-Module-Display-Category` and `OpenIDE-Module-Name` into the `src/main/nbm/manifest.mf` file. The second option sets a `OpenIDE-Module-Localizing-Bundle` entry into the `manifest.mf` so values are defined elsewhere in `Bundle.properties` file. The value is then simply the path to the file (e.g. `OpenIDE-Module-Localizing-Bundle: org/project/Bundle.properties`).

The second option is preferable when the short or long description have too many characters as the manifest format is pretty restrictive.  

#### How to add a new module?

This applies for suite plugins with multiple modules. Besides creating the module folder, edit the `pom.xml` file and add the folder path to `<modules>`, like in this example:

```
    <!-- List of modules -->
    <modules>
        <!-- Add here the paths of all modules (e.g. <module>modules/MyModule</module>) -->
        <module>modules/ExampleModule</module> 
    </modules>
```

#### Where are dependencies configured?

Dependencies are configured in the `<dependencies>` section in the plugin folder's `pom.xml`. Each dependency has a `groupId`, an `artifactId` and a `version`. There are three types of dependencies a plugin can have: an external library, a Gephi module or a Netbeans module.

The list of Gephi and Netbeans dependencies one can use can be found in the parent POM, which you can browse [here](https://github.com/gephi/gephi-plugins/blob/6136ba8427349aa16c4f4b94265267fc3de0e767/modules/pom.xml#L76). All possible dependencies are listed in the `<dependencyManagement>` section. Because each plugin module already inherits the version from this parent pom, it can be omitted. For instance, this is how a plugin depends on `GraphAPI` and Netbeans's `Lookup`.

```
<dependencies>
     <dependency>
         <groupId>org.netbeans.api</groupId>
         <artifactId>org-openide-util-lookup</artifactId>
     </dependency>
     <dependency>
         <groupId>org.gephi</groupId>
         <artifactId>graph-api</artifactId>
    </dependency>
</dependencies>
```

#### How to best write unit tests for my plugin?

It's recommended to use unit-testing to ensure a reliable plugin.

A JUnit4 dependency can be added to your module's `pom.xml`

```
<dependency>
    <groupId>org.netbeans.api</groupId>
    <artifactId>org-netbeans-modules-nbjunit</artifactId>
    <scope>test</scope>
</dependency>
```

Those tests will automatically be run when your plugin is built.

#### What are public packages for?

This applies for suite plugins with multiple modules. A module should declare the packages it wants to make accessible to other modules. For instance, if a module `B` depends on the class `my.org.project.ExampleController` defined in a module `A`, the `A` module should declare `my.org.project` as public package.

Public packages are configured in the module's `pom.xml` file. Edit the `<publicPackages>` entry. Example:

```
<publicPackages>
    <publicPackage>my.org.project</publicPackage>
</publicPackages>
```

#### What is the difference between plugin and module?

It's the same thing. We say module because Gephi is a modular application and is composed of many independent modules. Plugins also are modules but we call them plugin because they aren't in the _core_ Gephi.

#### When running the plugin in Netbeans I get an error "Running standalone modules or suites requires..."

This error appears when you try to run a module. To run Gephi with your plugin you need to run the `gephi-plugins` project, not your module.

## Best practices

### Code quality

- Write your code in English, so it can be best reviewed and maintained.