package com.olgaivancic.blog.dao;

import com.olgaivancic.blog.model.BlogEntry;
import com.olgaivancic.blog.model.Tag;

import java.util.List;

public interface BlogDao {
    boolean addEntry(BlogEntry blogEntry);
    List<BlogEntry> findAllEntries();
    BlogEntry findEntryBySlug(String slug);
    List<BlogEntry> findEntriesByTagSlug(Tag tag);
    boolean deleteEntry(BlogEntry blogEntry);

}
