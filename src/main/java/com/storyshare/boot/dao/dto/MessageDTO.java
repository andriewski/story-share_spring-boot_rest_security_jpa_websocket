package com.storyshare.boot.dao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "date")
public class MessageDTO {
    private String text;
    private Timestamp date;
    private long senderID;
    private long receiverID;
    private String senderName;
    private String receiverName;
}
