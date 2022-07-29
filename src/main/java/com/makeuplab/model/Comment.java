package com.makeuplab.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    User user;
    @ManyToOne
    Lesson lesson;
    String text;

    public Comment(){}

    public Comment(User user, Lesson lesson, String text) {
        this.user = user;
        this.lesson = lesson;
        this.text = text;
    }
}
