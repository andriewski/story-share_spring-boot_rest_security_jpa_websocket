package com.storyshare.boot.dao.dao.impl;

import com.storyshare.boot.dao.dao.CommentDAO;
import com.storyshare.boot.dao.dto.CommentDTO;
import com.storyshare.boot.pojos.Comment;
import com.storyshare.boot.pojos.Pagination;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository(value = "commentDAO")
@NoArgsConstructor
public class CommentDAOImpl extends BaseDAO<Comment> implements CommentDAO {
    private static volatile CommentDAO INSTANCE = null;
    private static final String getCommentsInThePostWithOffsetAndLimitQuery = "SELECT u.name as userName, " +
            "c.text as text, c.date as date, c.id as commentID, u.id as userID FROM Comment c INNER JOIN c.user u " +
            "WHERE c.post.id = :postID ORDER BY c.date DESC";

    private static final String getNumberOfCommentsInThePostQuery = "SELECT COUNT(*) FROM Comment c " +
            "WHERE c.post.id = :postID";

    @Override
    @SuppressWarnings("unchecked")
    public List<CommentDTO> getCommentsInThePostWithOffsetAndLimit(long postID, Pagination pagination) {
        return (List<CommentDTO>) getCurrentSession()
                .createQuery(getCommentsInThePostWithOffsetAndLimitQuery)
                .setParameter("postID", postID)
                .setFirstResult(pagination.getOffset())
                .setMaxResults(pagination.getLimit())
                .list().stream()
                .map(o -> {
                    Object[] objects = (Object[]) o;

                    return new CommentDTO((String) objects[0], (String) objects[1],
                            Timestamp.valueOf((LocalDateTime) objects[2]), (long) objects[3], (long) objects[4]);
                })
                .collect(Collectors.toList());
    }

    @Override
    public long getNumberOfCommentsInThePost(long postID) {
        return (long) getCurrentEntityManager()
                .createQuery(getNumberOfCommentsInThePostQuery)
                .setParameter("postID", postID)
                .getSingleResult();
    }
}
