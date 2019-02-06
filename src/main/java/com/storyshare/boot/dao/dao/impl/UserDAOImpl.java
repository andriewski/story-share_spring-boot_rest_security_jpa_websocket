package com.storyshare.boot.dao.dao.impl;

import com.google.gson.JsonObject;
import com.storyshare.boot.dao.dao.UserDAO;
import com.storyshare.boot.pojos.User;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository(value = "userDAO")
@NoArgsConstructor
public class UserDAOImpl extends BaseDAO<User> implements UserDAO {
    private static volatile UserDAO INSTANCE = null;
    private static final String getUserByEmailQuery = "FROM User WHERE email = :email";
    private static final String unbanUserQuery = "UPDATE User u SET u.status = 'active' WHERE u.id = :userID";
    private static final String banUserQuery = "UPDATE User u SET u.status = 'banned' WHERE u.id = :userID";
    private static final String getUserAvatarQuery = "SELECT u.avatar FROM User u WHERE u.id = :userID";
    private static final String getUserNameQuery = "SELECT u.name FROM User u WHERE u.id = :userID";
    private static final String getUserStatusQuery = "SELECT u.status FROM User u WHERE u.id = :userID";
    private static final String getUserRoleAndStatusQuery = "SELECT u.role, u.status FROM User u WHERE u.id = :userID";
    private static final String assignAdminQuery = "UPDATE User u SET u.role = 'admin' WHERE u.id = :userID";
    private static final String assignUserQuery = "UPDATE User u SET u.role = 'user' WHERE u.id = :userID";

    @Override
    public User getUserByEmail(String email) {
        return (User) getCurrentEntityManager()
                .createQuery(getUserByEmailQuery)
                .setParameter("email", email)
                .getSingleResult();
    }

    @Override
    public String getUserAvatar(long userID) {
        return (String) getCurrentEntityManager()
                .createQuery(getUserAvatarQuery)
                .setParameter("userID", userID)
                .getSingleResult();
    }

    @Override
    public String getUserStatus(long userID) {
        return (String) getCurrentEntityManager()
                .createQuery(getUserStatusQuery)
                .setParameter("userID", userID)
                .getSingleResult();
    }

    @Override
    public String getUserName(long userID) {
        return (String) getCurrentEntityManager()
                .createQuery(getUserNameQuery)
                .setParameter("userID", userID)
                .getSingleResult();
    }

    @Override
    public JsonObject getUserRoleAndStatus(long userID) {
        JsonObject roleAndStatus = new JsonObject();

        Object[] result = (Object[]) getCurrentEntityManager()
                .createQuery(getUserRoleAndStatusQuery)
                .setParameter("userID", userID)
                .getSingleResult();

        roleAndStatus.addProperty("role", result[0].toString());
        roleAndStatus.addProperty("status", result[1].toString());

        return roleAndStatus;
    }

    @Override
    public void unbanUser(long userID) {
        getCurrentEntityManager()
                .createQuery(unbanUserQuery)
                .setParameter("userID", userID)
                .executeUpdate();
    }

    @Override
    public void banUser(long userID) {
        getCurrentEntityManager()
                .createQuery(banUserQuery)
                .setParameter("userID", userID)
                .executeUpdate();
    }

    @Override
    public void assignAdmin(long userID) {
        getCurrentEntityManager()
                .createQuery(assignAdminQuery)
                .setParameter("userID", userID)
                .executeUpdate();
    }

    @Override
    public void assignUser(long userID) {
        getCurrentEntityManager()
                .createQuery(assignUserQuery)
                .setParameter("userID", userID)
                .executeUpdate();
    }
}
