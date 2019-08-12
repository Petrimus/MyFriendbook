package Myfriendbook.Controllers;

import Myfriendbook.Domain.entities.Account;
import Myfriendbook.Domain.forms.CommentForm;
import Myfriendbook.Domain.forms.MessageForm;
import Myfriendbook.Repositories.AccountRepository;
import Myfriendbook.Services.ProfileServices;
import Myfriendbook.Services.RelationshipServices;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

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

    @Autowired
    private RelationshipServices relationshipServices;

    @ModelAttribute
    private MessageForm getMessageForm() {
        return new MessageForm();
    }

    @GetMapping("MyFriendbook/{profilename}")
    public String viewProfilePage(
            @ModelAttribute CommentForm commentForm,
            @PathVariable String profilename, Model model, Principal principal) {

        Account currentUser = accountrepository.findByUsername(principal.getName());
        model.addAttribute("currentuser", currentUser.getProfilename());
        model.addAttribute("profilename", currentUser.getProfilename());
        model.addAttribute("name", currentUser.getName());
        model.addAttribute("profilePicture", currentUser.getProfilePicture());
        model.addAttribute("wallMessages", profileServices.getWallMessages(currentUser, currentUser));
        model.addAttribute("friendlist", relationshipServices.findFriends(currentUser));
        model.addAttribute("ownPage", true);
        model.addAttribute("pendinglist", relationshipServices.findPending(currentUser));

        return "homepage";
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
        model.addAttribute("currentuser", currentUser.getProfilename());
        model.addAttribute("profilename", friend.getProfilename());
        model.addAttribute("name", friend.getName());
        model.addAttribute("friendId", id);
        model.addAttribute("profilePicture", friend.getProfilePicture());
        model.addAttribute("wallMessages", profileServices.getWallMessages(currentUser, friend));
        model.addAttribute("friendlist", relationshipServices.findFriends(currentUser));
        model.addAttribute("ownPage", false);

        return "homepage";
    }
}
