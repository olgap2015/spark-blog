package com.olgaivancic.blog.dao;

import com.olgaivancic.blog.model.BlogEntry;
import com.olgaivancic.blog.model.Tag;

import java.util.List;
import java.util.Set;

public interface BlogDao {
    boolean addEntry(BlogEntry blogEntry);
    List<BlogEntry> findAllEntries();
    Set<Tag> findAllTags();
    BlogEntry findEntryBySlug(String slug);
    List<BlogEntry> findEntriesByTagSlug(String tagSlug);
    boolean deleteEntry(BlogEntry blogEntry);

}
