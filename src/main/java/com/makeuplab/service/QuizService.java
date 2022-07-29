package com.makeuplab.service;

import com.makeuplab.model.Course;
import com.makeuplab.model.Question;
import com.makeuplab.model.Quiz;
import com.makeuplab.model.User;

import java.util.List;

public interface QuizService {
    Quiz findById(Long id);
    List<Quiz> findAll();
    Quiz add(Long courseId);
    Quiz addQuestion(Long id,List<Question> questions);
    Quiz addUsers(Long id,List<User> users);
    Quiz update(Long id, List<Long> usersIds, Long courseId, List<Long> questionsIds);
    void delete(Long id);

}
