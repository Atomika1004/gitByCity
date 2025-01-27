package com.atomika.gitByCity.service;


import com.atomika.gitByCity.dto.Comment;
import com.atomika.gitByCity.dto.mapper.CommentMapper;
import com.atomika.gitByCity.exception.NotFoundException;
import com.atomika.gitByCity.repositories.ClientRepository;
import com.atomika.gitByCity.repositories.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final ClientRepository clientRepository;


    @Transactional
    public Comment create (Comment comment, String clientName) {
        comment.setClientId(clientRepository.findClientIdByUsername(clientName));
        return commentMapper.entityToDto(
                commentRepository.save(commentMapper.dtoToEntity(comment)));
    }

    public Comment update (Comment comment, String clientName) {
        comment.setClientId(clientRepository.findClientIdByUsername(clientName));
        return commentMapper.entityToDto(
                commentRepository.save(commentMapper.dtoToEntity(comment)));
    }


    //todo посмотрет потом //ошбика была в том что на уровне связей стояло nullable = false и из за этого не давало удалить
    @Transactional
    public Long delete(Long id) {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
            return id;
        } else {
            throw new NotFoundException("нет такого коммента" + id);
        }
    }

    @Transactional
    public List<Comment> findAllForRoute (long routeId) {
        return commentMapper.toList(commentRepository.findAllForRoute(routeId));
    }

    @Transactional
    public List<Comment> findAllForPoint (long pointId) {
        return commentMapper.toList(commentRepository.findAllForPoint(pointId));
    }


    public boolean isCreator (String username, long commentId) {
        return commentRepository.isCreator(username, commentId);
    }

}
