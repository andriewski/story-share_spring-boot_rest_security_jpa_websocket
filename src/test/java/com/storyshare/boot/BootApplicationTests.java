package com.storyshare.boot;

import com.storyshare.boot.pojos.User;
import com.storyshare.boot.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ComponentScan("com.storyshare")
public class BootApplicationTests {
    @Autowired
    private UserService userService;

    @Test
    @Transactional
    public void contextLoads() {
        System.out.println(userService.save(new User("test", "test@tut.by",
                "avatar", "12345", "user")));
    }
}

