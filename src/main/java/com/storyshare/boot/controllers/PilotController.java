package com.storyshare.boot.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.storyshare.boot.dao.dto.UserDTO;
import com.storyshare.boot.pojos.User;
import com.storyshare.boot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.ResourceBundle;

@RestController
public class PilotController {
    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @GetMapping(value = "/login_success")
    public ResponseEntity<String> loginSuccess(Authentication auth) {
        String email = ((UserDetails) auth.getPrincipal()).getUsername();
        User user = userService.getUserByEmail(email);
        UserDTO userDTO = new UserDTO(user.getId(), user.getName(), user.getAvatar(), user.getRole());

        return new ResponseEntity<>(new Gson().toJson(userDTO), HttpStatus.OK);
    }

    @GetMapping(value = "/logout_handler")
    @ResponseStatus(HttpStatus.OK)
    public void logoutPage(HttpServletRequest req, HttpServletResponse resp, Authentication auth) {
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(req, resp, auth);
        }
    }

    @GetMapping(value = "locale", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getLocale(@RequestParam("locale") String locale) {
        ResourceBundle rb;
        JsonObject json = new JsonObject();

        if ("ru".equals(locale)) {
            rb = ResourceBundle.getBundle("localization_ru");
        } else if ("en".equals(locale)) {
            rb = ResourceBundle.getBundle("localization_en");
        } else {
            rb = ResourceBundle.getBundle("localization");
        }

        Enumeration<String> listOfKeys = rb.getKeys();
        String nextElement;
        while (listOfKeys.hasMoreElements()) {
            nextElement = listOfKeys.nextElement();
            json.addProperty(nextElement, rb.getString(nextElement));
        }

        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }
}

/*
login
logout
get_locale
*/