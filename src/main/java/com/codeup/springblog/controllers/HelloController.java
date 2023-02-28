package com.codeup.springblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {

    @GetMapping("/hello/{name}")
    public String sayHello(@PathVariable String name, Model model){
        model.addAttribute("name", name);
        return "hello";
    }

    @GetMapping("/welcome")
    public String welcome(){
        return "welcome";
    }

    @GetMapping("/join")
    public String showJoinForm(){
        return "join";
    }

    @PostMapping("/join")
    public String joinCohort(@RequestParam(name = "cohort") String cohort, Model model){
        model.addAttribute("cohort", "Welcome to "+ cohort+ "!");
        return "join";
    }

    @GetMapping("/roll-dice")
    public String chooseNum(){
        return "roll-dice";
    }

    @GetMapping("/roll-dice/{number}")
    public String rollDice(@PathVariable String number, Model model) {

        int diceRoll = (int)(Math.random()*6)+1;

        boolean youWin = Integer.parseInt(number) == diceRoll;

        String win;
        if(youWin) {
           win = "You Won";
        }
        else{
            win = "You Lose";
        }

        model.addAttribute("number","You guessed "+ number+ ". The dice roll was "+ diceRoll+ ". "+ win);
        return "roll-dice";
    }
}
