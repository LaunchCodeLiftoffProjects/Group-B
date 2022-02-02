package org.launchcode.PostIt.controllers;

import org.launchcode.PostIt.models.AbstractPost;
import org.launchcode.PostIt.models.data.ImagePostRepository;
import org.launchcode.PostIt.models.data.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Controller
public class MyPostsController {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ImagePostRepository imagePostRepository;

    @Autowired
    private AuthenticationController authController;

    @GetMapping("MyPosts")
    public String getMyPosts(Model model, HttpSession session){
        int id = authController.getUserFromSession(session).getId();

        List<AbstractPost> posts = new ArrayList<>();
        posts.addAll((Collection<? extends AbstractPost>) postRepository.findAll());
        posts.addAll((Collection<? extends AbstractPost>) imagePostRepository.findAll());

        List<AbstractPost> myPosts = new ArrayList<>();
        for(AbstractPost post : posts){
            if(post.getUser().getId() == id){
                myPosts.add(post);
            }
        }


        posts.sort(Comparator.naturalOrder());
        model.addAttribute("posts", myPosts);
        return "myPosts";
    }

}
