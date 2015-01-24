/**
 * Copyright 2010-2013 Coda Hale and Yammer, Inc.
 * Copyright 2015 Jason Dunkelberger (a.k.a. dirkraft)
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.github.dirkraft.dropwizard.fileassets;

import com.google.common.base.Charsets;
import io.dropwizard.Bundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Paths;

/**
 * A bundle for serving static asset files from the classpath.
 */
public class FileAssetsBundle implements Bundle {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileAssetsBundle.class);

    private static final String DEFAULT_ASSETS_NAME = "file-assets";
    private static final String DEFAULT_INDEX_FILE = "index.htm";
    private static final String DEFAULT_PATH = "/assets";

    private final String filePath;
    private final String uriPath;
    private final String indexFile;
    private final String servletName;

    /**
     * Creates a new AssetsBundle which serves up static assets from
     * {@code src/main/resources/assets/*} as {@code /assets/*}.
     *
     * @see FileAssetsBundle#FileAssetsBundle(String, String, String)
     */
    public FileAssetsBundle() {
        this(DEFAULT_PATH, DEFAULT_PATH, DEFAULT_INDEX_FILE, DEFAULT_ASSETS_NAME);
    }

    /**
     * Creates a new AssetsBundle which will configure the application to serve the static files
     * located in {@code src/main/resources/${path}} as {@code /${path}}. For example, given a
     * {@code path} of {@code "/assets"}, {@code src/main/resources/assets/example.js} would be
     * served up from {@code /assets/example.js}.
     *
     * @param path the classpath and URI root of the static asset files
     * @see FileAssetsBundle#FileAssetsBundle(String, String, String)
     */
    public FileAssetsBundle(String path) {
        this(path, path, DEFAULT_INDEX_FILE, DEFAULT_ASSETS_NAME);
    }

    /**
     * Creates a new AssetsBundle which will configure the application to serve the static files
     * located in {@code src/main/resources/${resourcePath}} as {@code /${uriPath}}. For example, given a
     * {@code resourcePath} of {@code "/assets"} and a uriPath of {@code "/js"},
     * {@code src/main/resources/assets/example.js} would be served up from {@code /js/example.js}.
     *
     * @param filePath the resource path (in the classpath) of the static asset files
     * @param uriPath  the uri path for the static asset files
     * @see FileAssetsBundle#FileAssetsBundle(String, String, String)
     */
    public FileAssetsBundle(String filePath, String uriPath) {
        this(filePath, uriPath, DEFAULT_INDEX_FILE, DEFAULT_ASSETS_NAME);
    }

    /**
     * Creates a new AssetsBundle which will configure the application to serve the static files
     * located in {@code src/main/resources/${resourcePath}} as {@code /${uriPath}}. If no file name is
     * in ${uriPath}, ${indexFile} is appended before serving. For example, given a
     * {@code resourcePath} of {@code "/assets"} and a uriPath of {@code "/js"},
     * {@code src/main/resources/assets/example.js} would be served up from {@code /js/example.js}.
     *
     * @param filePath  the resource path (in the classpath) of the static asset files
     * @param uriPath   the uri path for the static asset files
     * @param indexFile the name of the index file to use
     */
    public FileAssetsBundle(String filePath, String uriPath, String indexFile) {
        this(filePath, uriPath, indexFile, DEFAULT_ASSETS_NAME);
    }

    /**
     * Creates a new AssetsBundle which will configure the application to serve the static files
     * located in {@code src/main/resources/${resourcePath}} as {@code /${uriPath}}. If no file name is
     * in ${uriPath}, ${indexFile} is appended before serving. For example, given a
     * {@code resourcePath} of {@code "/assets"} and a uriPath of {@code "/js"},
     * {@code src/main/resources/assets/example.js} would be served up from {@code /js/example.js}.
     *
     * @param filePath    the resource path (in the classpath) of the static asset files
     * @param uriPath     the uri path for the static asset files
     * @param indexFile   the name of the index file to use
     * @param servletName the name of servlet mapping used for this assets bundle
     */
    public FileAssetsBundle(String filePath, String uriPath, String indexFile, String servletName) {
        this.filePath = filePath.endsWith("/") ? filePath : (filePath + '/');
        this.uriPath = uriPath.endsWith("/") ? uriPath : (uriPath + '/');
        this.indexFile = indexFile;
        this.servletName = servletName;
    }

    @Override
    public void initialize(Bootstrap<?> bootstrap) {
        // nothing doing
    }

    @Override
    public void run(Environment environment) {
        LOGGER.info("Registering FileAssetBundle with servlet name {} for request path {} from file system {}",
                servletName, uriPath + '*', Paths.get(filePath).toAbsolutePath());
        environment.servlets().addServlet(servletName, createServlet()).addMapping(uriPath + '*');
    }

    private FileAssetServlet createServlet() {
        return new FileAssetServlet(filePath, uriPath, indexFile, Charsets.UTF_8);
    }
}
