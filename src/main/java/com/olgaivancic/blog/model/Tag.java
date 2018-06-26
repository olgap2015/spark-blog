package com.olgaivancic.blog.model;

import com.github.slugify.Slugify;

import java.io.IOException;
import java.util.Objects;

public class Tag implements Comparable<Tag> {
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

    @Override
    public String toString() {
        return "Tag{" +
                "tagText='" + tagText + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(tagText, tag.tagText);
    }

    @Override
    public int hashCode() {

        return Objects.hash(tagText, slug);
    }

    @Override
    public int compareTo(Tag o) {
        return this.getTagText().compareTo(o.getTagText());
    }
}
