package org.example.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "friends_requests")
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "request_sender_id")
    private User requestSenderId;

    private Date sentTime;

    public FriendRequest() {
    }

    public FriendRequest(User user, User requestSenderId, Date sentTime) {
        this.user = user;
        this.requestSenderId = requestSenderId;
        this.sentTime = sentTime;
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

    public User getRequestSenderId() {
        return requestSenderId;
    }

    public void setRequestSenderId(User requestSenderId) {
        this.requestSenderId = requestSenderId;
    }

    public Date getSentTime() {
        return sentTime;
    }

    public void setSentTime(Date sentTime) {
        this.sentTime = sentTime;
    }
}
