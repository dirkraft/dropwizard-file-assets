
dropwizard-file-assets
======================

I just want to serve plain files in my Dropwizard app. That's all this does; let you serve files from your file system.
It has about 0 extra features.

[![Build Status](https://travis-ci.org/dirkraft/dropwizard-file-assets.svg?branch=master)](https://travis-ci.org/dirkraft/dropwizard-file-assets)



## Dependency ##

Add the dependency from Maven central.


### Maven ###
Unverified (I use gradle these days)

```xml
<dependency>
	<groupId>com.github.dirkraft.dropwizard</groupId>
	<artifactId>dropwizard-file-assets</artifactId>
	<version>0.0.1</version>
</dependency>

```


### Gradle ###
```gradle
apply plugin: 'maven'

repositories {
    mavenCentral()
}

dependencies {
    compile 'com.github.dirkraft.dropwizard:dropwizard-file-assets:0.0.1'
}
```




## Integration ##

This thing was mostly spurred on by development (I use IntelliJ for everything). I want to be able to refresh my browser
rather than have to constantly restart dropwizard. I usually end up with two main methods: one for production and one
for development. But there's no reason you couldn't also use this in production to serve files from the file system.

Serving out of the classpath the traditional way with standard
[dropwizard-assets](http://dropwizard.io/manual/core.html#serving-assets).
Perhaps you have bundled your static assets in your jar.

#### Standard Dropwizard Assets Bundle ####

```java
@Override
public void initialize(Bootstrap<Configuration> bootstrap) {
    bootstrap.addBundle(new AssetsBundle("/web", "/", "index.html"));
}
```
See a slightly more complete example in [StandardAssetsBundleApp.java](https://github.com/dirkraft/dropwizard-file-assets/blob/master/src/test/java/com/github/dirkraft/dropwizard/fileassets/StandardAssetsBundleApp.java)

Serving directly from the filesystem thanks to this small module, for instant browser refreshability.

#### Dropwizard File Assets Bundle ####

```java
@Override
public void initialize(Bootstrap<Configuration> bootstrap) {
    // This is a relative path, relative to the working directory. If you run this main method in IntelliJ,
    // by default the working directory is the project root directory.
    bootstrap.addBundle(new FileAssetsBundle("src/test/resources/web", "/", "index.html"));
}
```
See a slightly more complete example in [FileAssetsBundleApp.java](https://github.com/dirkraft/dropwizard-file-assets/blob/master/src/test/java/com/github/dirkraft/dropwizard/fileassets/FileAssetsBundleApp.java)

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
