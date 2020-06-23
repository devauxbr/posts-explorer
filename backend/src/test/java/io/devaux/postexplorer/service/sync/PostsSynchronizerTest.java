package io.devaux.postexplorer.service.sync;

import io.devaux.postexplorer.model.Post;
import io.devaux.postexplorer.persistence.PostRepository;
import io.devaux.postexplorer.service.sync.mongo.MongoDbContainer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ContextConfiguration(initializers = PostsSynchronizerTest.MongoDbInitializer.class)
@ExtendWith(SpringExtension.class)
public class PostsSynchronizerTest {

    @Autowired
    private PostsSynchronizer synchronizer;

    @Autowired
    private PostRepository repository;

    @Test
    void name() {
        synchronizer.sync();

        // We validate that the synced posts list is not empty and that every elements contain values
        // Note that we don't verify the exact values as we assume the fetched posts values may change, as these
        // would break the test
        List<Post> posts = repository.findAll();
        assertThat(posts).isNotEmpty();
        for (Post post : posts) {
            assertThat(post.getId()).isNotNull();
            assertThat(post.getUserId()).isNotNull();
            assertThat(post.getTitle()).isNotEmpty();
            assertThat(post.getBody()).isNotEmpty();
        }
    }

    /*
    MongoDB Testcontainers plumbing
     */

    private static MongoDbContainer mongoDbContainer;

    @BeforeAll
    public static void startContainerAndPublicPortIsAvailable() {
        mongoDbContainer = new MongoDbContainer();
        mongoDbContainer.start();
    }

    public static class MongoDbInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues values = TestPropertyValues.of(
                    "spring.data.mongodb.host=" + mongoDbContainer.getContainerIpAddress(),
                    "spring.data.mongodb.port=" + mongoDbContainer.getPort()

            );
            values.applyTo(configurableApplicationContext);
        }
    }

}
