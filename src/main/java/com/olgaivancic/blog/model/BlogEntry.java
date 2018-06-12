package com.olgaivancic.blog.model;

import java.util.Objects;

public class BlogEntry {

    private String title;
    private String body;

    public BlogEntry(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlogEntry blogEntry = (BlogEntry) o;
        return Objects.equals(title, blogEntry.title) &&
                Objects.equals(body, blogEntry.body);
    }

    @Override
    public int hashCode() {

        return Objects.hash(title, body);
    }

    public boolean addComment(Comment comment) {
        // Store these comments!
        return false;
    }
}
