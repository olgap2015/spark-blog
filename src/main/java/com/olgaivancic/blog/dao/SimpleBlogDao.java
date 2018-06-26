package com.olgaivancic.blog.dao;

import com.olgaivancic.blog.model.BlogEntry;
import com.olgaivancic.blog.model.Comment;
import com.olgaivancic.blog.model.Tag;

import java.util.*;

public class SimpleBlogDao implements BlogDao {
    private final ArrayList<BlogEntry> blogEntries;

    public SimpleBlogDao() {
        String blogBody = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc ut rhoncus felis, vel tincidunt neque. Vestibulum ut metus eleifend, malesuada nisl at, scelerisque sapien. Vivamus pharetra massa libero, sed feugiat turpis efficitur at.\nCras egestas ac ipsum in posuere. Fusce suscipit, libero id malesuada placerat, orci velit semper metus, quis pulvinar sem nunc vel augue. In ornare tempor metus, sit amet congue justo porta et. Etiam pretium, sapien non fermentum consequat, dolor augue gravida lacus, non accumsan lorem odio id risus. Vestibulum pharetra tempor molestie. Integer sollicitudin ante ipsum, a luctus nisi egestas eu. Cras accumsan cursus ante, non dapibus tempor.";
        String commentBody = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc ut rhoncus felis, vel tincidunt neque. Vestibulum ut metus eleifend, malesuada nisl at, scelerisque sapien. Vivamus pharetra massa libero, sed feugiat turpis efficitur at.";
        List<Tag> tags1 = new ArrayList<>();
        Tag tag1 = new Tag("tag1");
        tags1.add(tag1);
        List<Tag> tags2 = new ArrayList<>();
        Tag tag2 = new Tag("tag2");
        tags2.add(tag2);
        List<Tag> tags3 = new ArrayList<>();
        Tag tag3 = new Tag("tag1");
        Tag tag4 = new Tag("tag2");
        tags3.add(tag3);
        tags3.add(tag4);
        Comment comment = new Comment("Carling Kirk", commentBody);
        List<Comment> comments = new ArrayList<>();
        comments.add(comment);
        blogEntries = new ArrayList<>();
        BlogEntry blogEntry1 = new BlogEntry(comments,"The best day I’ve ever had", blogBody);
        blogEntries.add(blogEntry1);
        BlogEntry blogEntry2 = new BlogEntry("The absolute worst day I’ve ever had", blogBody, tags1);
        blogEntries.add(blogEntry2);
        BlogEntry blogEntry3 = new BlogEntry("That time at the mall", blogBody, tags2);
        blogEntries.add(blogEntry3);
        BlogEntry blogEntry4 = new BlogEntry("Dude, where’s my car?", blogBody, tags3);
        blogEntries.add(blogEntry4);
    }

    @Override
    public boolean addEntry(BlogEntry blogEntry) {
        return blogEntries.add(blogEntry);
    }

    @Override
    public List<BlogEntry> findAllEntries() {
        return new ArrayList<>(blogEntries);
    }

    @Override
    public Set<Tag> findAllTags() {
        Set<Tag> tags = new TreeSet<>();
        blogEntries.forEach(entry -> tags.addAll(entry.getTags()));
        return tags;
    }

    @Override
    public BlogEntry findEntryBySlug(String slug) {
        return blogEntries.stream()
                .filter(blogEntry -> blogEntry.getSlug().equals(slug))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }


    // TODO:oi - it seems like this method is not working, it returns List containing 0 elements.
    @Override
    public List<BlogEntry> findEntriesByTagSlug(String tagSlug) {
        List<BlogEntry> blogEntriesByTag = new ArrayList<>();
        blogEntries.forEach(entry -> {
            entry.getTags().forEach(tag -> {
                if (tag.getSlug().contains(tagSlug)) {
                    blogEntriesByTag.add(entry);
                }
            });
        });
        return blogEntriesByTag;
    }

    @Override
    public boolean deleteEntry(BlogEntry blogEntry) {
        return blogEntries.remove(blogEntry);
    }
}
