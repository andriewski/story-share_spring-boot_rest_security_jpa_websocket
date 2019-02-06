package com.storyshare.boot.dao.dao;

import com.storyshare.boot.dao.dto.MessageDTO;
import com.storyshare.boot.pojos.Message;
import com.storyshare.boot.pojos.Pagination;

import java.util.List;

public interface MessageDAO extends DAO<Message> {
    List<MessageDTO> getLastMessagesInUsersDialogs(long userID);

    List<MessageDTO> getUserMessagesWithOtherUserWithOffset(long userID, long otherUser, Pagination pagination);

    List<MessageDTO> getAllUserMessagesWithOtherUser(long userID, long otherUser);

    int deleteMessageInCertainUser(long userID, long messageID);
}
