package com.codeup.springblog.controllers;

import com.codeup.springblog.repositories.AdRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class AdController {
    private final AdRepository adDao;

    public AdController(AdRepository adDao) {
        this.adDao = adDao;
    }

    //In Java, before we can use methods of other classes, we first need to create the object of that class (i.e. class A needs to create an instance of class B). One of the ways we can do this, is in the constructor. Spring provides us with some facilities to use automatic dependency injection in our classes. The following example shows how we can inject AdRepository into our AdController.

    @GetMapping("/ads")
    public String index(Model model){
        model.addAttribute("ads", adDao.findAll());
        return "ads/index";
    }

    //We can use dependency injection in most of the classes in our Spring application. We can even inject services into other services! This is how you can use it in order to get the list of all Ads.
    //By extending JpaRepository, we inherit the CRUD functionality that the Spring framework provides, including methods for retrieving an Iterable Interface1 with all the ads (findAll), a specific ad (getById), inserting or updating an ad (save), and deleting an ad (delete).
}
