package com.makeuplab.service.impl;

import com.makeuplab.model.Course;
import com.makeuplab.model.Lesson;
import com.makeuplab.model.Quiz;
import com.makeuplab.model.exceptions.CourseNotFoundException;
import com.makeuplab.model.exceptions.QuizNotFoundException;
import com.makeuplab.repository.CourseRepository;
import com.makeuplab.repository.LessonRepository;
import com.makeuplab.repository.QuizRepository;
import com.makeuplab.service.CourseService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;
    private final QuizRepository quizRepository;

    public CourseServiceImpl(CourseRepository courseRepository, LessonRepository lessonRepository, QuizRepository quizRepository) {
        this.courseRepository = courseRepository;
        this.lessonRepository = lessonRepository;
        this.quizRepository = quizRepository;
    }

    @Override
    public Course findById(Long id) {
        return this.courseRepository.findById(id).orElseThrow(()->new CourseNotFoundException());
    }

    @Override
    public List<Course> findAll() {
        return this.courseRepository.findAll();
    }

    @Override
    public Course add(String title, String subtitle, String description, String imageLink) {
        List<Lesson> lessonList=new ArrayList<>();
        Course course=new Course(title,subtitle,description,lessonList,imageLink,new Quiz());
        return this.courseRepository.save(course);
    }

    @Override
    public Course addLessons(Long id,Lesson lesson) {
        Course course=this.findById(id);
        List<Lesson> lessonList=course.getLessonList();
        lessonList.add(lesson);
        course.setLessonList(lessonList);
        return this.courseRepository.save(course);
    }

    @Override
    public Course addQuiz(Long id,Quiz quiz) {
        Course course=this.findById(id);

        course.setQuiz(quiz);
        return this.courseRepository.save(course);
    }

    @Override
    public Course update(Long id, String title, String subtitle, String description, List<Long> lessonIds, String imageLink, Long quizId) {
        Course course=this.findById(id);

        course.setTitle(title);
        course.setSubtitle(subtitle);
        course.setDescription(description);
        List<Lesson> lessonList=this.lessonRepository.findAllById(lessonIds);
        course.setLessonList(lessonList);
        course.setImageLink(imageLink);
        Quiz quiz=this.quizRepository.findById(quizId).orElseThrow(()->new QuizNotFoundException());
        course.setQuiz(quiz);
        return this.courseRepository.save(course);
    }


    @Override
    public void delete(Long id) {
        Course course=this.findById(id);

        this.courseRepository.delete(course);

    }

    @Override
    public Course findByLesson(Lesson lesson) {
        List<Course> courseList=this.courseRepository.findAll();
        Course course=new Course();
        for (Course c:courseList
             ) {
            if(c.getLessonList().contains(lesson))
            {
                course=c;
            }
        }

        return course;
    }
}
