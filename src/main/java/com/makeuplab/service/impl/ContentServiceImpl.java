package com.makeuplab.service.impl;

import com.makeuplab.model.Content;
import com.makeuplab.model.exceptions.ContentNotFoundException;
import com.makeuplab.repository.ContentRepository;
import com.makeuplab.service.ContentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {
    private final ContentRepository contentRepository;

    public ContentServiceImpl(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    @Override
    public Content findById(Long id) {
        return this.contentRepository.findById(id).orElseThrow(()->new ContentNotFoundException());
    }

    @Override
    public List<Content> findAll() {
        return this.contentRepository.findAll();
    }

    @Override
    public Content add(String title, String text) {
        Content content=new Content(title,text);
        return this.contentRepository.save(content);
    }

    @Override
    public Content update(Long id, String title, String text) {
        Content content=this.findById(id);
        content.setText(text);
        content.setTitle(title);
        return this.contentRepository.save(content);
    }

    @Override
    public void delete(Long id) {
        Content content=this.findById(id);

        this.contentRepository.delete(content);
    }
}
