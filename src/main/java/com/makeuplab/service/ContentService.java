package com.makeuplab.service;

import com.makeuplab.model.Content;

import java.util.List;

public interface ContentService {
    Content findById(Long id);
    List<Content> findAll();
    Content add(String title,String text);
    Content update(Long id,String title,String text);
    void delete(Long id);
}
