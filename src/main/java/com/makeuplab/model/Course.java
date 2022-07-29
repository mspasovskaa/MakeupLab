package com.makeuplab.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String title;
    String subtitle;
    String description;
    @OneToMany
    List<Lesson> lessonList;
    String imageLink;
    @OneToOne
    Quiz quiz;

    public Course(){
    }

    public Course(String title, String subtitle, String description, List<Lesson> lessonList, String imageLink, Quiz quiz) {
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
        this.lessonList = lessonList;
        this.imageLink = imageLink;
        this.quiz = quiz;
    }

    public Course(String title, String subtitle, String description, List<Lesson> lessonList, String imageLink) {
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
        this.lessonList = lessonList;
        this.imageLink = imageLink;
        this.quiz = quiz;
    }
}
