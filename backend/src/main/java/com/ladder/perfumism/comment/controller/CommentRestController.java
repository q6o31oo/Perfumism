package com.ladder.perfumism.comment.controller;

import com.ladder.perfumism.comment.controller.request.CommentCreateRequest;
import com.ladder.perfumism.comment.service.CommentService;
import com.sun.jndi.toolkit.url.Uri;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/comment")
public class CommentRestController {

    private final CommentService commentService;

    public CommentRestController(CommentService commentService){
        this.commentService = commentService;
    }

    @PostMapping("/{article_id}")
    public ResponseEntity<Void> createComment(@AuthenticationPrincipal String email,
        @RequestBody CommentCreateRequest request, @PathVariable(value = "article_id")Long articleId){
        commentService.commentCreate(email,articleId,request);

        return ResponseEntity.ok().build();
    }


}