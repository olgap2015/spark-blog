package com.olgaivancic.blog.model;

import com.github.slugify.Slugify;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BlogEntry {

    private String postTitle;
    private String postBody;
    private String slug;
    private List<Comment> comments;
    // TODO:oi - add a private field Date for the BlogEntry class
    // TODO:oi - add toString() method after creating Date field

    public BlogEntry(String title, String blogBody) {
        this.postTitle = title;
        this.postBody = blogBody;
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

    @Override
    public int hashCode() {

        return Objects.hash(postTitle, postBody);
    }

    public boolean addComment(Comment comment) {
        // Store these comments!
        return false;
    }

    public String getSlug() {
        return slug;
    }
}
