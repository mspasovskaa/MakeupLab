package com.makeuplab.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    @OneToMany
    private List<Content> contentList;

    private String imageLink;

    @OneToMany(cascade=CascadeType.ALL)
    private List<Comment> comments;

    public Lesson(String title, String description,String imageLink) {

        this.title = title;
        this.description = description;
        this.imageLink=imageLink;
    }

    public Lesson(){}

    public Lesson(String title, String description, List<Content> contentList, String imageLink, List<Comment> comments) {
        this.title = title;
        this.description = description;
        this.contentList = contentList;
        this.imageLink = imageLink;
        this.comments = comments;
    }
}
