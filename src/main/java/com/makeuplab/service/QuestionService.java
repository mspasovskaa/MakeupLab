package com.makeuplab.service;

import com.makeuplab.model.Question;

import java.util.List;

public interface QuestionService {
    Question findById(Long id);
    List<Question> findAll();
    Question add(Long quizId,String question, String answer);
    Question update(Long id,Long quizId,String question, String answer);
    void delete(Long id);
    List<Question> findAllByIds(List<Long> sub);
}
