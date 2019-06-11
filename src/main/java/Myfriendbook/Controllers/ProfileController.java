package Myfriendbook.Controllers;

import Myfriendbook.Domain.entities.Account;
import Myfriendbook.Domain.forms.CommentForm;
import Myfriendbook.Domain.forms.MessageForm;
import Myfriendbook.Repositories.AccountRepository;
import Myfriendbook.Services.ProfileServices;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author petri
 */
@Controller
public class ProfileController {

    @Autowired
    AccountRepository accountrepository;

    @Autowired
    private ProfileServices profileServices;

    @GetMapping("MyFriendbook/{profilename}")
    public String viewProfilePage(
            @ModelAttribute MessageForm messageForm, @ModelAttribute CommentForm commentForm,
            @PathVariable String profilename, Model model, Principal principal) {

        Account currentUser = accountrepository.findByUsername(principal.getName());
        model.addAttribute("profilename", currentUser.getProfilename());
        model.addAttribute("name", currentUser.getName());
        model.addAttribute("profilePicture", currentUser.getProfilePicture());
        model.addAttribute("wallMessages", profileServices.getWallMessages(currentUser, currentUser));

        return "homepage";
    }

    @PostMapping("/{profilename}/thumpup")
    public String thumpup(@PathVariable String profilename) {

        return null;
    }

    @GetMapping("/MyFriendbook/{profilename}/friend/{id}")
    public String viewFriend(
            @ModelAttribute MessageForm messageForm, @ModelAttribute CommentForm commentForm,
            @PathVariable String profilename, @PathVariable Long id,
            Principal principal, Model model) {

        Account currentUser = accountrepository.findByUsername(principal.getName());
        Account friend = accountrepository.getOne(id);

        if (!profileServices.isAllowed(currentUser, friend)) {
            return "redirect:MyFriendbook/{profilename}";
        }

        model.addAttribute("profilename", friend.getProfilename());
        model.addAttribute("name", friend.getName());
        model.addAttribute("profilePicture", friend.getProfilePicture());
        model.addAttribute("wallMessages", profileServices.getWallMessages(currentUser, friend));

        return "homepage";
    }
}
