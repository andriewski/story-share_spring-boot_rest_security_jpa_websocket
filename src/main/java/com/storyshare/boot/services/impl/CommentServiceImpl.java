package com.storyshare.boot.services.impl;

import com.storyshare.boot.dao.dao.CommentDAO;
import com.storyshare.boot.dao.dao.PostDAO;
import com.storyshare.boot.dao.dao.UserDAO;
import com.storyshare.boot.dao.dto.CommentDTO;
import com.storyshare.boot.pojos.Comment;
import com.storyshare.boot.pojos.Pagination;
import com.storyshare.boot.services.CommentService;
import com.storyshare.boot.services.ServiceException;
import lombok.NoArgsConstructor;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service(value = "commentService")
@Transactional
@NoArgsConstructor
public class CommentServiceImpl extends BaseService<Comment> implements CommentService {
    @Autowired
    @Qualifier("commentDAO")
    private CommentDAO commentDAO;
    @Autowired
    @Qualifier("userDAO")
    private UserDAO userDAO;
    @Autowired
    @Qualifier("postDAO")
    private PostDAO postDAO;

    @Override
    public Comment save(long userID, long postID, String text, LocalDateTime date) {
        try {
            return commentDAO.save(new Comment(userDAO.load(userID), postDAO.load(postID), text, date));
        } catch (HibernateException e) {
            throw new ServiceException("Error saving comment");
        }
    }

    @Override
    public void delete(long commentID) {
        try {
            commentDAO.delete(commentDAO.load(commentID));
        } catch (HibernateException e) {
            throw new ServiceException("Error deleting by ID " + commentID);
        }
    }

    @Override
    public List<CommentDTO> getCommentsInThePostWithOffsetAndLimit(long postID, Pagination pagination) {
        try {
            return commentDAO.getCommentsInThePostWithOffsetAndLimit(postID, pagination);
        } catch (HibernateException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Error getting Comments In The Post With Offset And Limit by ID ")
                    .append(postID)
                    .append(" and Pagination")
                    .append(pagination);

            throw new ServiceException(sb.toString());
        }
    }

    @Override
    public long getNumberOfCommentsInThePost(long postID) {
        try {
            return commentDAO.getNumberOfCommentsInThePost(postID);
        } catch (HibernateException e) {
            throw new ServiceException("Error getting Number Of Comments In The Post by id " + postID);
        }
    }
}
