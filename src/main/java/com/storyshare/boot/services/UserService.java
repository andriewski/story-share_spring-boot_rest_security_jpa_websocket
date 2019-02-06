package com.storyshare.boot.services;

import com.google.gson.JsonObject;
import com.storyshare.boot.pojos.User;

public interface UserService extends IService<User> {
    User getUserByEmail(String email);

    String getUserAvatar(long userID);

    String getUserStatus(long userID);

    String getUserName(long userID);

    JsonObject getUserRoleAndStatus(long userID);

    void banUser(long id);

    void unbanUser(long id);

    void assignAdmin(long id);

    void assignUser(long id);
}
