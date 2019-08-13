package Myfriendbook.Controllers;

import Myfriendbook.Repositories.AccountRepository;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author petri
 */
@Controller
public class DefaultController {
    
    @Autowired AccountRepository accountRepository;

    @GetMapping("/")    
    public String helloWorld() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login.html";
    }

    @GetMapping("/home")
    public String goToHomepage(Principal principal) {
        String profilename = accountRepository.findByUsername(principal.getName()).getProfilename();
        return "redirect:/MyFriendbook/" + profilename;
    }

}
