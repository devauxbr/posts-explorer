package io.devaux.postexplorer.sync.client;

import io.devaux.postexplorer.model.Post;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This is a Integration Test (IT) and not a Unit Test because it cannot be run in isolation : it requires the
 * jsonplaceholder.typecode.com endpoint to be available in order to pass
 */
class PostsRestClientIT {

    private final PostsRestClient underTest = new PostsRestClient(
            "https://jsonplaceholder.typicode.com/posts");

    @Test
    void shouldFetchPosts() {
        List<Post> posts = underTest.getPosts();

        assertThat(posts).isNotEmpty();
        for (Post post : posts) {
            assertThat(post.getId()).isNotNull();
            assertThat(post.getUserId()).isNotNull();
            assertThat(post.getTitle()).isNotEmpty();
            assertThat(post.getBody()).isNotEmpty();
        }
    }
}
