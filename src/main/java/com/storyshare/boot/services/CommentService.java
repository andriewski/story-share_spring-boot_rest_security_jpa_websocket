package com.storyshare.boot.services;

import com.storyshare.boot.dao.dto.CommentDTO;
import com.storyshare.boot.pojos.Comment;
import com.storyshare.boot.pojos.Pagination;

import java.time.LocalDateTime;
import java.util.List;

public interface CommentService extends IService<Comment> {
    Comment save(long userID, long postID, String text, LocalDateTime date);

    void delete(long commentID);

    List<CommentDTO> getCommentsInThePostWithOffsetAndLimit(long postID, Pagination pagination);

    long getNumberOfCommentsInThePost(long postID);
}
