package com.makeuplab.service.impl;

import com.makeuplab.model.Comment;
import com.makeuplab.model.Content;
import com.makeuplab.model.Lesson;
import com.makeuplab.model.exceptions.LessonNotFoundException;
import com.makeuplab.repository.CommentRepository;
import com.makeuplab.repository.ContentRepository;
import com.makeuplab.repository.LessonRepository;
import com.makeuplab.service.LessonService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;
    private final ContentRepository contentRepository;
    private final CommentRepository commentRepository;

    public LessonServiceImpl(LessonRepository lessonRepository, ContentRepository contentRepository, CommentRepository commentRepository) {
        this.lessonRepository = lessonRepository;
        this.contentRepository = contentRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public Lesson findById(Long id) {
        return this.lessonRepository.findById(id).orElseThrow(()->new LessonNotFoundException());
    }

    @Override
    public List<Lesson> findAll() {
        return this.lessonRepository.findAll();
    }

    @Override
    public Lesson add(String title, String description, String imageLink) {
        List<Content> contentList=new ArrayList<>();
        List<Comment> comments=new ArrayList<>();
        Lesson lesson=new Lesson(title,description,contentList,imageLink,comments);
        return this.lessonRepository.save(lesson);
    }

    @Override
    public Lesson addContents(Long id,List<Content> contentList) {
        Lesson lesson=this.findById(id);
        lesson.setContentList(contentList);
        return this.lessonRepository.save(lesson);
    }

    @Override
    public Lesson addComments(Long id,List<Comment> comments) {
        Lesson lesson=this.findById(id);

        lesson.setComments(comments);
        return this.lessonRepository.save(lesson);
    }

    @Override
    public Lesson addComment(Long id, Comment comment) {
        Lesson lesson=this.findById(id);
        List<Comment> comments=lesson.getComments();
        comments.add(comment);
        lesson.setComments(comments);
        return this.lessonRepository.save(lesson);
    }

    @Override
    public Lesson update(Long id, String title, String description, List<Content> contentList, String imageLink, List<Comment> comments) {
        Lesson lesson=this.findById(id);

        lesson.setTitle(title);
        lesson.setDescription(description);
        lesson.setContentList(contentList);
        lesson.setImageLink(imageLink);
        lesson.setComments(comments);
        return this.lessonRepository.save(lesson);
    }

    @Override
    public void delete(Long id) {
        Lesson lesson=this.findById(id);

        this.lessonRepository.delete(lesson);
    }
}
