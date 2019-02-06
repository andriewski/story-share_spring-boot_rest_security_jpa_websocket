package com.storyshare.boot.services.impl;

import com.storyshare.boot.dao.dao.PostDAO;
import com.storyshare.boot.dao.dao.UserDAO;
import com.storyshare.boot.dao.dto.PostDTO;
import com.storyshare.boot.pojos.Pagination;
import com.storyshare.boot.pojos.Post;
import com.storyshare.boot.services.PostService;
import com.storyshare.boot.services.ServiceException;
import lombok.NoArgsConstructor;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service(value = "postService")
@Transactional
@NoArgsConstructor
public class PostServiceImpl extends BaseService<Post> implements PostService {
    @Autowired
    @Qualifier("userDAO")
    private UserDAO userDAO;
    @Autowired
    @Qualifier("postDAO")
    private PostDAO postDAO;

    @Override
    public Post save(String text, LocalDateTime date, long userID, String picture) {
        try {
            return postDAO.save(new Post(text, date, userDAO.load(userID), picture));
        } catch (HibernateException e) {
            throw new ServiceException("Error saving post");
        }
    }

    @Override
    public void delete(long postID) {
        try {
            postDAO.delete(postDAO.load(postID));
        } catch (HibernateException e) {
            throw new ServiceException("Error deleting by ID " + postID);
        }
    }

    @Override
    public List<PostDTO> getPostsWithPagination(Pagination pagination, long userID) {
        try {
            return postDAO.getPostsWithPagination(pagination, userID);
        } catch (HibernateException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Error getting Posts With Pagination by userID ")
                    .append(userID)
                    .append(" and Pagination")
                    .append(pagination);

            throw new ServiceException(sb.toString());
        }
    }

    @Override
    public void likePostByUser(long postID, long userID) {
        try {
            postDAO.likePostByUser(postID, userDAO.load(userID));
        } catch (HibernateException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Error liking Post by id ")
                    .append(postID)
                    .append(" and userID ")
                    .append(userID);

            throw new ServiceException(sb.toString());
        }
    }

    @Override
    public void unlikePostByUser(long postID, long userID) {
        try {
            postDAO.unlikePostByUser(postID, userDAO.load(userID));
        } catch (HibernateException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Error unliking Post by id ")
                    .append(postID)
                    .append(" and userID ")
                    .append(userID);

            throw new ServiceException(sb.toString());
        }
    }

    @Override
    public PostDTO getSinglePostDTO(long userID, long postID) {
        try {
            return postDAO.getSinglePostDTO(userID, postID);
        } catch (HibernateException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Error getting Single PostDTO userID ")
                    .append(userID)
                    .append(" and postID ")
                    .append(postID);

            throw new ServiceException(sb.toString());
        }
    }

    @Override
    public long getNumberOfAllPosts() {
        try {
            return postDAO.getNumberOfAllPosts();
        } catch (HibernateException e) {
            throw new ServiceException("Error getting Number Of All Posts ");
        }
    }
}
