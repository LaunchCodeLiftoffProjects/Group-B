package org.launchcode.PostIt.controllers;

import org.launchcode.PostIt.models.AbstractPost;
import org.launchcode.PostIt.models.data.ImagePostRepository;
import org.launchcode.PostIt.models.data.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@Controller
public class IndexController {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ImagePostRepository imagePostRepository;

    @GetMapping("/")
    public String displayIndex(Model model){
        List<AbstractPost> posts = new ArrayList<>();
        posts.addAll((Collection<? extends AbstractPost>) postRepository.findAll());
        posts.addAll((Collection<? extends AbstractPost>) imagePostRepository.findAll());

        posts.sort(Comparator.naturalOrder());

        model.addAttribute("posts", posts);
        return "index";
    }
}
