package com.codeup.springblog.models;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @Size(min = 2, message = "Title must be at least 2 characters long")
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    @Size(min = 2, message = "Body of the post must be at least 2 characters long")
    private String body;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Valid
    private User user;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name="post_likes",
            joinColumns={@JoinColumn(name="post_id")},
            inverseJoinColumns={@JoinColumn(name="user_id")}
    )
    public List<User> usersThatLiked = new ArrayList<>();

    public Post(long id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public Post() {
    }

    public Post(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean containsId(final List<User> list, final long id){
        return list.stream().map(User::getId).anyMatch(userId -> userId == id);
    }

    public void toggleUserLike(User user) {
        if (containsId(usersThatLiked, user.getId())) {
            usersThatLiked.remove(user);
        } else {
            usersThatLiked.add(user);
        }
    }

    public List<User> getUserThatLiked() {
        return usersThatLiked;
    }

    public void setUserThatLiked(List<User> usersThatLiked) {
        this.usersThatLiked = usersThatLiked;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", user=" + user +
                '}';
    }
}