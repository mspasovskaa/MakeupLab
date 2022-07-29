package com.makeuplab.service.impl;

import com.makeuplab.model.Question;
import com.makeuplab.model.Quiz;
import com.makeuplab.model.exceptions.QuestionNotFoundException;
import com.makeuplab.model.exceptions.QuizNotFoundException;
import com.makeuplab.repository.QuestionRepository;
import com.makeuplab.repository.QuizRepository;
import com.makeuplab.service.QuestionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository, QuizRepository quizRepository) {
        this.questionRepository = questionRepository;
        this.quizRepository = quizRepository;
    }

    @Override
    public Question findById(Long id) {
        return this.questionRepository.findById(id).orElseThrow(()->new QuestionNotFoundException());
    }

    @Override
    public List<Question> findAll() {
        return this.questionRepository.findAll();
    }

    @Override
    public Question add(Long quizId, String question, String answer) {
        Quiz quiz=this.quizRepository.findById(quizId).orElseThrow(()->new QuizNotFoundException());
        Question question1=new Question(quiz,question,answer);
        return this.questionRepository.save(question1);
    }

    @Override
    public Question update(Long id, Long quizId, String question, String answer) {
        Quiz quiz=this.quizRepository.findById(quizId).orElseThrow(()->new QuizNotFoundException());

        Question question1=this.findById(id);
        question1.setQuestion(question);
        question1.setQuiz(quiz);
        question1.setAnswer(answer);

        return this.questionRepository.save(question1);
    }

    @Override
    public void delete(Long id) {
        Question question1=this.findById(id);

        this.questionRepository.delete(question1);
    }

    @Override
    public List<Question> findAllByIds(List<Long> sub) {
        List<Question> questions=this.questionRepository.findAllById(sub);
        return questions;
    }
}
