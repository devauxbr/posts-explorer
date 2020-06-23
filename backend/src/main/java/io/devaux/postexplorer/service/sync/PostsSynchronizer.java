package io.devaux.postexplorer.service.sync;

import io.devaux.postexplorer.model.Post;
import io.devaux.postexplorer.persistence.PostRepository;
import io.devaux.postexplorer.service.sync.client.PostsRestClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service that synchronises posts with our MongoDB database
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PostsSynchronizer {
    private final PostRepository repository;
    private final PostsRestClient client;

    /**
     * A sync job is started every 5m
     */
    @Scheduled(fixedDelay = 300_000, initialDelay = 300_000)
    public synchronized void sync() {
        log.info("Posts sync job starting...");
        List<Post> posts = client.getPosts();
        repository.saveAll(posts);
        log.info("Posts sync job done.");
    }

    /**
     * A first sync job is triggered on startup
     */
    @EventListener(ApplicationReadyEvent.class)
    public void doStartupSync() {
        this.sync();
    }
}
