package com.makeuplab.service;

import com.makeuplab.model.*;

import java.util.List;

public interface QuizScoreService {
    QuizScore findById(Long id);
    List<QuizScore> findAll();
    QuizScore add(User user, Quiz quiz,double score);
    QuizScore update(Long id,User user,Quiz quiz,double score);
    void delete(Long id);
    QuizScore findQuizScoreByUserAndCourse(User user, Course course);
    double calculateScore(List<Question> trueQuestions,List<Question> wrongQuestions, List<Question> questions);
}
