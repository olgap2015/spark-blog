package com.olgaivancic.blog.model;

import java.util.Objects;

public class Comment {
    private String commentAuthor;
    private String commentBody;
    // TODO:oi - add a Date field for the Comment class as well
    // TODO:oi - add toString() method after creating Date field

    public Comment(String commentAuthor, String commentBody) {
        this.commentAuthor = commentAuthor;
        this.commentBody = commentBody;
    }

    public String getCommentAuthor() {
        return commentAuthor;
    }

    public String getCommentBody() {
        return commentBody;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(commentAuthor, comment.commentAuthor) &&
                Objects.equals(commentBody, comment.commentBody);
    }

    @Override
    public int hashCode() {

        return Objects.hash(commentAuthor, commentBody);
    }


}


