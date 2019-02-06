package com.storyshare.boot.dao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private String userName;
    private String text;
    private Timestamp date;
    private long commentID;
    private long userID;
}
