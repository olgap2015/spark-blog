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

    public BlogEntry(String title, String blogBody, String stringOfTags) {
        this.postTitle = title;
        this.postBody = blogBody;
        dateCreated = new Date();
        comments = new ArrayList<>();
        tags = new ArrayList<>();
        try {
            Slugify slugify = new Slugify();
            slug = slugify.slugify(title);
        } catch (IOException e) {
            e.printStackTrace();
        }
        createListOfTags(stringOfTags);
    }

    public BlogEntry(String title, String blogBody,
                     String commentAuthor, String commentText, String stringOfTags) {
        this.postTitle = title;
        this.postBody = blogBody;
        this.tags = new ArrayList<>();
        createListOfTags(stringOfTags);
        dateCreated = new Date();
        comments = new ArrayList<>();
        try {
            Slugify slugify = new Slugify();
            slug = slugify.slugify(title);
        } catch (IOException e) {
            e.printStackTrace();
        }
        comments.add(new Comment(commentAuthor, commentText));
    }

    private void createListOfTags(String stringOfTags) {
        List<String> stringTags = Arrays.asList(stringOfTags.split(","));
        stringTags.forEach(string -> {
            Tag tag = new Tag(string);
            tags.add(tag);
        });
    }

    private List<Tag> createAndReturnListOfTags(String stringOfTags) {
        List<String> stringTags = Arrays.asList(stringOfTags.split(","));
        stringTags.forEach(string -> {
            Tag tag = new Tag(string);
            tags.add(tag);
        });
        return tags;
    }

    public BlogEntry(String title, String blogBody,
                     String commentAuthor, String commentText) {
        this.postTitle = title;
        this.postBody = blogBody;
        dateCreated = new Date();
        comments = new ArrayList<>();
        tags = new ArrayList<>();
        try {
            Slugify slugify = new Slugify();
            slug = slugify.slugify(title);
        } catch (IOException e) {
            e.printStackTrace();
        }
        comments.add(new Comment(commentAuthor, commentText));
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
    // TODO:oi - something is not right with this set method. COmpare it to the createListOfTags method
    public void setTags(String stringOfTags) {
        tags = createAndReturnListOfTags(stringOfTags);
    }
}
