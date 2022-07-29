package com.makeuplab.service;

import com.makeuplab.model.Comment;
import com.makeuplab.model.Content;
import com.makeuplab.model.Lesson;

import java.util.List;

public interface LessonService {
    Lesson findById(Long id);
    List<Lesson> findAll();
    Lesson add(String title, String description, String imageLink);
    Lesson addContents(Long id,List<Content> contentList);
    Lesson addComments(Long id,List<Comment> comments);
    Lesson addComment(Long id,Comment comment);
    Lesson update(Long id,String title, String description, List<Content> contentList, String imageLink, List<Comment> comments);
    void delete(Long id);
}
