package com.stock.service;

import com.stock.entity.Forum;
import com.stock.model.ForumModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ForumService {
    void publishTheMessage(String email, ForumModel forumModel);
    List<Forum> getAllPublishedMessages();
}
