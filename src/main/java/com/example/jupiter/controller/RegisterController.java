//**********************************************************************************************************************
// * Documentation
// * Author: zilin.li
// * Date: 1//22
// * Definition: Implementation of RegisterController class.
// * Note: <Info>
//**********************************************************************************************************************
package com.example.jupiter.controller;

//**********************************************************************************************************************
// * Includes
//**********************************************************************************************************************

// Project includes
import com.example.jupiter.entity.db.User;
import com.example.jupiter.service.RegisterService;

// Framework includes
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

// System includes
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//**********************************************************************************************************************
// * Class definition
//**********************************************************************************************************************

@Controller
public class RegisterController {

//**********************************************************************************************************************
// * Public methods
//**********************************************************************************************************************

    @Autowired
    private RegisterService registerService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(@RequestBody User user, HttpServletResponse response) throws IOException {
        if (!registerService.register(user)) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }
}


