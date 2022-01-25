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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.Date;

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

        ImgurAPI.uploadImage(imagePostDTO.getImage());
        newImagePost.setUser(authenticationController.getUserFromSession(session));
        imagePostRepository.save(newImagePost);
        return "redirect:";
    }
}
