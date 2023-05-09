package com.stock.service.impl;

import com.stock.entity.Forum;
import com.stock.model.ForumModel;
import com.stock.repository.ForumRepository;
import com.stock.service.ForumService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ForumServiceImpl implements ForumService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ForumRepository forumRepository;
    @Override
    public void publishTheMessage(String email, ForumModel forumModel) {
        forumModel.setEmail(email);
        Forum forum = modelMapper.map(forumModel,Forum.class);
        this.forumRepository.save(forum);
    }

    @Override
    public List<Forum> getAllPublishedMessages() {
        return this.forumRepository.findAll();
    }
}
