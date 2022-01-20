package org.launchcode.PostIt.controllers;

import org.launchcode.PostIt.models.AbstractPost;
import org.launchcode.PostIt.models.TextPost;
import org.launchcode.PostIt.models.data.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private PostRepository postRepository;
    @GetMapping("/")
    public String displayIndex(Model model){
        List<TextPost> posts = (List<TextPost>) postRepository.findAll();
        model.addAttribute("posts", posts);
        return "index";
    }
}
