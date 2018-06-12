package com.olgaivancic.blog.dao;

import com.olgaivancic.blog.model.BlogEntry;

import java.util.ArrayList;
import java.util.List;

public class SimpleBlogDao implements BlogDao {
    private final ArrayList<BlogEntry> blogEntries;

    public SimpleBlogDao() {
        blogEntries = new ArrayList<>();
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
    public BlogEntry findEntryBySlug(String slug) {
        return blogEntries.stream()
                .filter(blogEntry -> blogEntry.getSlug().equals(slug))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }
}
