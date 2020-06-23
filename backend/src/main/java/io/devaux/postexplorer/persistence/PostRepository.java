package io.devaux.postexplorer.persistence;


import io.devaux.postexplorer.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post, Integer> {
}
