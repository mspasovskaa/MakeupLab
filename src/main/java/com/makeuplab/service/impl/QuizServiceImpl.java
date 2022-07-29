package com.makeuplab.service.impl;

import com.makeuplab.model.Course;
import com.makeuplab.model.Question;
import com.makeuplab.model.Quiz;
import com.makeuplab.model.User;
import com.makeuplab.model.exceptions.CourseNotFoundException;
import com.makeuplab.model.exceptions.QuizNotFoundException;
import com.makeuplab.repository.CourseRepository;
import com.makeuplab.repository.QuestionRepository;
import com.makeuplab.repository.QuizRepository;
import com.makeuplab.repository.UserRepository;
import com.makeuplab.service.QuizService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizServiceImpl implements QuizService {
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final CourseRepository courseRepository;

    public QuizServiceImpl(QuizRepository quizRepository, UserRepository userRepository, QuestionRepository questionRepository, CourseRepository courseRepository) {
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public Quiz findById(Long id) {
        return this.quizRepository.findById(id).orElseThrow(()->new QuizNotFoundException());
    }

    @Override
    public List<Quiz> findAll() {
        return this.quizRepository.findAll();
    }

    @Override
    public Quiz add(Long courseId) {
        Course course=this.courseRepository.findById(courseId).orElseThrow(()->new CourseNotFoundException());
        List<User> users=new ArrayList<>();
        List<Question> questions=new ArrayList<>();
        Quiz quiz=new Quiz(users,course,questions);
        return this.quizRepository.save(quiz);
    }

    @Override
    public Quiz addQuestion(Long id,List<Question> questions) {
        Quiz quiz=this.findById(id);

        quiz.setQuestions(questions);
        return this.quizRepository.save(quiz);

    }

    @Override
    public Quiz addUsers(Long id,List<User> users) {
        Quiz quiz=this.findById(id);
        quiz.setUsers(users);

        return this.quizRepository.save(quiz);

    }

    @Override
    public Quiz update(Long id, List<Long> usersIds, Long courseId, List<Long> questionsIds) {
        Quiz quiz=this.findById(id);

        List<User> users=this.userRepository.findAllById(usersIds);
        Course course=this.courseRepository.findById(courseId).orElseThrow(()->new CourseNotFoundException());
        List<Question> questions=this.questionRepository.findAllById(questionsIds);
        quiz.setCourse(course);
        quiz.setUsers(users);
        quiz.setQuestions(questions);
        return this.quizRepository.save(quiz);

    }

    @Override
    public void delete(Long id) {
        Quiz quiz=this.findById(id);
        this.quizRepository.delete(quiz);

    }
}
