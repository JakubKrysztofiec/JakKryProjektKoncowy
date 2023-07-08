package pl.coderslab.bootmaven.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.bootmaven.entity.User;
import pl.coderslab.bootmaven.service.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {
    @Autowired
    private UserService userService;

    @PostMapping
    public String showImageSearchPage(){
        return "image-search";
    }

    @GetMapping("/image-search")
    public String searchImages(@RequestParam("query") String query, Model model) {

        List<String> imageUrls = searchGoogleImages(query, 9);// Change the second argument to get the desired number of images

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();
        User user = userService.findByEmail(currentUserEmail);

        user.getUserHistory().addAll(imageUrls);
        userService.saveUser(user);

        if (!imageUrls.isEmpty()) {
            model.addAttribute("imageUrls", imageUrls);
            return "image-search";
        } else {
            return null;
        }
    }

    private List<String> searchGoogleImages(String query, int numImages) {
        List<String> imageUrls = new ArrayList<>();
        try {
            query = query.replace(" ", "+");
            String url = "https://www.google.com/search?q=" + query + "&tbm=isch";
            Document document = Jsoup.connect(url).userAgent("Mozilla/5.0").get();
            Elements elements = document.select("img[src]");
            int count = 0;
            for (Element element : elements) {
                String imageUrl = element.attr("src");
                if (imageUrl.startsWith("https://encrypted-tbn0.gstatic.com/")) {
                    imageUrls.add(imageUrl);
                    count++;
                    if (count == numImages) {
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageUrls;
    }
}
