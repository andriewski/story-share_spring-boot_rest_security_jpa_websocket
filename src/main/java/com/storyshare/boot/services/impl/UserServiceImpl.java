package com.storyshare.boot.services.impl;

import com.google.gson.JsonObject;
import com.storyshare.boot.dao.dao.UserDAO;
import com.storyshare.boot.pojos.User;
import com.storyshare.boot.services.ServiceException;
import com.storyshare.boot.services.UserService;
import lombok.NoArgsConstructor;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
@Transactional
@NoArgsConstructor
public class UserServiceImpl extends BaseService<User> implements UserService {
    @Autowired
    @Qualifier("userDAO")
    private UserDAO userDAO;

    @Override
    public User getUserByEmail(String email) {
        try {
            return userDAO.getUserByEmail(email);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public String getUserAvatar(long userID) {
        try {
            return userDAO.getUserAvatar(userID);
        } catch (HibernateException e) {
            throw new ServiceException("Error getting user avatar by userID " + userID);
        }
    }

    @Override
    public String getUserStatus(long userID) {
        try {
            return userDAO.getUserStatus(userID);
        } catch (HibernateException e) {
            throw new ServiceException("Error getting user status by userID " + userID);
        }
    }


    @Override
    public String getUserName(long userID) {
        try {
            return userDAO.getUserName(userID);
        } catch (HibernateException e) {
            throw new ServiceException("Error getting user name by userID " + userID);
        }
    }

    @Override
    public JsonObject getUserRoleAndStatus(long userID) {
        try {
            return userDAO.getUserRoleAndStatus(userID);
        } catch (HibernateException e) {
            throw new ServiceException("Error getting user role and status by userID " + userID);
        }
    }

    @Override
    public void banUser(long id) {
        try {
            userDAO.banUser(id);
        } catch (HibernateException e) {
            throw new ServiceException("Error banning User by id " + id);
        }
    }

    @Override
    public void unbanUser(long id) {
        try {
            userDAO.unbanUser(id);
        } catch (HibernateException e) {
            throw new ServiceException("Error unbanning User by id " + id);
        }
    }

//    @Secured("BOSS")
//    @PreAuthorize("hasAuthority('BOSS')")
    @Override
    public void assignAdmin(long id) {
        try {
            userDAO.assignAdmin(id);
        } catch (HibernateException e) {
            throw new ServiceException("Error assigning like Admin by id " + id);
        }
    }

//    @Secured("BOSS")
//    @PreAuthorize("hasAuthority('BOSS')")
    @Override
    public void assignUser(long id) {
        try {
            userDAO.assignUser(id);
        } catch (HibernateException e) {
            throw new ServiceException("Error assigning like User by id " + id);
        }
    }
}
