package com.makeuplab.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Quiz quiz;
    private String question;
    private String answer;

    public Question(){}
    public Question(Quiz quiz, String question, String answer) {
        this.quiz = quiz;
        this.question = question;
        this.answer = answer;
    }
}
