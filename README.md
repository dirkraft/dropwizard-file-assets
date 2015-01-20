dropwizard-file-assets
======================

I just want to serve plain files in my Dropwizard app. That's all this does; let you serve files from your file system.
It has about 0 extra features.

I'm actually working on something else, but thought I would need this repeatedly so here it is.




## Dependency ##

Add the dependency which is currently only hosted on Sonatype's gracious snapshot repository. Maybe I will push to maven
central someday, but for now I've loaded enough crapware on there.


### Maven ###
Unverified (I use gradle these days)

Add the Sonatype snapshot repo. See
[stackoverflow](http://stackoverflow.com/questions/7715321/how-to-download-snapshot-version-from-maven-snapshot-repository)

```xml
<dependency>
	<groupId>com.github.dirkraft.dropwizard</groupId>
	<artifactId>dropwizard-file-assets</artifactId>
	<version>0.0.1-SNAPSHOT</version>
</dependency>

```


### Gradle ###
```gradle
apply plugin: 'maven'

repositories {
    maven {
        url 'https://oss.sonatype.org/content/repositories/snapshots/'
    }
}

dependencies {
    compile 'com.github.dirkraft.dropwizard:dropwizard-file-assets:0.0.1-SNAPSHOT'
}
```




## Integration ##

This thing was mostly spurred on by development (I use IntelliJ for everything). I want to be able to refresh my browser
rather than have to constantly restart dropwizard. I usually end up with two main methods: one for production and one
for development. But there's no reason you couldn't also use this in production to serve files from the file system.

Serving out of the classpath the traditional way with standard
[dropwizard-assets](http://dropwizard.io/manual/core.html#serving-assets).
Perhaps you have bundled your static assets in your jar.

```java
@Override
public void initialize(Bootstrap<Configuration> bootstrap) {
    bootstrap.addBundle(new AssetsBundle("/web", "/", "index.html"));
}
```
See a slightly more complete example in TODO

Serving directly from the filesystem thanks to this small module, for instant browser refreshability.

```java
@Override
public void initialize(Bootstrap<Configuration> bootstrap) {
    // This is a relative path, relative to the working directory. If you run this main method in IntelliJ,
    // by default the working directory is the project root directory.
    bootstrap.addBundle(new FileAssetsBundle("src/test/resources/web", "/", "index.html"));
}
```
See a slightly more complete example in TODO

If you wanted, you could map it into your custom Configuration, but I don't usually care for that stuff until much
later, if ever. If you do want that, you should check out
[dropwizard-configurable-assets-bundle](https://github.com/bazaarvoice/dropwizard-configurable-assets-bundle).
I just found it unwieldy to get started, but once you're going you might think about switching over to that for more of
its features.


### Troubleshooting ###

On startup if you have not restricted your logging too much, you might see a message from the FileAssetsBundle
explaining what it's doing.

    INFO  [2015-01-20 03:27:32,380] com.github.dirkraft.dropwizard.fileassets.FileAssetsBundle: Registering FileAssetBundle with servlet name assets for request path /* from file system /Users/dirkraft/src/dropwizard-file-assets/src/test/resources/web

You can also enable debug level logging on `com.github.dirkraft.dropwizard.fileassets.FileAssetServlet`, which will
then log the resolved absolute path to every resource request received, right before it attempts to read the file data.



### License ###
dropwizard-file-assets is released under the Apache 2.0 license. It is a slight modification on the original
[dropwizard-assets module](https://github.com/dropwizard/dropwizard/tree/master/dropwizard-assets) and so maintains
dropwizard's licensing scheme.
