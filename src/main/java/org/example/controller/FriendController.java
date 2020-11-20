package org.example.controller;

import org.example.entity.Friend;
import org.example.entity.FriendRequest;
import org.example.entity.User;
import org.example.repo.FriendRepo;
import org.example.repo.FriendRequestRepo;
import org.example.repo.PostRepo;
import org.example.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class FriendController {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private FriendRequestRepo friendRequestRepo;
    @Autowired
    private FriendRepo friendRepo;

    @GetMapping("/friends")
    public String listFriend(@AuthenticationPrincipal User user,
                             Model model){
        model.addAttribute("user",user);

        List<User> request = new ArrayList<>();
        List<FriendRequest> byRequestSenderId = friendRequestRepo.findByRequestSenderId(user);
        for (FriendRequest friendRequest:byRequestSenderId){
            request.add(friendRequest.getUser());
        }
        List<Friend> friends = friendRepo.findByUser(user);

        model.addAttribute("request",request);
        model.addAttribute("friends",friends);
        return "friends";
    }
    @GetMapping("/searchFriend")
    public String searchFriend(@AuthenticationPrincipal User user,
                                @RequestParam(name = "search")String fullName,
                             Model model){
        List<User> byFullNameContaining = userRepo.findByFullNameContainingAndUsernameIsNot(fullName, user.getUsername());
        List<Friend> friends = friendRepo.findByUserAndFriend_FullNameContaining(user, fullName);

        List<User> users = new ArrayList<>();
        List<FriendRequest> all = friendRequestRepo.findAll();
        List<User> request = new ArrayList<>();
        List<FriendRequest> byRequestSenderId = friendRequestRepo.findByRequestSenderId(user);
        for (FriendRequest friendRequest:byRequestSenderId){
            request.add(friendRequest.getUser());
        }
        boolean check = true;

        for (User user1:byFullNameContaining){
            for (Friend friend:friends){
                if(friend.getFriend().getUsername().equals(user1.getUsername())){ check = false; break;}
            }
            for(FriendRequest friendRequest:all){
                    if(friendRequest.getUser().getUsername().equals(user.getUsername())
                            &&friendRequest.getRequestSenderId().getUsername().equals(user1.getUsername())
                    ){
                        check = false;
                        break;
                    }
                    else if(friendRequest.getUser().getUsername().equals(user1.getUsername())
                            &&friendRequest.getRequestSenderId().getUsername().equals(user.getUsername())){
                        check = false;
                        break;
                    }
            }
            if(check) users.add(user1);
            check = true;
        }
        model.addAttribute("request",request);
        model.addAttribute("friends", friends);
        model.addAttribute("users", users);
        model.addAttribute("user",user);
        return "search";
    }

    @PostMapping("/addFriend")
    public String addFriend(
            @RequestParam(name = "id")int id,
            @AuthenticationPrincipal User user){
        User friend = userRepo.findById(id).get();
        FriendRequest friendRequest = new FriendRequest(user,friend,new Date(System.currentTimeMillis()));
        friendRequestRepo.save(friendRequest);
        return "redirect:friends";
    }
    @PostMapping("/confirmFriend")
    public String confirmFriend(
            @RequestParam(name = "id")int id,
            @AuthenticationPrincipal User user){
        User user1 = userRepo.findById(id).get();
        Friend friend = new Friend(user,user1,new Date(System.currentTimeMillis()));
        friendRepo.save(friend);
        Friend friend1 = new Friend(user1,user,new Date(System.currentTimeMillis()));
        friendRepo.save(friend1);
        FriendRequest friendRequest = friendRequestRepo.findByUserAndRequestSenderId(user1,user);
        friendRequestRepo.delete(friendRequest);
        return "redirect:friends";
    }
    @PostMapping("/rejectFriend")
    public String rejectFriend(
            @RequestParam(name = "id")int id,
            @AuthenticationPrincipal User user){
        User user1 = userRepo.findById(id).get();
        FriendRequest friendRequest = friendRequestRepo.findByUserAndRequestSenderId(user1,user);
        friendRequestRepo.delete(friendRequest);
        return "redirect:friends";
    }
}
