package com.makeuplab.service.impl;

import com.makeuplab.model.*;
import com.makeuplab.model.exceptions.QuizNotFoundException;
import com.makeuplab.repository.QuizRepository;
import com.makeuplab.repository.QuizScoreRepository;
import com.makeuplab.repository.UserRepository;
import com.makeuplab.service.QuizScoreService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizScoreServiceImpl implements QuizScoreService {
    private final QuizScoreRepository quizScoreRepository;
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;

    public QuizScoreServiceImpl(QuizScoreRepository quizScoreRepository, QuizRepository quizRepository, UserRepository userRepository) {
        this.quizScoreRepository = quizScoreRepository;
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
    }

    @Override
    public QuizScore findById(Long id) {
        return this.quizScoreRepository.findById(id).orElseThrow(()->new QuizNotFoundException());
    }

    @Override
    public List<QuizScore> findAll() {
        return this.quizScoreRepository.findAll();
    }

    @Override
    public QuizScore add(User user, Quiz quiz,double score) {
        QuizScore quizScore=new QuizScore(user,quiz,score);

        return this.quizScoreRepository.save(quizScore);
    }

    @Override
    public QuizScore update(Long id, User user, Quiz quiz,double score) {
        QuizScore quizScore=this.findById(id);
        quizScore.setQuiz(quiz);
        quizScore.setUser(user);
        quizScore.setScore(score);
        return this.quizScoreRepository.save(quizScore);
    }

    @Override
    public void delete(Long id) {
        QuizScore quizScore=this.findById(id);
        this.quizScoreRepository.delete(quizScore);
    }

    @Override
    public QuizScore findQuizScoreByUserAndCourse(User user, Course course) {
        List<QuizScore> quizScores=this.quizScoreRepository.findAll();
        QuizScore quizScore=new QuizScore();
        for (QuizScore q:quizScores
        ) {
            if(q.getUser().equals(user) && q.getQuiz().getCourse().equals(course))
            {
                quizScore=q;
            }
        }
        return quizScore;
    }

    @Override
    public double calculateScore(List<Question> trueQuestions,List<Question> wrongQuestions, List<Question> questions) {
        int i=0,j=0;
        for (Question q:trueQuestions) {
            for (Question q1: questions) {
                if(q1.equals(q))
                {
                    i++;
                }
            }
        }
        for (Question q:wrongQuestions) {
            for (Question q1: questions) {
                if(q1.equals(q))
                {
                    j++;
                }
            }
        }

        double sum=0;
        if(i==0)
        {
            return sum;
        }
        sum=100.0/trueQuestions.size()*(i - j);
        return sum;
    }
}
