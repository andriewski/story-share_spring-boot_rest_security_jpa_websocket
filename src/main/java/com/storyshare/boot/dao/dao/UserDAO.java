package com.storyshare.boot.dao.dao;

import com.google.gson.JsonObject;
import com.storyshare.boot.pojos.User;

public interface UserDAO extends DAO<User> {
    String getUserAvatar(long userID);

    User getUserByEmail(String email);

    String getUserName(long userID);

    String getUserStatus(long userID);

    JsonObject getUserRoleAndStatus(long userID);

    void unbanUser(long userID);

    void banUser(long userID);

    void assignAdmin(long userID);

    void assignUser(long userID);
}
