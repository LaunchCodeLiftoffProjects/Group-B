package org.launchcode.PostIt.controllers;

import org.launchcode.PostIt.models.data.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class EditController {
    @Autowired
    PostRepository postRepository;

    @Autowired
    AuthenticationController authenticationController;

    @GetMapping("/edit/{id}")
    public String editPost(@PathVariable int id, Model model){
        Object post = postRepository.findById(id);
        model.addAttribute("post", post);
        return "edit";
    }

}
