package org.launchcode.PostIt.controllers;

import org.launchcode.PostIt.models.AbstractPost;
import org.launchcode.PostIt.models.TextPost;
import org.launchcode.PostIt.models.data.ImagePostRepository;
import org.launchcode.PostIt.models.data.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ImagePostRepository imagePostRepository;

    @GetMapping("/")
    public String displayIndex(Model model){
        //TODO Sort posts by date
        List<AbstractPost> posts = new ArrayList<>();
        posts.addAll((Collection<? extends AbstractPost>) postRepository.findAll());
        model.addAttribute("posts", posts);
        return "index";
    }
}
