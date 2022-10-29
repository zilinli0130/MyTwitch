//**********************************************************************************************************************
// * Documentation
// * Author: zilin.li
// * Date: 10/22
// * Definition: Implementation of SearchController class.
// * Note: <Info>
//**********************************************************************************************************************
package com.example.jupiter.controller;

//**********************************************************************************************************************
// * Includes
//**********************************************************************************************************************

// project includes
import com.example.jupiter.entity.db.Item;
import com.example.jupiter.service.GameService;

// Framework includes
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

// System includes
import java.util.*;

//**********************************************************************************************************************
// * Class definition
//**********************************************************************************************************************

@Controller
public class SearchController {

//**********************************************************************************************************************
// * Public methods
//**********************************************************************************************************************
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, List<Item>> SearchItemFromService(@RequestParam(value = "game_id") String gameId) {
        return gameService.SearchItems(gameId);
    }

//**********************************************************************************************************************
// * Private attributes
//**********************************************************************************************************************
    @Autowired
    private GameService gameService;
}
