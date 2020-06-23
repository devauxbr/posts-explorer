package io.devaux.postexplorer.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Post {
    @Id
    private Integer id;
    private Integer userId;
    private String title;
    private String body;
}
