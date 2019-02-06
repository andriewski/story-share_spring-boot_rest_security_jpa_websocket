package com.storyshare.boot.controllers;

import com.google.gson.Gson;
import com.storyshare.boot.dao.dto.PostDTO;
import com.storyshare.boot.pojos.Pagination;
import com.storyshare.boot.services.PostService;
import com.storyshare.boot.services.UserService;
import com.storyshare.boot.services.auth.MVCUser;
import com.storyshare.boot.wrappers.Like;
import com.storyshare.boot.wrappers.PostWrapper;
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
public class PostController {
    @Autowired
    @Qualifier("postService")
    private PostService postService;
    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @GetMapping(value = "posts/length")
    public ResponseEntity<Long> getPostLength() {
        long numberOfPosts = postService.getNumberOfAllPosts();

        if (numberOfPosts == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(numberOfPosts, HttpStatus.OK);
    }

    @GetMapping(value = "posts", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getPosts(
            @RequestParam("offset") int offset,
            @RequestParam("limit") int limit,
            @RequestParam("userID") long userID,
            Authentication auth) {
        if (auth != null && auth.isAuthenticated() && ((MVCUser) auth.getPrincipal()).getId() != userID) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Pagination pagination = new Pagination(offset, limit);
        List<PostDTO> listOfPosts = postService.getPostsWithPagination(pagination, userID);
        Gson gson = new Gson();
        String json = gson.toJson(listOfPosts);

        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @GetMapping(value = "posts/{postID}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getSinglePost(
            @PathVariable("postID") long postID,
            @RequestParam("userID") long userID,
            Authentication auth) {
        if (auth != null && auth.isAuthenticated() && ((MVCUser) auth.getPrincipal()).getId() != userID) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        PostDTO postDTO = postService.getSinglePostDTO(userID, postID);
        Gson gson = new Gson();
        String json = gson.toJson(postDTO);

        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @PatchMapping(value = "posts/{postID}/like", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void likeUnlikePost(
            @PathVariable("postID") long postID,
            @RequestBody Like like,
            Authentication auth) {
        if (auth != null && auth.isAuthenticated() && ((MVCUser) auth.getPrincipal()).getId()
                .equals(like.getUserID())) {
            if (like.getIsLiked()) {
                postService.likePostByUser(postID, like.getUserID());
            } else {
                postService.unlikePostByUser(postID, like.getUserID());
            }
        }
    }

    @PostMapping(value = "posts", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPost(@RequestBody PostWrapper post, Authentication auth) {
        if (auth != null && auth.isAuthenticated()
                && ((MVCUser) auth.getPrincipal()).getId().equals(post.getUserID())) {
            postService.save(post.getText(), LocalDateTime.now(), post.getUserID(), post.getPicture());
        }
    }

    @DeleteMapping(value = "posts/{postID}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePost(@PathVariable("postID") long postID, @RequestParam Long userID, Authentication auth) {
        if (auth != null && auth.isAuthenticated() && ((MVCUser) auth.getPrincipal()).getId().equals(userID)) {
            postService.delete(postID);
        }
    }
}


/*
post_length
post_list
post_single
create_post
like_unlike_post
delete_post
*/