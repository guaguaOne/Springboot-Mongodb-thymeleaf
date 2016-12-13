package com.tumei.web.controller.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@RestController
@RequestMapping("/session")
public class SessionController {

    @RequestMapping("/uid")
    public String index(HttpSession session)
    {
        UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
            session.setAttribute("uid", uid);
        }

        return session.getId();
    }
}
