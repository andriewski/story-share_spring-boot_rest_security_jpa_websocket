package com.storyshare.boot.services.impl;

import com.storyshare.boot.dao.dao.MessageDAO;
import com.storyshare.boot.dao.dao.UserDAO;
import com.storyshare.boot.dao.dto.MessageDTO;
import com.storyshare.boot.pojos.Message;
import com.storyshare.boot.pojos.Pagination;
import com.storyshare.boot.services.MessageService;
import com.storyshare.boot.services.ServiceException;
import lombok.NoArgsConstructor;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service(value = "messageService")
@Transactional
@NoArgsConstructor
public class MessageServiceImpl extends BaseService<Message> implements MessageService {
    @Autowired
    @Qualifier("messageDAO")
    private MessageDAO messageDAO;
    @Autowired
    @Qualifier("userDAO")
    private UserDAO userDAO;

    @Override
    public Message save(String text, LocalDateTime date, long senderID, long receiverID) {
        return messageDAO.save(new Message(text, date, userDAO.load(senderID), userDAO.load(receiverID)));
    }

    @Override
    public List<MessageDTO> getLastMessagesInUsersDialogs(long userID) {
        try {
            return messageDAO.getLastMessagesInUsersDialogs(userID);
        } catch (HibernateException e) {
            throw new ServiceException("Error getting Comments Last Messages In Users Dialogs by ID " + userID);
        }
    }

    @Override
    public List<MessageDTO> getUserMessagesWithOtherUserWithOffset(long userID, long otherUser, Pagination pagination) {
        try {
            return messageDAO.getUserMessagesWithOtherUserWithOffset(userID, otherUser, pagination);
        } catch (HibernateException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Error getting User Messages With Other User With Offset by userID ")
                    .append(userID)
                    .append(" anotherUserID ")
                    .append(otherUser)
                    .append(" and pagination ")
                    .append(pagination);

            throw new ServiceException(sb.toString());
        }
    }

    @Override
    public List<MessageDTO> getAllUserMessagesWithOtherUser(long userID, long otherUser) {
        try {
            return messageDAO.getAllUserMessagesWithOtherUser(userID, otherUser);
        } catch (HibernateException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Error getting All User Messages With Other User userID ")
                    .append(userID)
                    .append(" and anotherUserID ")
                    .append(otherUser);

            throw new ServiceException(sb.toString());
        }
    }

    @Override
    public int deleteMessageInCertainUser(long userID, long messageID) {
        try {
            return messageDAO.deleteMessageInCertainUser(userID, messageID);
        } catch (HibernateException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Error deleting delete Message In Certain User by userID ")
                    .append(userID)
                    .append(" and messageID ")
                    .append(messageID);

            throw new ServiceException(sb.toString());
        }
    }
}
