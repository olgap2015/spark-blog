package com.olgaivancic.blog.model;

import com.github.slugify.Slugify;

import java.io.IOException;

public class Tag {
    private String tagText;
    private String slug;

    public Tag(String tagText) {
        this.tagText = tagText;
        try {
            Slugify slugify = new Slugify();
            slug = slugify.slugify(tagText);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getTagText() {
        return tagText;
    }

    public String getSlug() {
        return slug;
    }

}
