package com.storyshare.boot.dao.dao;

import com.storyshare.boot.dao.dto.CommentDTO;
import com.storyshare.boot.pojos.Comment;
import com.storyshare.boot.pojos.Pagination;

import java.util.List;

public interface CommentDAO extends DAO<Comment> {
    List<CommentDTO> getCommentsInThePostWithOffsetAndLimit(long postID, Pagination pagination);

    long getNumberOfCommentsInThePost(long postID);
}
