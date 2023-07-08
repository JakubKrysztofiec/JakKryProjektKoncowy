package pl.coderslab.bootmaven.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ImageStylingChoiceController {

    @GetMapping("/image-styling-choice")
    public String imageStylingChoice(Model model, @RequestParam("imageUrl") String imageUrl) {
        model.addAttribute("imageUrl", imageUrl);
        return "image-styling-choice";
    }
}
