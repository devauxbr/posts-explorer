package io.devaux.postexplorer.service.sync.client;

import io.devaux.postexplorer.model.Post;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

import static java.util.Arrays.asList;

@Component
public class PostsRestClient {

    private final String postUrl;

    public PostsRestClient(@Value("${posts.url}") String postUrl) {
        this.postUrl = postUrl;
    }

    public List<Post> getPosts() {
        RestTemplate restTemplate = new RestTemplate();
        return asList(
                Objects.requireNonNull(
                        restTemplate.getForObject(postUrl, Post[].class)
                )
        );
    }
}
