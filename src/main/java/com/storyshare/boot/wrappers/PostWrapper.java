package com.storyshare.boot.wrappers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostWrapper {
    private Long userID;
    private String picture;
    private String text;
}
