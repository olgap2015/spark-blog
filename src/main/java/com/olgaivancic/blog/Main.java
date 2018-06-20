package com.olgaivancic.blog;

import com.olgaivancic.blog.dao.SimpleBlogDao;
import com.olgaivancic.blog.model.BlogEntry;
import com.olgaivancic.blog.model.Comment;
import com.olgaivancic.blog.model.Tag;
import spark.ModelAndView;
import spark.Request;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.*;

import static spark.Spark.*;

public class Main {

    private static final String FLASH_MESSAGE_KEY = "flash_message";
    private static final String PASSWORD = "admin";

    public static void main(String[] args) {

        staticFileLocation("/public");

        SimpleBlogDao dao = new SimpleBlogDao();

        before((req, res) -> {
            if (req.cookie("password") != null) {
                req.attribute("password", req.cookie("password"));
            }
//            if (req.cookie("previousPage") != null) {
//                req.attribute("previousPage", req.cookie("previousPage"));
//            }
//            if (req.cookie("slug") != null) {
//                req.attribute("slug", req.cookie("slug"));
//            }
        });

        // before giving access to edit page, make sure that the user is admin
        // TODO:oi - fix the path here, add the slug
        before("/edit/*", (req, res) -> {
//            String slug = req.params("slug");
//
//            res.cookie("slug", slug);
//            res.cookie("previousPage", "edit");

            if (req.attribute("password") == null ||
                    (!req.attribute("password").equals(PASSWORD))) {
                setFlashMessage(req, "Please, sign in first");
                res.redirect("/password");
                halt();
            }
        });

        before("/new", (req, res) -> {
//            res.cookie("previousPage", "new");

            if (req.attribute("password") == null ||
                    (!req.attribute("password").equals(PASSWORD))) {
                setFlashMessage(req, "Please, sign in first");
                res.redirect("/password");
                halt();
            }
        });

        // displays the Home (index) page with all the blog entries and an edit button
        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<BlogEntry> blogEntries = dao.findAllEntries();
            Collections.sort(blogEntries);
            Collections.reverse(blogEntries);
            model.put("blogEntries", blogEntries);
            model.put("flashMessage", captureFlashMessage(req));
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        // displays a blog post page
        get("/blogposts/:slug", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("blogEntry", dao.findEntryBySlug(req.params("slug")));
            model.put("flashMessage", captureFlashMessage(req));
            return new ModelAndView(model, "detail.hbs");
        }, new HandlebarsTemplateEngine());

        // displays a page to add a new blog post
        get("/new", (req, res) -> {
            Map<String, String> model = new HashMap<>();
//            model.put("password", req.attribute("password"));
            model.put("flashMessage", captureFlashMessage(req));
            return new ModelAndView(model, "new.hbs");
        }, new HandlebarsTemplateEngine());

        // displays a page to edit an existing blog entry
        get("/edit/:slug", (req, res) -> {
            BlogEntry blogEntry = dao.findEntryBySlug(req.params("slug"));

            Map<String, Object> model = new HashMap<>();
            model.put("blogEntry", blogEntry);
//            model.put("password", req.attribute("password"));
            model.put("flashMessage", captureFlashMessage(req));

            return new ModelAndView(model, "edit.hbs");
        }, new HandlebarsTemplateEngine());

        // displays a page where user can enter the password.
        // Access is denied if the password entered is not "admin"
        get("/password", (req, res) -> {
            Map<String, String> model = new HashMap<>();
            model.put("flashMessage", captureFlashMessage(req));
            return new ModelAndView(model, "password.hbs");
        }, new HandlebarsTemplateEngine());

        post("/sign-in", (req, res) -> {
            Map<String, String> model = new HashMap<>();
            String password = req.queryParams("password");
            res.cookie("password", password);
//            model.put("password", password);
            model.put("flashMessage", captureFlashMessage(req));
            if (!password.toLowerCase().equals(PASSWORD)) {
                setFlashMessage(req, "Wrong password! Please try again!");
                res.redirect("/password");
                halt();
            }
//            String slug = req.cookie("slug");
//            String location = "/edit/" + slug;
//            if (req.attribute("previousPage").equals("edit")) {
//                res.redirect(location);
//            } else if (req.attribute("previousPage").equals("new")) {
//                res.redirect("/new");
//            }
            setFlashMessage(req, "You are now signed in! Welcome!");
            res.redirect("/");
            return null;
        });

        post("publish-new-entry", (req, res) -> {
            String title = req.queryParams("title");
            String entry = req.queryParams("entry");
            if(title.isEmpty() || entry.isEmpty()) {
                setFlashMessage(req, "Both TITLE and ENTRY are required fields!");
                res.redirect("/new");
                halt();
            }
            String stringOfTags = req.queryParams("tags");
            BlogEntry blogEntry = new BlogEntry(title, entry, stringOfTags);
            dao.addEntry(blogEntry);
            res.redirect("/");
            return null;
        });

        post("/blogposts/:slug/publish-comment", (req, res) -> {
            BlogEntry blogEntry = dao.findEntryBySlug(req.params("slug"));
            String author = req.queryParams("name");
            String commentText = req.queryParams("comment");
            if(commentText.isEmpty() || author.isEmpty()) {
                setFlashMessage(req, "Both NAME and COMMENT are required fields!");
                res.redirect("/blogposts/" + blogEntry.getSlug());
                halt();
            }
            Comment comment = new Comment(author, commentText);
            blogEntry.addComment(comment);
            res.redirect("/blogposts/" + blogEntry.getSlug());
            return null;
        });

        post("/edit/:slug", (req, res) -> {
            BlogEntry blogEntry = dao.findEntryBySlug(req.params("slug"));
            String title = req.queryParams("title");
            String entry = req.queryParams("entry");
            String stringOfTags = req.queryParams("tags");
            if(title.isEmpty() || entry.isEmpty()) {
                setFlashMessage(req, "Both TITLE and ENTRY are required fields!");
                res.redirect("/blogposts/" + blogEntry.getSlug());
                halt();
            }
            blogEntry.setPostBody(entry);
            blogEntry.setPostTitle(title);
            blogEntry.setDateCreated(new Date());
            blogEntry.setTags(stringOfTags);
            res.redirect("/blogposts/" + blogEntry.getSlug());
            return null;
        });

    }

    private static void setFlashMessage(Request req, String message) {
        req.session().attribute(FLASH_MESSAGE_KEY, message);
    }

    private static String getFlashMessage(Request req) {
        if (req.session(false) == null) {
            return null;
        }
        if(!req.session().attributes().contains(FLASH_MESSAGE_KEY)) {
            return null;
        }
        return (String) req.session().attribute(FLASH_MESSAGE_KEY);
    }

    private static String captureFlashMessage(Request req) {
        String message = getFlashMessage(req);
        if (message != null) {
            req.session().removeAttribute(FLASH_MESSAGE_KEY);
        }
        return message;
    }




}
