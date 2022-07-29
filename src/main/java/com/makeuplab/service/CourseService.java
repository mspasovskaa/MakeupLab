package com.makeuplab.service;

import com.makeuplab.model.Course;
import com.makeuplab.model.Lesson;
import com.makeuplab.model.Quiz;

import java.util.List;

public interface CourseService {
    Course findById(Long id);
    List<Course> findAll();
    Course add(String title, String subtitle, String description, String imageLink);
    Course addLessons(Long id,Lesson lessonList);
    Course addQuiz(Long id,Quiz quiz);
    Course update(Long id,String title, String subtitle, String description, List<Long> lessonIds, String imageLink, Long quizId);
    void delete(Long id);
    Course findByLesson(Lesson lesson);
}
