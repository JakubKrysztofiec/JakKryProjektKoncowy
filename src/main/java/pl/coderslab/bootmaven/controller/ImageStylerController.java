package pl.coderslab.bootmaven.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.bootmaven.entity.User;
import pl.coderslab.bootmaven.service.ImageStylerService;
import pl.coderslab.bootmaven.service.UserService;

import java.io.IOException;

@Controller
public class ImageStylerController {

    @Autowired
    private UserService userService;
    private final ImageStylerService imageStylerService = new ImageStylerService();


    @GetMapping("/image-styling")
    public String styleImage(@RequestParam("imageUrl") String imageUrl, @RequestParam("style") String style, Model model) throws IOException {
//        String styledImage = imageStylerService.styleImage(imageUrl, style);
//        model.addAttribute("styledImage", styledImage);
//        model.addAttribute("imageUrl", imageUrl);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();
        User user = userService.findByEmail(currentUserEmail);

        user.getSelectHistory().add(imageUrl);
        userService.saveUser(user);


        return "user-panel";
    }
}
