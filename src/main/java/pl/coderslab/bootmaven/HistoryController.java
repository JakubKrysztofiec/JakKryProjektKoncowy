package pl.coderslab.bootmaven;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HistoryController {

    private final UserRepository userRepository;

    public HistoryController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping("/user-history")
    public String viewUserHistory(Model model){
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("userHistory", user.getUserHistory());
        return "user-history";
    }

}
