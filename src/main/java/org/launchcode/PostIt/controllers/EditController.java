package org.launchcode.PostIt.controllers;

import org.launchcode.PostIt.models.AbstractPost;
import org.launchcode.PostIt.models.TextPost;
import org.launchcode.PostIt.models.data.PostRepository;
import org.launchcode.PostIt.models.dto.TextPostFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class EditController {
    @Autowired
    PostRepository postRepository;

    @Autowired
    AuthenticationController authenticationController;

    @GetMapping("/edit/{id}")
    public String editPost(@PathVariable int id, Model model){
        Optional<TextPost> result = postRepository.findById(id);
        TextPost post = result.get();
        model.addAttribute("post", post);
        return "edit";
    }

    @PostMapping("edit/{id}")
    public String savePost(@PathVariable int id, Model model, @RequestParam String newTitle, @RequestParam String newBody, @RequestParam(required = false) boolean newAnonymousFalse, @RequestParam(required = false) boolean newAnonymousTrue) {
        Optional<TextPost> result = postRepository.findById(id);
        TextPost post = result.get();
        post.setBody(newBody);
        post.setTitle(newTitle);
        if (!post.getAnonymous()) {
            post.setAnonymous(newAnonymousFalse);
        } else if (post.getAnonymous()) {
            post.setAnonymous(newAnonymousTrue);
        }

        postRepository.save(post);
        model.addAttribute("post", post);
        return "edit";
    }
}
