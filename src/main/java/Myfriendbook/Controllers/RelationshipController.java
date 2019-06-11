package Myfriendbook.Controllers;

import Myfriendbook.Domain.entities.Account;
import Myfriendbook.Domain.entities.Relationship;
import Myfriendbook.Repositories.AccountRepository;
import Myfriendbook.Repositories.RelationshipRepository;
import Myfriendbook.Services.AccountServices;
import Myfriendbook.Services.RelationshipServices;
import java.security.Principal;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author petri
 */
@Controller
public class RelationshipController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountServices accountServices;

    @Autowired
    private RelationshipRepository relationRepository;

    @Autowired
    private RelationshipServices relationshipSevices;

    @GetMapping("/MyFriendbook/{profilename}/relationships")
    public String viewSearchpage(Model model, Principal principal) {
        Account currentUser = accountRepository.findByUsername(principal.getName());

        // model.addAttribute("searchresult", null);
        model.addAttribute("profilename", currentUser.getProfilename());
        model.addAttribute("profilepicture", currentUser.getProfilePicture());
        model.addAttribute("friendlist", relationshipSevices.findFriends(currentUser));
        model.addAttribute("pendinglist", relationshipSevices.findPending(currentUser));

        return "searchfriend";
    }

    @GetMapping("/MyFriendbook/{profilename}/relationships/find")
    public String searchForFriends(@RequestParam String name, Model model, Principal principal) {
        Account currentUser = accountRepository.findByUsername(principal.getName());

        if (name != null && !name.trim().isEmpty()) {
            model.addAttribute("name", name.trim());
            model.addAttribute("searchresults", accountRepository.findByNameContaining(name));
        }

        model.addAttribute("profilename", currentUser.getProfilename());
        model.addAttribute("profilepicture", currentUser.getProfilePicture());
        model.addAttribute("friendlist", relationshipSevices.findFriends(currentUser));
        model.addAttribute("pendinglist", relationshipSevices.findPending(currentUser));

        return "searchfriend";
    }

    @PostMapping("/MyFriendbook/{profilename}/relationship/{id}")
    public String friendRequest(Principal principal, @PathVariable Long id) {
        Account currentUser = accountRepository.findByUsername(principal.getName());
        Account friend = accountRepository.getOne(id);

        relationshipSevices.makeFriendRequest(currentUser, friend);
        return "redirect:/MyFriendbook/{profilename}/relationships";
    }

    @Transactional
    @PostMapping("/MyFriendbook/{profilename}/relationship/{id}/accept")
    public String acceptFriendRequest(@PathVariable Long id,
            @PathVariable String profilename, Principal principal) {
        Account currentUser = accountRepository.findByUsername(principal.getName());
        List<Relationship> pending = currentUser.getRecievedFriendrequests();

        Relationship actualRequest = pending.stream()
                .filter(p -> Objects.equals(p.getSender().getId(), id) && p.getType() == 0)
                .findFirst().get();

        actualRequest.setType(1);

        return "redirect:/MyFriendbook/" + profilename + "/relationships";
    }

    @Transactional
    @PostMapping("/MyFriendbook/{profilename}/relationship/{id}/decline")
    public String declineFriendRequest(@PathVariable Long id,
            @PathVariable String profilename, Principal principal) {
        Account currentUser = accountRepository.findByUsername(principal.getName());
        List<Relationship> pending = currentUser.getRecievedFriendrequests();

        Relationship actualRequest = pending.stream()
                .filter(p -> Objects.equals(p.getSender().getId(), id) && p.getType() == 0)
                .findFirst().get();

        actualRequest.setType(2);

        return "redirect:/MyFriendbook/" + profilename + "/relationships";
    }
}
