package org.launchcode.PostIt.controllers;

import org.launchcode.PostIt.models.AbstractPost;
import org.launchcode.PostIt.models.ImagePost;
import org.launchcode.PostIt.models.TextPost;
import org.launchcode.PostIt.models.data.ImagePostRepository;
import org.launchcode.PostIt.models.data.PostRepository;
import org.launchcode.PostIt.models.dto.TextPostFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class EditController {
    @Autowired
    PostRepository postRepository;

    @Autowired
    ImagePostRepository imagePostRepository;

    @Autowired
    AuthenticationController authenticationController;

    @GetMapping("/edit/{id}")
    public String editPost(@PathVariable int id, Model model){
        Optional<TextPost> textResult = postRepository.findById(id);
        Optional<ImagePost> imageResult = imagePostRepository.findById(id);
        if(textResult.isPresent()) {
            TextPost post = textResult.get();
            model.addAttribute("post", post);
        } else if (imageResult.isPresent()) {
            ImagePost post = imageResult.get();
            model.addAttribute("post", post);
        }
        return "edit";

    }

    @PostMapping("edit/{id}")
    public String savePost(@PathVariable int id, Model model, @RequestParam String newTitle, @RequestParam(required = false) String newBody, @RequestParam(required = false) boolean newAnonymousFalse, @RequestParam(required = false) boolean newAnonymousTrue, HttpSession session) {
        Optional<TextPost> textResult = postRepository.findById(id);
        Optional<ImagePost> imageResult = imagePostRepository.findById(id);

            if(textResult.isPresent()) {
                TextPost post = textResult.get();
                if (textResult.get().getUser() == authenticationController.getUserFromSession(session)) {
                    post.setBody(newBody);
                    post.setTitle(newTitle);
                    if (!post.getAnonymous()) {
                        post.setAnonymous(newAnonymousFalse);
                    } else if (post.getAnonymous()) {
                        post.setAnonymous(newAnonymousTrue);
                    }
                    postRepository.save(post);
                } else {
                    model.addAttribute("errorMessage", "This is not your post!");
                }
                model.addAttribute("post", post);
            } else if (imageResult.isPresent()) {
                ImagePost post = imageResult.get();
                if(imageResult.get().getUser() == authenticationController.getUserFromSession(session)) {
                    post.setTitle(newTitle);
                    if (!post.getAnonymous()) {
                        post.setAnonymous(newAnonymousFalse);
                    } else if (post.getAnonymous()) {
                        post.setAnonymous(newAnonymousTrue);
                    }
                    imagePostRepository.save(post);
                } else {
                    model.addAttribute("errorMessage", "This is not your post!");
                }
                model.addAttribute("post", post);
            }
        return "edit";
    }
}
