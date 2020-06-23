package io.devaux.postexplorer;

import io.devaux.postexplorer.model.Post;
import io.devaux.postexplorer.persistence.PostRepository;
import io.devaux.postexplorer.service.PostService;
import io.devaux.postexplorer.service.sync.PostsSynchronizer;
import io.devaux.postexplorer.mongo.MongoDbContainer;
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
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests that spin up a MongoDB instance with Testcontainers to test in "real conditions"
 */
@SpringBootTest
@ContextConfiguration(initializers = IntegrationTest.MongoDbInitializer.class)
@ExtendWith(SpringExtension.class)
public class IntegrationTest {

    @Autowired
    private PostsSynchronizer synchronizer;

    @Autowired
    private PostRepository repository;

    @Autowired
    private PostService service;

    @Test
    void syncShouldFetchAndSavePosts() {
        synchronizer.sync();

        // We validate that the synced posts list is not empty and that every elements contain values
        // Note that we don't verify the exact values as we assume the fetched posts values may change, and this
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

    @Test
    void fetchShouldBeLimitedAndOrdered() {
        List<Post> actual = service.findTop50OrderByTitle();

        List<Post> expected = repository.findAll().stream()
                .sorted(comparing(Post::getTitle))
                .limit(50)
                .collect(toList());
        assertThat(actual).containsExactly(expected.toArray(new Post[0]));
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
