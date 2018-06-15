package com.olgaivancic.blog;

import com.olgaivancic.blog.dao.SimpleBlogDao;
import spark.ModelAndView;
import spark.Request;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {

        staticFileLocation("/public");

        SimpleBlogDao dao = new SimpleBlogDao();

        // displays the Home (index) page with all the blog entries and an edit button
        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("blogEntries", dao.findAllEntries());
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());
    }
}
