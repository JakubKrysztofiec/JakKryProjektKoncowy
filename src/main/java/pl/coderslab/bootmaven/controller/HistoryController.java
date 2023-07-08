package pl.coderslab.bootmaven.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.coderslab.bootmaven.entity.CustomUserDetails;
import pl.coderslab.bootmaven.UserRepository;
import pl.coderslab.bootmaven.entity.User;
import pl.coderslab.bootmaven.service.UserService;

@Controller
public class HistoryController {

    private final UserRepository userRepository;
    private final UserService userService;

    public HistoryController(UserRepository userRepository, UserService userService){
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/user-history")
    public String viewUserHistory(Model model){
        //Gets data from the current user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        //Adds data
        model.addAttribute("userHistory", user.getUserHistory());
        model.addAttribute("userName", userName);
        return "user-history";
    }

    @GetMapping("/select-history")
    public String viewSelectHistory(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();
        User user = userService.findByEmail(currentUserEmail);

        model.addAttribute("userName", user.getEmail());
        model.addAttribute("selectHistory", user.getSelectHistory());
        return "select-history";
    }
    @GetMapping("/delete-prompt")
    public String deletePrompt(){
        return "delete-prompt";
    }

    @GetMapping("/delete-history")
    public String deleteHistory(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();
        User user = userService.findByEmail(currentUserEmail);

        user.getSelectHistory().clear();
        user.getUserHistory().clear();
        userService.saveUser(user);

        return "redirect:/user-panel";
    }
}
