package com.storyshare.boot.dao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    private long postID;
    private long userID;
    private String text;
    private Timestamp date;
    private String userName;
    private String userAvatar;
    private String picture;
    private int likes;
    private boolean isLiked;
}
