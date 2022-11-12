//**********************************************************************************************************************
// * Documentation
// * Author: zilin.li
// * Date: 11/22
// * Definition: Implementation of RecommendationController class.
// * Note: <Info>
//**********************************************************************************************************************
package com.example.jupiter.controller;

//**********************************************************************************************************************
// * Includes
//**********************************************************************************************************************

// Project includes
import com.example.jupiter.entity.db.Item;
import com.example.jupiter.service.RecommendationException;
import com.example.jupiter.service.RecommendationService;

// Framework includes
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

// System includes
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

//**********************************************************************************************************************
// * Class definition
//**********************************************************************************************************************

@Controller
public class RecommendationController {

//**********************************************************************************************************************
// * Public methods
//**********************************************************************************************************************

    @RequestMapping(value = "/recommendation", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, List<Item>> recommendation(HttpServletRequest request) throws ServletException {
        HttpSession session = request.getSession(false);
        Map<String, List<Item>> itemMap;
        try {
            if (session == null) {
                itemMap = recommendationService.recommendItemsByDefault();
            } else {
                String userId = (String) session.getAttribute("user_id");
                itemMap = recommendationService.recommendItemsByUser(userId);
            }
        } catch (RecommendationException e) {
            throw new ServletException(e);
        }

        return itemMap;
    }

//**********************************************************************************************************************
// * Private attributes
//**********************************************************************************************************************
    @Autowired
    private RecommendationService recommendationService;
}
