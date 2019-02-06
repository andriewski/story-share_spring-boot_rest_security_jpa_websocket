//package com.storyshare.boot.aspects;
//
//import com.storyshare.boot.exceptions.BanUserException;
//import com.storyshare.boot.services.UserService;
//import com.storyshare.boot.services.auth.MVCUser;
//import org.aspectj.lang.annotation.AfterThrowing;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//
//@Aspect
//@Component(value = "secondAspect")
//public class AuthAspect {
//    @Autowired
//    @Qualifier("userService")
//    private UserService userService;
//
//    @Before(value = "execution(* *(..)) && (within(com.storyshare.boot.controllers..*))")
//    public void handleAround() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//        if (!auth.getPrincipal().equals("anonymousUser") &&
//                ("banned").equals(userService.getUserStatus(((MVCUser) auth.getPrincipal()).getId()))) {
//                throw new BanUserException();
//        }
//    }
//
//    @AfterThrowing(value = "execution(* *(..)) && (within(com.storyshare.boot.controllers..*))")
//    public void handle() {
//
//    }
//
//}