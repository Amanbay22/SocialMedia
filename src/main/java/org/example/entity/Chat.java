package org.example.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "chats")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "opponent_user_id")
    private User opponentUser;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "latest_message_text", columnDefinition = "TEXT")
    private String latestMessageText;

    @Column(name = "latest_message_time")
    private Date latestMessageTime;

    public Chat() {
    }

    public Chat( User user, User opponentUser, Date createdDate, String latestMessageText, Date latestMessageTime) {
        this.user = user;
        this.opponentUser = opponentUser;
        this.createdDate = createdDate;
        this.latestMessageText = latestMessageText;
        this.latestMessageTime = latestMessageTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getOpponentUser() {
        return opponentUser;
    }

    public void setOpponentUser(User opponentUser) {
        this.opponentUser = opponentUser;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getLatestMessageText() {
        return latestMessageText;
    }

    public void setLatestMessageText(String latestMessageText) {
        this.latestMessageText = latestMessageText;
    }

    public Date getLatestMessageTime() {
        return latestMessageTime;
    }

    public void setLatestMessageTime(Date latestMessageTime) {
        this.latestMessageTime = latestMessageTime;
    }
}
