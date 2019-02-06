package com.storyshare.boot.dao.dao.impl;

import com.storyshare.boot.dao.dao.PostDAO;
import com.storyshare.boot.dao.dto.PostDTO;
import com.storyshare.boot.pojos.Pagination;
import com.storyshare.boot.pojos.Post;
import com.storyshare.boot.pojos.User;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository(value = "postDAO")
@NoArgsConstructor
public class PostDAOImpl extends BaseDAO<Post> implements PostDAO {
    private static volatile PostDAO INSTANCE = null;

    private static final String getPostsWithPaginationQuery = "SELECT p.id AS postID, p.user.id AS userID, " +
            "p.text AS text, p.date AS date, p.user.name AS userName, p.user.avatar AS userAvatar, " +
            "p.picture AS picture, p.likes.size AS likes, " +
            "CASE WHEN ((SELECT count(userLike.id) FROM Post p1 INNER JOIN p1.likes AS userLike WHERE p1.id = p.id " +
            "AND userLike.id = :userID) > 0) THEN true ELSE false END AS isLiked " +
            "FROM Post p INNER JOIN p.user " +
            "ORDER BY p.date DESC";

    private static final String getSinglePostDTOQuery = "SELECT p.id AS postID, p.user.id AS userID, " +
            "p.text AS text, p.date AS date, p.user.name AS userName, p.user.avatar AS userAvatar, " +
            "p.picture AS picture, p.likes.size AS likes, " +
            "CASE WHEN ((SELECT COUNT(userLike.id) FROM Post p1 INNER JOIN p1.likes AS userLike WHERE p1.id = p.id " +
            "AND userLike.id = :userID) > 0) THEN true ELSE false END AS isLiked " +
            "FROM Post p INNER JOIN p.user WHERE p.id = :postID";


    private static final String getNumberOfAllPostsQuery = "SELECT COUNT(*) FROM Post";

    @Override
    @SuppressWarnings("unchecked")
    public List<PostDTO> getPostsWithPagination(Pagination pagination, long userID) {
        return (List<PostDTO>) getCurrentSession().createQuery(getPostsWithPaginationQuery)
                .setParameter("userID", userID)
                .setFirstResult(pagination.getOffset())
                .setMaxResults(pagination.getLimit())
                .list().stream()
                .map(o -> {
                    Object[] objects = (Object[]) o;

                    return new PostDTO((long) objects[0], (long) objects[1], (String) objects[2],
                            Timestamp.valueOf((LocalDateTime) objects[3]), (String) objects[4], (String) objects[5],
                            (String) objects[6], (int) objects[7], (boolean) objects[8]);
                })
                .collect(Collectors.toList());


    }

    @Override
    public void likePostByUser(long postID, User user) {
        load(postID).getLikes().add(user);
    }

    @Override
    public void unlikePostByUser(long postID, User user) {
        get(postID).getLikes().remove(user);
    }

    @Override
    public PostDTO getSinglePostDTO(long userID, long postID) {
        Object[] objects = (Object[]) getCurrentSession()
                .createQuery(getSinglePostDTOQuery)
                .setParameter("userID", userID)
                .setParameter("postID", postID)
                .getSingleResult();

        return new PostDTO((long) objects[0], (long) objects[1], (String) objects[2],
                Timestamp.valueOf((LocalDateTime) objects[3]), (String) objects[4], (String) objects[5],
                (String) objects[6], (int) objects[7], (boolean) objects[8]);
    }

    @Override
    public long getNumberOfAllPosts() {
        return (long) getCurrentEntityManager()
                .createQuery(getNumberOfAllPostsQuery)
                .getSingleResult();
    }
}