package org.example.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.example.entity.Post;
import org.example.entity.Role;
import org.example.entity.User;
import org.example.repo.PostRepo;
import org.example.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
public class SocialController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PostRepo postRepo;
    @GetMapping("/")
    public String home(){
        return "redirect:post";
    }
    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }
    @PostMapping("/registration")
    public String addUser(User user, Model model){
        User userProfile = userRepo.findByUsername(user.getUsername());
        if(userProfile!=null){
            model.addAttribute("error","User exist");
            return "registration";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepo.save(user);
        return "redirect:/login";
    }
    @GetMapping("/profile")
    public String profile(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "message", defaultValue = "null")
                    String message,
            Model model){
        if(message.equals("null")) message = null;
        model.addAttribute("message", message);
        model.addAttribute("user", user);
        return "profile";
    }
    @PostMapping("/updateProfile")
    public String update(@RequestParam(name = "fullName") String fullName,
                         @RequestParam(name = "birthDate") String birthDate,
                         @AuthenticationPrincipal User user){
        user.setFullName(fullName);
        user.setBirthDate(birthDate);
        userRepo.save(user);
        return "redirect:/profile";
    }
    @PostMapping("/updateUrl")
    public String update(@RequestParam(name = "pictureUrl") String url,
                         @AuthenticationPrincipal User user){
        user.setPictureUrl(url);
        userRepo.save(user);
        return "redirect:/profile";
    }
    @PostMapping("/updatePassword")
    public String update(@RequestParam(name = "oldPassword") String old,
                         @RequestParam(name = "newPassword") String newPass,
                         @RequestParam(name = "newPassword1") String newPass1,
                         @AuthenticationPrincipal User user,
                         Model model
                         ){
        String message;
        if(old.equals(user.getPassword())){
            if(newPass.equals(newPass1)) {user.setPassword(newPass);
                userRepo.save(user);
                return "redirect:/profile";
            }
            else{
                message= "New password not equals to re-password";
            }
        }
        else message = "Password is not correct";
        return "redirect:/profile?message="+message;
    }

    @GetMapping("/post")
    public String post(
            @AuthenticationPrincipal User user,
            Model model){
        model.addAttribute("user", user);
        List<Post> all = postRepo.findAll();
        model.addAttribute("posts",all);
        return "social";
    }
    @GetMapping("/myPost")
    public String myPost(
            @AuthenticationPrincipal User user,
            Model model){
        model.addAttribute("user", user);
        List<Post> byAuthor = postRepo.findByAuthor(user);
        model.addAttribute("posts",byAuthor);
        return "mypost";
    }
    @PostMapping("/addNews")
    public String addNews( @AuthenticationPrincipal User user,
            Post post){
        post.setAuthor(user);
        post.setPostDate(new Date(System.currentTimeMillis()));
        postRepo.save(post);
        return "redirect:/myPost";
    }
    @GetMapping("/details")
    public String details(@RequestParam(name = "id") int id,
                          @AuthenticationPrincipal User user,
                          Model model){
        Post post = postRepo.findById(id).get();
        model.addAttribute("check", user.getUsername().equals(post.getAuthor().getUsername()));
        model.addAttribute("post", post);
        return "post_details";
    }
    @GetMapping("/deletePost")
    public String deletePost(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "id") int id){
        if(user.getUsername().equals(postRepo.findById(id).get().getAuthor().getUsername())){
        postRepo.deleteById(id);
            return "redirect:myPost";
        }
        else return "redirect:post";
    }
    @GetMapping("/findOnePost")
    @ResponseBody
    public Post findOnePost(@RequestParam(name = "id") int id){
        return postRepo.findById(id).get();
    }
    @PostMapping("/savePost")
    public String savePost(
            @RequestParam(name = "id") int id,
            Post post){
        Post post1 = postRepo.findById(id).get();
        post.setPostDate(post1.getPostDate());
        post.setAuthor(post1.getAuthor());
        postRepo.save(post);
        return "redirect:details?id="+id;
    }

}
