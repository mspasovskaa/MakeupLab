package com.makeuplab.service.impl;

import com.makeuplab.model.Comment;
import com.makeuplab.model.Lesson;
import com.makeuplab.model.User;
import com.makeuplab.model.exceptions.CommentNotFoundException;
import com.makeuplab.model.exceptions.LessonNotFoundException;
import com.makeuplab.model.exceptions.UserNotFoundException;
import com.makeuplab.repository.CommentRepository;
import com.makeuplab.repository.LessonRepository;
import com.makeuplab.repository.UserRepository;
import com.makeuplab.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;

    public CommentServiceImpl(CommentRepository commentRepository, LessonRepository lessonRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.lessonRepository = lessonRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Comment findById(Long id) {
        return this.commentRepository.findById(id).orElseThrow(()->new CommentNotFoundException());
    }

    @Override
    public List<Comment> findAll() {
        return this.commentRepository.findAll();
    }

    @Override
    public Comment add(Long userId, Long lessonId, String text) {
        User user=this.userRepository.findById(userId).orElseThrow(()->new UserNotFoundException());
        Lesson lesson=this.lessonRepository.findById(lessonId).orElseThrow(()->new LessonNotFoundException());


        Comment comment=new Comment(user,lesson,text);
        return this.commentRepository.save(comment);
    }

    @Override
    public Comment update(Long id, Long userId, Long lessonId, String text) {
        Comment comment=this.findById(id);
        User user=this.userRepository.findById(userId).orElseThrow(()->new UserNotFoundException());
        Lesson lesson=this.lessonRepository.findById(lessonId).orElseThrow(()->new LessonNotFoundException());
        comment.setLesson(lesson);
        comment.setUser(user);
        comment.setText(text);
        return this.commentRepository.save(comment);
    }

    @Override
    public void delete(Long id) {
        Comment comment=this.findById(id);

        this.commentRepository.delete(comment);
    }
}
