package com.atomika.gitByCity.controllers;

import com.atomika.gitByCity.dto.Comment;
import com.atomika.gitByCity.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/base/comments")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/route/{routeId}")
    public List<Comment> getCommentsForRoute(@PathVariable("routeId") long routeId ) {
        return commentService.findAllForRoute(routeId);
    }

    @GetMapping("/point/{pointId}")
    public List<Comment> getCommentsForPoint(@PathVariable("pointId") long pointId ) {
        return commentService.findAllForPoint(pointId);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Comment addComment(@RequestBody Comment comment) {
        String clientName = AuthorizationController.getCurrentUsername();
        return commentService.create(comment, clientName);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@commentService.isCreator(authentication.name,#comment.id)")
    public Comment updateComment(@RequestBody Comment comment) {
        String clientName = AuthorizationController.getCurrentUsername();
        return commentService.update(comment, clientName);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@commentService.isCreator(authentication.name,#id)")
    public Long deleteComment(@PathVariable("id") long id) {
        return commentService.delete(id);
    }

}
