package com.makeuplab.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class QuizScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Quiz quiz;

    private double score;

    public QuizScore(){

    }

    public QuizScore(User user, Quiz quiz,double score) {
        this.user = user;
        this.quiz = quiz;
        this.score=score;
    }
}
