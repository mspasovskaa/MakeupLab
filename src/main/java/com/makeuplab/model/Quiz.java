package com.makeuplab.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany
    private List<User> users;

    @OneToOne
    private Course course;

    @OneToMany
    private List<Question> questions;


    public Quiz (){}

    public Quiz(List<Question> questions) {
        this.questions = questions;
    }

    public Quiz(List<User> users, Course course, List<Question> questions) {
        this.users = users;
        this.course = course;
        this.questions = questions;
    }
}
