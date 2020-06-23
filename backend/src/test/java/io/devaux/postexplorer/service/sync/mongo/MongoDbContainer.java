package io.devaux.postexplorer.service.sync.mongo;

import org.testcontainers.containers.GenericContainer;

/**
 * Setup inspired from https://github.com/nirajsonawane/SpringBoot-mongodb-testcontainers
 */
public class MongoDbContainer extends GenericContainer<MongoDbContainer> {

    public static final int MONGODB_PORT = 27017;
    public static final String DEFAULT_IMAGE_AND_TAG = "mongo:4.2.7";
    public MongoDbContainer() {
        this(DEFAULT_IMAGE_AND_TAG);
    }

    public MongoDbContainer(String image) {
        super(image);
        addExposedPort(MONGODB_PORT);
    }

    public Integer getPort() {
        return getMappedPort(MONGODB_PORT);
    }

}
