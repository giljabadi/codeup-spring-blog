package com.codeup.springblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class FirstController {
    @GetMapping("/test")
    @ResponseBody
    public String test(){
        return "Java sucks ass!";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/bubbles")
    @ResponseBody
    public String bubbles(){
        return "Popped";
    }

    @GetMapping("/greeting/{name}")
    @ResponseBody
    public String greeting(@PathVariable String name){
        return "Yo, wassup " + name;
    }
}

