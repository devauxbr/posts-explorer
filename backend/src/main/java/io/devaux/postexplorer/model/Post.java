package io.devaux.postexplorer.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Post {
    @Id
    private Integer id;
    private Integer userId;
    private String title;
    private String body;
}
