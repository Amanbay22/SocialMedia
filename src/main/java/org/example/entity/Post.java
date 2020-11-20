package org.example.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    @Column(columnDefinition = "TEXT")
    private String title;
    @Column(name = "short_content", columnDefinition = "TEXT")
    private String shortContent;
    @Column(columnDefinition = "TEXT")
    private String content;
    @Column(name = "post_date")
    private Date postDate;

    public Post() {
    }

    public Post(int id, User author, String title, String shortContent, String content, Date postDate) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.shortContent = shortContent;
        this.content = content;
        this.postDate = postDate;
    }
    public String getAuthorName(){
        return author!=null ? author.getFullName():"<none>";
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortContent() {
        return shortContent;
    }

    public void setShortContent(String shortContent) {
        this.shortContent = shortContent;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }
}
