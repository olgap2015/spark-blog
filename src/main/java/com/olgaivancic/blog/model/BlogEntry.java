package com.olgaivancic.blog.model;

import com.github.slugify.Slugify;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class BlogEntry {

    private String postTitle;
    private String postBody;
    private Date dateCreated;
    private String slug;
    private List<Comment> comments;
    // TODO:oi - add toString() method after creating Date field

    public BlogEntry(String title, String blogBody) {
        this.postTitle = title;
        this.postBody = blogBody;
        dateCreated = new Date();
        comments = new ArrayList<>();
        try {
            Slugify slugify = new Slugify();
            slug = slugify.slugify(title);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getPostTitle() {
        return postTitle;
    }

    public String getPostBody() {
        return postBody;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlogEntry blogEntry = (BlogEntry) o;
        return Objects.equals(postTitle, blogEntry.postTitle) &&
                Objects.equals(postBody, blogEntry.postBody);
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public String getFormattedDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy 'at' h:mm");
        String formattedDate = dateFormat.format(dateCreated);
        return formattedDate;
    }

    @Override
    public int hashCode() {

        return Objects.hash(postTitle, postBody);
    }

    public boolean addComment(Comment comment) {
        // Store these comments!
        return comments.add(comment);
    }

    public String getSlug() {
        return slug;
    }
}
