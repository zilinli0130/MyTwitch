package com.example.jupiter.controller;

import com.example.jupiter.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class GameController {

    @Autowired
    private GameService gameService;

    // /game?game_name=whatever
    // /game
    // http://localhost:8080/game
    @RequestMapping(value = "/game", method = RequestMethod.GET)
    public void getGame(@RequestParam(value = "game_name", required = false) String gameName, HttpServletResponse response) throws IOException {
        String fakeName = gameService.fakeName();
        System.out.println(fakeName);
        response.getWriter().write(fakeName);
    }
}
