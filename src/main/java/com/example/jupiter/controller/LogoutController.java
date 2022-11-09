//**********************************************************************************************************************
// * Documentation
// * Author: zilin.li
// * Date: 11/22
// * Definition: Implementation of LogoutController class.
// * Note: <Info>
//**********************************************************************************************************************
package com.example.jupiter.controller;

//**********************************************************************************************************************
// * Includes
//**********************************************************************************************************************

// Framework includes
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//**********************************************************************************************************************
// * Class definition
//**********************************************************************************************************************

@Controller
public class LogoutController {

//**********************************************************************************************************************
// * Public methods
//**********************************************************************************************************************

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Delete the cookie on front end
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

    }
}

