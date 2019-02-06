package com.storyshare.boot.services;

import com.storyshare.boot.dao.dto.MessageDTO;
import com.storyshare.boot.pojos.Message;
import com.storyshare.boot.pojos.Pagination;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageService extends IService<Message> {
    Message save(String text, LocalDateTime date, long senderID, long receiverID);

    List<MessageDTO> getLastMessagesInUsersDialogs(long userID);

    List<MessageDTO> getUserMessagesWithOtherUserWithOffset(long userID, long otherUser,
                                                            Pagination pagination);

    List<MessageDTO> getAllUserMessagesWithOtherUser(long userID, long otherUser);

    int deleteMessageInCertainUser(long userID, long messageID);
}
