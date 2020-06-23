package io.devaux.postexplorer.service;

import com.querydsl.core.types.OrderSpecifier;
import io.devaux.postexplorer.model.Post;
import io.devaux.postexplorer.persistence.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.querydsl.core.types.Order.ASC;
import static io.devaux.postexplorer.model.QPost.post;
import static org.springframework.data.querydsl.QPageRequest.of;
import static org.springframework.data.querydsl.QSort.by;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository repository;

    public List<Post> findTop50OrderByTitle() {
        return repository.findAll(of(0, 50, by(new OrderSpecifier<>(ASC, post.title))))
                .toList();
    }
}
