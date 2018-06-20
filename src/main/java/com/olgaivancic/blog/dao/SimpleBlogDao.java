package com.olgaivancic.blog.dao;

import com.olgaivancic.blog.model.BlogEntry;
import com.olgaivancic.blog.model.Tag;

import java.util.ArrayList;
import java.util.List;

public class SimpleBlogDao implements BlogDao {
    private final ArrayList<BlogEntry> blogEntries;

    public SimpleBlogDao() {
        List<String> tags1 = new ArrayList<>();
        tags1.add("tag1");
        List<String> tags2 = new ArrayList<>();
        tags2.add("tag2");
        List<String> tags3 = new ArrayList<>();
        tags3.add("tag1");
        tags3.add("tag2");
        blogEntries = new ArrayList<>();
        blogEntries.add(new BlogEntry("The best day I’ve ever had",
                                  "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc ut rhoncus felis, vel tincidunt neque. Vestibulum ut metus eleifend, malesuada nisl at, scelerisque sapien. Vivamus pharetra massa libero, sed feugiat turpis efficitur at.\nCras egestas ac ipsum in posuere. Fusce suscipit, libero id malesuada placerat, orci velit semper metus, quis pulvinar sem nunc vel augue. In ornare tempor metus, sit amet congue justo porta et. Etiam pretium, sapien non fermentum consequat, dolor augue gravida lacus, non accumsan lorem odio id risus. Vestibulum pharetra tempor molestie. Integer sollicitudin ante ipsum, a luctus nisi egestas eu. Cras accumsan cursus ante, non dapibus tempor.",
                             "Carling Kirk",
                               "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc ut rhoncus felis, vel tincidunt neque. Vestibulum ut metus eleifend, malesuada nisl at, scelerisque sapien. Vivamus pharetra massa libero, sed feugiat turpis efficitur at."));
        blogEntries.add(new BlogEntry("The absolute worst day I’ve ever had",
                                  "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc ut rhoncus felis, vel tincidunt neque. Vestibulum ut metus eleifend, malesuada nisl at, scelerisque sapien. Vivamus pharetra massa libero, sed feugiat turpis efficitur at.\nCras egestas ac ipsum in posuere. Fusce suscipit, libero id malesuada placerat, orci velit semper metus, quis pulvinar sem nunc vel augue. In ornare tempor metus, sit amet congue justo porta et. Etiam pretium, sapien non fermentum consequat, dolor augue gravida lacus, non accumsan lorem odio id risus. Vestibulum pharetra tempor molestie. Integer sollicitudin ante ipsum, a luctus nisi egestas eu. Cras accumsan cursus ante, non dapibus tempor.",
                                           tags1));
        blogEntries.add(new BlogEntry("That time at the mall",
                                  "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc ut rhoncus felis, vel tincidunt neque. Vestibulum ut metus eleifend, malesuada nisl at, scelerisque sapien. Vivamus pharetra massa libero, sed feugiat turpis efficitur at.\nCras egestas ac ipsum in posuere. Fusce suscipit, libero id malesuada placerat, orci velit semper metus, quis pulvinar sem nunc vel augue. In ornare tempor metus, sit amet congue justo porta et. Etiam pretium, sapien non fermentum consequat, dolor augue gravida lacus, non accumsan lorem odio id risus. Vestibulum pharetra tempor molestie. Integer sollicitudin ante ipsum, a luctus nisi egestas eu. Cras accumsan cursus ante, non dapibus tempor.",
                                           tags2));
        blogEntries.add(new BlogEntry("Dude, where’s my car?",
                                  "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc ut rhoncus felis, vel tincidunt neque. Vestibulum ut metus eleifend, malesuada nisl at, scelerisque sapien. Vivamus pharetra massa libero, sed feugiat turpis efficitur at.\nCras egestas ac ipsum in posuere. Fusce suscipit, libero id malesuada placerat, orci velit semper metus, quis pulvinar sem nunc vel augue. In ornare tempor metus, sit amet congue justo porta et. Etiam pretium, sapien non fermentum consequat, dolor augue gravida lacus, non accumsan lorem odio id risus. Vestibulum pharetra tempor molestie. Integer sollicitudin ante ipsum, a luctus nisi egestas eu. Cras accumsan cursus ante, non dapibus tempor.",
                                           tags3));
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

    @Override
    public List<BlogEntry> findEntriesByTagSlug(Tag tag) {
        List<BlogEntry> blogEntriesByTag = new ArrayList<>();
        blogEntries.forEach(entry -> {
            if (entry.getTags().contains(tag)) {
                blogEntriesByTag.add(entry);
            }
        });
        return blogEntriesByTag;
    }
}
