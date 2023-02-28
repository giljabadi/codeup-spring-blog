package com.codeup.springblog.controllers;

import com.codeup.springblog.models.Post;
import com.codeup.springblog.models.User;
import com.codeup.springblog.repositories.PostRepository;
import com.codeup.springblog.repositories.UserRepository;
import com.codeup.springblog.services.EmailService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class PostController {
    private PostRepository postDao;
    private UserRepository userDao;
    private final EmailService emailService;

    public PostController(PostRepository postDao, UserRepository userDao, EmailService emailService) {
        this.postDao = postDao;
        this.userDao = userDao;
        this.emailService = emailService;
    }

    @GetMapping("/posts")
    public String getPosts(Model vModel) {
        if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User loggedInUser = userDao.findById(principal.getId()).get();
            vModel.addAttribute("user", loggedInUser);
        }
        List<Post> posts = postDao.findAll();
        vModel.addAttribute("posts", posts);
        return "posts/index";
    }

    @GetMapping("/posts/{id}")
    public String getPost(@PathVariable long id, Model vModel) {
        Post post = postDao.findById(id).get();
        vModel.addAttribute("post", post);
        return "posts/show";
    }

    @GetMapping("/posts/create")
    public String viewCreate(Model model){
        model.addAttribute("post", new Post());
        return "posts/create";
    }

    @PostMapping("/posts/create")
    public String savePost(@Valid Post post, Errors validation, Model model) {
        if (validation.hasErrors()) {
            model.addAttribute("errors", validation);
            model.addAttribute("post", post);
            return "posts/create";
        }
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        post.setUser(principal);
        postDao.save(post);
        emailService.prepareAndSend(post, "You created a new post!");
        return "redirect:/posts?created";
    }

    @GetMapping("/posts/{id}/edit")
    public String showEditForm(@PathVariable long id, Model model) {
        Post post = postDao.findById(id).get();
        model.addAttribute("post", post);
        return "posts/edit";
    }

    @PostMapping("/posts/{id}/edit")
    public String submitEditForm(@Valid Post postUpdates, Errors validation, Model model) {
        if (validation.hasErrors()) {
            model.addAttribute("errors", validation);
            model.addAttribute("post", postUpdates);
            return "posts/edit";
        }
        Optional<Post> postToUpdate = postDao.findById(postUpdates.getId());
        Post post = postToUpdate.get();
        post.setTitle(postUpdates.getTitle());
        post.setBody(postUpdates.getBody());
        postDao.save(post);
        return "redirect:/posts?edited";
    }

    @PostMapping("/posts/{id}/delete")
    public String deletePost(@PathVariable long id) {
        postDao.deleteById(id);
        return "redirect:/posts";
    }

    @PostMapping("/posts/{id}/like")
    @ResponseBody
    public String likeUnlikePost(@PathVariable long id) {
        Post post = postDao.findById(id).get();
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userThatLiked = userDao.findById(principal.getId()).get();
        post.toggleUserLike(userThatLiked);
        postDao.save(post);
        return "Post liked!";
    }

}
