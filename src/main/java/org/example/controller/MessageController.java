package org.example.controller;

import org.example.entity.Chat;
import org.example.entity.Message;
import org.example.entity.User;
import org.example.repo.ChatRepo;
import org.example.repo.MessageRepo;
import org.example.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class MessageController {

    @Autowired
    private ChatRepo chatRepo;
    @Autowired
    private MessageRepo messageRepo;
    @Autowired
    private UserRepo userRepo;

    @PostMapping("/newChat")
    public String newChat(@AuthenticationPrincipal User user,
                          @RequestParam(name = "username") int id,
                          @RequestParam(name = "text") String text,
                          Model model){
        User byId = userRepo.findById(id).get();
        Chat chat = chatRepo.findByUserAndOpponentUser(user, byId);
        Chat chat2 = chatRepo.findByUserAndOpponentUser(byId, user);
        if(chat==null && chat2==null){
            Date date = new Date(System.currentTimeMillis());
            Chat chat3 = new Chat(user,byId,new Date(System.currentTimeMillis()), text, date);
            chatRepo.save(chat3);
            Message message = new Message(chat3, byId, user, text, false, date);
            messageRepo.save(message);
        }
        else if(chat!=null) {
            Date date = new Date(System.currentTimeMillis());

            chat.setLatestMessageTime(date);
            chat.setLatestMessageText(text);
            chatRepo.save(chat);
            Message message = new Message(chat, byId, user, text, false, date);
            messageRepo.save(message);
        }
        else{
            Date date = new Date(System.currentTimeMillis());
            chat2.setLatestMessageTime(date);
            chat2.setLatestMessageText(text);
            chatRepo.save(chat2);
            Message message = new Message(chat2,byId,user, text, false, date);
            messageRepo.save(message);
        }
        return "redirect:friends";
    }
    @GetMapping("/chats")
    public String showChats(@AuthenticationPrincipal User user,
            Model model){
        List<Chat> chats = chatRepo.findByUserOrOpponentUser(user, user);
        List<Chat> noRead = new ArrayList<>();
        List<Chat> read = new ArrayList<>();
        for(Chat chat:chats){
            Message message = messageRepo.findByChatAndMessageTextAndSentDate(chat,chat.getLatestMessageText(),chat.getLatestMessageTime());
            if(message!=null){
            if(!message.isReadByReceiver() && !message.getSender().getUsername().equals(user.getUsername())){
                noRead.add(chat);
            }
            else read.add(chat);}
        }
        Collections.reverse(noRead);
        Collections.reverse(read);
        model.addAttribute("noRead", noRead);
        model.addAttribute("read", read);
        model.addAttribute("user", user);
        return "chats";
    }
    @GetMapping("/message")
    public String showMessage(@AuthenticationPrincipal User user,
                              @RequestParam(name = "id") int id,
                              Model model){
        List<Message> messages = messageRepo.findByChat_Id(id);
        for(Message message:messages){
            if(message.getUser().getUsername().equals(user.getUsername())) {message.setReadByReceiver(true); messageRepo.save(message);}
        }
        Collections.reverse(messages);
        model.addAttribute("messages", messages);
        model.addAttribute("user", user);
        model.addAttribute("id", id);
        return "messages";
    }
    @PostMapping("/newMessage")
    public String newMessage(@AuthenticationPrincipal User user,
                             @RequestParam(name = "id") int id,
                             @RequestParam(name = "text") String text
                             ){
        Chat chat = chatRepo.findById(id).get();

        chat.setLatestMessageText(text);
        Date date = new Date(System.currentTimeMillis());
        chat.setLatestMessageTime(date);
        Message message;
        if(chat.getUser().getUsername().equals(user.getUsername())){
            message = new Message(chat,chat.getOpponentUser(),user,text,false,date);
        }
        else {
            message = new Message(chat,chat.getUser(),user,text,false,date);
        }
        chatRepo.save(chat);
        messageRepo.save(message);
        return "redirect:message"+"?id="+id;
    }
}
