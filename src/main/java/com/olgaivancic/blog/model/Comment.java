package com.olgaivancic.blog.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Comment {
    private String commentAuthor;
    private String commentBody;
    private Date dateCreated;

    public Comment(String commentAuthor, String commentBody) {
        this.commentAuthor = commentAuthor;
        this.commentBody = commentBody;
        dateCreated = new Date();
    }

    public String getCommentAuthor() {
        return commentAuthor;
    }

    public String getCommentBody() {
        return commentBody;
    }

    public String getFormattedDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy 'at' k:mm");
        String formattedDate = dateFormat.format(dateCreated);
        return formattedDate;
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


