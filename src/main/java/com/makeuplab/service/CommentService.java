package com.makeuplab.service;

import com.makeuplab.model.Comment;

import java.util.List;

public interface CommentService {

    Comment findById(Long id);
    List<Comment> findAll();
    Comment add(Long userId,Long lessonId,String text);
    Comment update(Long id,Long userId,Long lessonId,String text);
    void delete(Long id);
}
