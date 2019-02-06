package com.storyshare.boot.wrappers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentWrapper {
    private Long userID;
    private Long postID;
    private String text;

}
