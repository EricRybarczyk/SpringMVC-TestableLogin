package dev.ericrybarczyk.testablelogin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String securePage() {
        return "securePage";
    }
}
