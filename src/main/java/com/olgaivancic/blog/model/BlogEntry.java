package com.olgaivancic.blog.model;

import com.github.slugify.Slugify;
import com.olgaivancic.blog.dao.NotFoundException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BlogEntry implements Comparable<BlogEntry> {

    private String postTitle;
    private String postBody;
    private Date dateCreated;
    private String slug;
    private List<Comment> comments;
    private List<Tag> tags;
    private boolean tagsAreEmpty = true;

    public BlogEntry(String title, String blogBody) {
        this.postTitle = title;
        this.postBody = blogBody;
        tags = new ArrayList<>();
        dateCreated = new Date();
        comments = new ArrayList<>();
        try {
            Slugify slugify = new Slugify();
            slug = slugify.slugify(title);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BlogEntry(String title, String blogBody, List<Tag> tags) {
        this.postTitle = title;
        this.postBody = blogBody;
        dateCreated = new Date();
        comments = new ArrayList<>();
        this.tags = tags;
        tagsAreEmpty = false;
        try {
            Slugify slugify = new Slugify();
            slug = slugify.slugify(title);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BlogEntry(String title, String blogBody, List<Comment> comments, List<Tag> tags) {
        this.postTitle = title;
        this.postBody = blogBody;
        this.tags = tags;
        tagsAreEmpty = false;
        dateCreated = new Date();
        this.comments = comments;
        try {
            Slugify slugify = new Slugify();
            slug = slugify.slugify(title);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BlogEntry(List<Comment> comments, String title, String blogBody) {
        this.postTitle = title;
        this.postBody = blogBody;
        dateCreated = new Date();
        this.comments = comments;
        tags = new ArrayList<>();
        try {
            Slugify slugify = new Slugify();
            slug = slugify.slugify(title);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean areTagsNotEmpty() {
        return !tagsAreEmpty;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public Tag findTagBySlug(String slug) {
        return tags.stream()
                .filter(blogEntry -> blogEntry.getSlug().equals(slug))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    public boolean addTag (Tag tag) {
        return tags.add(tag);
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy 'at' k:mm");
        String formattedDate = dateFormat.format(dateCreated);
        return formattedDate;
    }

    public String getHtmlDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-d k:mm");
        String formattedDate = dateFormat.format(dateCreated);
        return formattedDate;
    }

    @Override
    public int hashCode() {

        return Objects.hash(postTitle, postBody);
    }

    public boolean addComment(Comment comment) {
        return comments.add(comment);
    }

    public String getSlug() {
        return slug;
    }

    @Override
    public int compareTo(BlogEntry o) {
        return this.getDateCreated().compareTo(o.getDateCreated());
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
        if (this.tags.isEmpty()) {
            tagsAreEmpty = true;
        }
    }

//    public boolean isEmpty
}
