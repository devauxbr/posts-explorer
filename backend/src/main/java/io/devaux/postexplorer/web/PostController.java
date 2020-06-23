package io.devaux.postexplorer.web;

import io.devaux.postexplorer.model.Post;
import io.devaux.postexplorer.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService service;

    @GetMapping("/top-50-posts")
    public List<Post> getTop50Posts() {
        return service.findTop50OrderByTitle();
    }
}
