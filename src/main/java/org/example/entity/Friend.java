package org.example.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "friends")
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "friend_id")
    private User friend;

    @Column(name = "added_time")
    private Date addedTime;

    public Friend() {
    }

    public Friend(User user, User friend, Date addedTime) {
        this.user = user;
        this.friend = friend;
        this.addedTime = addedTime;
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

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }

    public Date getAddedTime() {
        return addedTime;
    }


    public void setAddedTime(Date addedTime) {
        this.addedTime = addedTime;
    }
}
