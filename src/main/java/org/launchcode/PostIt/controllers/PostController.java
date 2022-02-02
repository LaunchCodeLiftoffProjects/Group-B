package org.launchcode.PostIt.controllers;

import org.launchcode.PostIt.models.ImagePost;
import org.launchcode.PostIt.models.ImgurAPI;
import org.launchcode.PostIt.models.TextPost;
import org.launchcode.PostIt.models.data.ImagePostRepository;
import org.launchcode.PostIt.models.data.PostRepository;
import org.launchcode.PostIt.models.dto.ImagePostDTO;
import org.launchcode.PostIt.models.dto.TextPostFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Optional;

@Controller
public class PostController {
    @Autowired
    PostRepository postRepository;

    @Autowired
    ImagePostRepository imagePostRepository;

    @Autowired
    AuthenticationController authenticationController;

    @GetMapping("/post")
    public String postForm(){
        return "post";
    }

    //TODO add validation
    @PostMapping("/post")
    public String submitPost(@ModelAttribute TextPostFormDTO textPostFormDTO, HttpSession session){
        TextPost newTextPost = new TextPost(textPostFormDTO.getTitle(), textPostFormDTO.getBody(), textPostFormDTO.getAnonymous());
        if(textPostFormDTO.getAnonymous() == null){
            newTextPost.setAnonymous(false);
        }

        newTextPost.setUser(authenticationController.getUserFromSession(session));
        postRepository.save(newTextPost);
        return "redirect:";
    }

    @GetMapping("/postImage")
    public String postImage() {return "postImage";}


    @PostMapping("/postImage")
    public String submitImagePost(@ModelAttribute ImagePostDTO imagePostDTO, HttpSession session){
        ImagePost newImagePost = new ImagePost(imagePostDTO.getTitle(), imagePostDTO.getUrl(), imagePostDTO.getAnonymous());
        if(imagePostDTO.getAnonymous() == null){
            newImagePost.setAnonymous(false);
        }

        newImagePost.setUrl(ImgurAPI.uploadImage(imagePostDTO.getImage()));
        newImagePost.setUser(authenticationController.getUserFromSession(session));
        imagePostRepository.save(newImagePost);
        return "redirect:";
    }

    @GetMapping("/post/{postId}")
    public String postPage(@PathVariable int postId, Model model, HttpSession session){
        Optional<TextPost> textPost = postRepository.findById(postId);
        Optional<ImagePost> imagePost = imagePostRepository.findById(postId);
        if(textPost.isPresent()){
            model.addAttribute("post", textPost.get());
            if(imagePost.get().getUser() == authenticationController.getUserFromSession(session)){
                model.addAttribute("editButtons", true);
            }
        } else if (imagePost.isPresent()){
            model.addAttribute("post", imagePost.get());
            if(imagePost.get().getUser() == authenticationController.getUserFromSession(session)){
                model.addAttribute("editButtons", true);
            }
        } else{
            model.addAttribute("error", "No post found");
        }
        return "postView";
    }
}
