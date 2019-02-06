package com.storyshare.boot.dao.dao;

import com.storyshare.boot.dao.dto.PostDTO;
import com.storyshare.boot.pojos.Pagination;
import com.storyshare.boot.pojos.Post;
import com.storyshare.boot.pojos.User;

import java.util.List;

public interface PostDAO extends DAO<Post> {
    List<PostDTO> getPostsWithPagination(Pagination pagination, long userID);

    PostDTO getSinglePostDTO(long userID, long postID);

    void likePostByUser(long postID, User user);

    void unlikePostByUser(long postID, User user);

    long getNumberOfAllPosts();
}
