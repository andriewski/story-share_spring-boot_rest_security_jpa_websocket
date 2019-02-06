package com.storyshare.boot.dao.dao.impl;

import com.storyshare.boot.dao.dao.MessageDAO;
import com.storyshare.boot.dao.dto.MessageDTO;
import com.storyshare.boot.pojos.Message;
import com.storyshare.boot.pojos.Pagination;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository(value = "messageDAO")
@NoArgsConstructor
public class MessageDAOImpl extends BaseDAO<Message> implements MessageDAO {
    private static volatile MessageDAO INSTANCE = null;
    private static final String getLastMessagesInUsersDialogsQuery = "SELECT m.text as text, m.date as date, " +
            "m.sender.id as senderID, m.receiver.id as receiverID, " +
            "senders.name AS senderName, receivers.name AS receiverName FROM Message m " +
            "INNER JOIN User AS senders ON m.sender.id = senders.id " +
            "INNER JOIN User AS receivers ON m.receiver.id = receivers.id " +
            "WHERE ((senders.id = :userID AND m.deletedBySender = false) " +
            "OR (receivers.id = :userID AND m.deletedByReceiver = false)) " +
            "AND m.id IN (SELECT MAX(m2.id) FROM Message m2 GROUP BY CASE " +
            "WHEN (m2.sender.id > m2.receiver.id AND ((m2.sender.id = :userID AND m2.deletedBySender = false ) OR " +
            "(m2.receiver.id = :userID AND m2.deletedByReceiver = false ))) THEN m2.receiver.id ELSE m2.sender.id END," +
            "CASE WHEN (m2.sender.id < m2.receiver.id AND ((m2.sender.id = :userID AND m2.deletedBySender = false) OR " +
            "(m2.receiver.id = :userID AND m2.deletedByReceiver = false))) THEN m2.receiver.id ELSE m2.sender.id END)" +
            "ORDER BY m.date DESC";

    private static final String getUserMessagesWithOtherUserWithOffsetAndLimitQuery = "SELECT m.text as text, " +
            "m.date as date, m.sender.id as senderID, m.receiver.id as receiverID, senders.name AS senderName, " +
            "receivers.name AS receiverName FROM Message m " +
            "INNER JOIN User AS senders ON m.sender.id = senders.id " +
            "INNER JOIN User AS receivers ON m.receiver.id = receivers.id WHERE (m.sender.id = :userID AND " +
            "m.receiver.id = :otherUser AND m.deletedBySender = false) OR " +
            "(m.sender.id = :otherUser AND m.receiver.id = :userID AND m.deletedByReceiver = false) ORDER BY m.date DESC";

    private static final String getAllUserMessagesWithOtherUser = "SELECT m.text as text, " +
            "m.date as date, m.sender.id as senderID, m.receiver.id as receiverID, senders.name AS senderName, " +
            "receivers.name AS receiverName FROM Message m " +
            "INNER JOIN User AS senders ON m.sender.id = senders.id " +
            "INNER JOIN User AS receivers ON m.receiver.id = receivers.id WHERE (m.sender.id = :userID AND " +
            "m.receiver.id = :otherUser AND m.deletedBySender = false) OR (m.sender.id = :otherUser " +
            "AND m.receiver.id = :userID AND m.deletedByReceiver = false)";

    private static final String deleteMessageInCertainUserQuery = "UPDATE Message m SET m.deletedBySender = " +
            "CASE WHEN (m.sender.id = :userID) THEN true ELSE m.deletedBySender END, " +
            "m.deletedByReceiver = CASE WHEN (m.receiver.id = :userID) THEN true ELSE m.deletedByReceiver END " +
            "WHERE m.id = :messageID";

    @Override
    @SuppressWarnings("unchecked")
    public List<MessageDTO> getLastMessagesInUsersDialogs(long userID) {
        return (List<MessageDTO>) getCurrentSession()
                .createQuery(getLastMessagesInUsersDialogsQuery)
                .setParameter("userID", userID)
                .list().stream()
                .map(o -> {
                    Object[] objects = (Object[]) o;

                    return new MessageDTO((String) objects[0], Timestamp.valueOf((LocalDateTime) objects[1]),
                            (long) objects[2], (long) objects[3], (String) objects[4], (String) objects[5]);
                })
                .collect(Collectors.toList());
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<MessageDTO> getUserMessagesWithOtherUserWithOffset(long userID, long otherUser, Pagination pagination) {
        return (List<MessageDTO>) getCurrentSession()
                .createQuery(getUserMessagesWithOtherUserWithOffsetAndLimitQuery)
                .setParameter("userID", userID)
                .setParameter("otherUser", otherUser)
                .setFirstResult(pagination.getOffset())
                .setMaxResults(pagination.getLimit())
                .list().stream()
                .map(o -> {
                    Object[] objects = (Object[]) o;

                    return new MessageDTO((String) objects[0], Timestamp.valueOf((LocalDateTime) objects[1]),
                            (long) objects[2], (long) objects[3], (String) objects[4], (String) objects[5]);
                })
                .collect(Collectors.toList());
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<MessageDTO> getAllUserMessagesWithOtherUser(long userID, long otherUser) {
        return (List<MessageDTO>) getCurrentSession()
                .createQuery(getAllUserMessagesWithOtherUser)
                .setParameter("userID", userID)
                .setParameter("otherUser", otherUser)
                .list().stream()
                .map(o -> {
                    Object[] objects = (Object[]) o;

                    return new MessageDTO((String) objects[0], Timestamp.valueOf((LocalDateTime) objects[1]),
                            (long) objects[2], (long) objects[3], (String) objects[4], (String) objects[5]);
                })
                .collect(Collectors.toList());
    }

    @Override
    public int deleteMessageInCertainUser(long userID, long messageID) {
        return getCurrentEntityManager()
                .createQuery(deleteMessageInCertainUserQuery)
                .setParameter("userID", userID)
                .setParameter("messageID", messageID)
                .executeUpdate();
    }
}
