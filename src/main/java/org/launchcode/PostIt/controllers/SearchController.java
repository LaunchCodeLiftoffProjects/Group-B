package org.launchcode.PostIt.controllers;

import org.launchcode.PostIt.models.TextPost;
import org.launchcode.PostIt.models.data.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
public class SearchController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/search")
    public String displaySearch(Model model){
        List<TextPost> posts = (List<TextPost>) postRepository.findAll();
        model.addAttribute("posts", posts);
        return "search";
    }

    @PostMapping("/search")
    public String returnSearchedString(@RequestParam String postSearch, Error errors, Model model) {
        List<TextPost> posts = (List<TextPost>) postRepository.findAll();
        List<TextPost> returnedPosts = new ArrayList<>();
        for(TextPost post : posts) {
            if (post.getTitle().toLowerCase().contains(postSearch.toLowerCase()) || post.getBody().toLowerCase().contains(postSearch.toLowerCase()) || post.getUser().getUsername().toLowerCase().contains(postSearch.toLowerCase())) {
                returnedPosts.add(post);
            }
        }
        model.addAttribute("posts", returnedPosts);
        return "search";
    }
}
