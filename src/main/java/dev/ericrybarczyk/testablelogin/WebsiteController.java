package dev.ericrybarczyk.testablelogin;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.security.Principal;

@Controller
public class WebsiteController {

    @RequestMapping({"/","home"})
    public String home() {
        return "home";
    }

    @RequestMapping("login")
    public String login() {
        return "login";
    }

    @RequestMapping("securePage")
    public String securePage(Model model, Principal principal) {
        if (principal != null) {
            User user = (User) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
            model.addAttribute("displayName", String.format("%s %s", user.getFirstName(), user.getLastName()));
        } else {
            model.addAttribute("displayName", "Unknown User");
        }
        return "securePage";
    }
}
