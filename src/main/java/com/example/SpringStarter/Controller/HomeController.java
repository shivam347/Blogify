package com.example.SpringStarter.Controller;

import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.SpringStarter.Models.Post;
import com.example.SpringStarter.Service.PostService;

/**
 * HomeController handles requests for the home page.
 * It retrieves all posts and passes them to the view for display.
 */
@Controller // Marks this class as a Spring MVC controller
public class HomeController {

    // Inject PostService to access post-related operations
    @Autowired
    private PostService postService;

    /**
     * Handles GET requests to "/home".
     * Adds all posts to the model so they can be displayed in the view.
     * 
     * @param model The model to pass data to the view.
     * @return The name of the view template ("home").
     */

    @GetMapping("/home")
    public String home(Model model,
            @RequestParam(name = "sort_by", defaultValue = "createdAt") String sort_by,
            @RequestParam(name = "per_page", defaultValue = "2") String per_page,
            @RequestParam(name = "page", defaultValue = "1") String page) {

        int pageNumber = Math.max(Integer.parseInt(page), 1) - 1; // Convert to

        int pageSize = Math.max(Integer.parseInt(per_page), 1);

        // First, get total pages by querying the first page
        Page<Post> tempPage = postService.findAll(0, pageSize, sort_by);
        int totalPages = tempPage.getTotalPages();

        // If requested pageNumber is too high, fallback to last available page
        if (pageNumber >= totalPages && totalPages > 0) {
            pageNumber = totalPages - 1;
        }

        Page<Post> posts_on_page = postService.findAll(pageNumber, pageSize,
                sort_by);

        List<Integer> pages = new ArrayList<>();
        if (posts_on_page.getTotalPages() > 0) {
            pages = IntStream.range(0, posts_on_page.getTotalPages())
                    .boxed().collect(Collectors.toList());
        }

        List<String> links = new ArrayList<>();
        for (int link : pages) {
            String active = (link == posts_on_page.getNumber()) ? "active" : "";
            String tempUrl = "/home?per_page=" + pageSize + "&page=" + (link + 1) +
                    "&sort_by=" + sort_by;
            String htmlLink = "<li class='page-item " + active + "'><a class='page-link' href='" + tempUrl + "'>"
                    + (link + 1) + "</a></li>";
            links.add(htmlLink);
        }

        model.addAttribute("posts", posts_on_page);
        model.addAttribute("links", links);
        model.addAttribute("sort_by", sort_by);
        model.addAttribute("per_page", pageSize); // send int, not raw string

        return "home";
    }

}
