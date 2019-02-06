package com.storyshare.boot.controllers;

import com.google.gson.Gson;
import com.storyshare.boot.dao.dto.CommentDTO;
import com.storyshare.boot.pojos.Pagination;
import com.storyshare.boot.services.CommentService;
import com.storyshare.boot.services.PostService;
import com.storyshare.boot.services.UserService;
import com.storyshare.boot.services.auth.MVCUser;
import com.storyshare.boot.wrappers.CommentWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class CommentController {
    @Autowired
    @Qualifier("commentService")
    private CommentService commentService;
    @Autowired
    @Qualifier("userService")
    private UserService userService;
    @Autowired
    @Qualifier("postService")
    private PostService postService;

    @PostMapping(value = "comments", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void createComment(@RequestBody CommentWrapper comment, Authentication auth) {
        if (auth != null && auth.isAuthenticated() && ((MVCUser) auth.getPrincipal()).getId()
                .equals(comment.getUserID())) {
            commentService.save(comment.getUserID(), comment.getPostID(), comment.getText(), LocalDateTime.now());
        }
    }

//    @PostMapping(value = "comments", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<String> createComment(@RequestBody CommentWrapper comment, Authentication auth) {
//        if (auth != null) {
//            MVCUser mvcUser = (MVCUser) auth.getPrincipal();
//            if (userService.getUserStatus(mvcUser.getId()).equals("banned")) {
//                return new ResponseEntity<>(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
//            }
//
//            if (auth.isAuthenticated() && (mvcUser.getId().equals(comment.getUserID()))) {
//                commentService.save(comment.getUserID(), comment.getPostID(), comment.getText(), LocalDateTime.now());
//                return new ResponseEntity<>(HttpStatus.OK);
//            }
//        } else {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//    }

    @GetMapping(value = "comments", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getComments(
            @RequestParam("postID") long postID,
            @RequestParam("offset") int offset,
            @RequestParam("limit") int limit) {
        Pagination pagination = new Pagination(offset, limit);
        List<CommentDTO> listOfComments = commentService.getCommentsInThePostWithOffsetAndLimit(postID, pagination);
        Gson gson = new Gson();
        String json = gson.toJson(listOfComments);

        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @GetMapping(value = "comments/length", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> getCommentsLength(@RequestParam("postID") long postID) {
        long numberOfCommentsInThePost = commentService.getNumberOfCommentsInThePost(postID);

        return new ResponseEntity<>(numberOfCommentsInThePost, HttpStatus.OK);
    }

    @DeleteMapping(value = "comments/{commentID}")
    public void deleteComment(@PathVariable long commentID, @RequestParam Long userID, Authentication auth) {
        if (auth != null && auth.isAuthenticated() && ((MVCUser) auth.getPrincipal()).getId().equals(userID)) {
            commentService.delete(commentID);
        }
    }
}

/*
create_comment
comment_list
delete_comment
comments_length
*/