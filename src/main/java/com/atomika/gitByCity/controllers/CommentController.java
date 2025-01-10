package com.atomika.gitByCity.controllers;

import com.atomika.gitByCity.dto.Comment;
import com.atomika.gitByCity.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/base/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping
    public List<Comment> getComments() {
        return commentService.findAll();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Comment addComment(@RequestBody Comment comment) {
        return commentService.create(comment);
    }

    @PutMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Comment updateComment(@PathVariable("id") long id, @RequestBody Comment comment) {
        return commentService.update(comment);
    }

    @DeleteMapping(value = "{id}")
    public Long deleteComment(@PathVariable("id") long id) {
        return commentService.delete(id);
    }
}
