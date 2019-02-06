package com.storyshare.boot.services;

import com.storyshare.boot.dao.dto.PostDTO;
import com.storyshare.boot.pojos.Pagination;
import com.storyshare.boot.pojos.Post;

import java.time.LocalDateTime;
import java.util.List;

public interface PostService extends IService<Post> {
    Post save(String text, LocalDateTime date, long userID, String picture);

    void delete(long postID);

    List<PostDTO> getPostsWithPagination(Pagination pagination, long userID);

    void likePostByUser(long postID, long userID);

    void unlikePostByUser(long postID, long userID);

    PostDTO getSinglePostDTO(long userID, long postID);

    long getNumberOfAllPosts();
}
