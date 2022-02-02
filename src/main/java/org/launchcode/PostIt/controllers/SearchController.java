package org.launchcode.PostIt.controllers;

import org.launchcode.PostIt.models.AbstractPost;
import org.launchcode.PostIt.models.TextPost;
import org.launchcode.PostIt.models.data.ImagePostRepository;
import org.launchcode.PostIt.models.data.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class SearchController {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ImagePostRepository imagePostRepository;

    @GetMapping("/search")
    public String displaySearch(Model model){
        //List<TextPost> posts = (List<TextPost>) postRepository.findAll();
        //model.addAttribute("posts", posts);
        return "search";
    }

    @PostMapping("/search")
    public String returnSearchedString(@RequestParam String postSearch, Error errors, Model model) {
        List<AbstractPost> posts = new ArrayList<>();
        posts.addAll((Collection<? extends AbstractPost>) postRepository.findAll());
        posts.addAll((Collection<? extends AbstractPost>) imagePostRepository.findAll());
        List<AbstractPost> returnedPosts = new ArrayList<>();
        for(AbstractPost post : posts) {
            if (post.getTitle().toLowerCase().contains(postSearch.toLowerCase())) {
                returnedPosts.add(post);
            }
        }
        returnedPosts.sort(Comparator.naturalOrder());
        model.addAttribute("posts", returnedPosts);
        return "search";
    }
}
