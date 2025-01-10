package com.atomika.gitByCity.service;


import com.atomika.gitByCity.dto.Comment;
import com.atomika.gitByCity.dto.mapper.CommentMapper;
import com.atomika.gitByCity.entity.CommentEntity;
import com.atomika.gitByCity.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public Comment create (Comment comment) {
        return commentMapper.entityToDto(
                commentRepository.save(commentMapper.dtoToEntity(comment)));
    }

    public Comment update (Comment comment) {
        return commentMapper.entityToDto(
                commentRepository.save(commentMapper.dtoToEntity(comment)));
    }

    public Long delete (Long id) {
        commentRepository.deleteById(id);
        return id;
    }

    public List<Comment> findAll () {
        return commentMapper.toList(commentRepository.findAll());
    }

    public boolean isCreator (String username, long commentId) {
        return commentRepository.isCreator(username, commentId);
    }

}
