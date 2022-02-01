package org.launchcode.PostIt.controllers;

import org.launchcode.PostIt.models.ImagePost;
import org.launchcode.PostIt.models.ImgurAPI;
import org.launchcode.PostIt.models.TextPost;
import org.launchcode.PostIt.models.data.ImagePostRepository;
import org.launchcode.PostIt.models.data.PostRepository;
import org.launchcode.PostIt.models.dto.ImagePostDTO;
import org.launchcode.PostIt.models.dto.RegisterFormDTO;
import org.launchcode.PostIt.models.dto.TextPostFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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
    public String postForm(Model model){
        model.addAttribute(new TextPostFormDTO());
        return "post";
    }


    @PostMapping("/post")
    public String submitPost(@ModelAttribute @Valid TextPostFormDTO textPostFormDTO, Errors errors,
                             Model model, HttpSession session){

        if (errors.hasErrors()) {
            model.addAttribute("title", "Post");
            return "post";
        }

        TextPost newTextPost = new TextPost(textPostFormDTO.getTitle(), textPostFormDTO.getBody(), textPostFormDTO.getAnonymous());
        if(textPostFormDTO.getAnonymous() == null){
            newTextPost.setAnonymous(false);
        }

        newTextPost.setUser(authenticationController.getUserFromSession(session));
        postRepository.save(newTextPost);
        return "redirect:";
    }

    @GetMapping("/postImage")
    public String postImage(Model model) {
        model.addAttribute(new ImagePostDTO());
        return "postImage";}


    @PostMapping("/postImage")
    public String submitImagePost(@ModelAttribute @Valid ImagePostDTO imagePostDTO,
                                  Errors errors, Model model, HttpSession session){

        if (errors.hasErrors()) {
            model.addAttribute("url", "ImagePost");
            return "postImage";
        }

        ImagePost newImagePost = new ImagePost(imagePostDTO.getTitle(), imagePostDTO.getUrl(), imagePostDTO.getAnonymous());
        if(imagePostDTO.getAnonymous() == null){
            newImagePost.setAnonymous(false);
        }

        newImagePost.setUrl(ImgurAPI.uploadImage(imagePostDTO.getImage()));
        newImagePost.setUser(authenticationController.getUserFromSession(session));
        imagePostRepository.save(newImagePost);
        return "redirect:";
    }
}
