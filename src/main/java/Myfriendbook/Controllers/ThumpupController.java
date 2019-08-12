package Myfriendbook.Controllers;

import Myfriendbook.Domain.entities.Account;
import Myfriendbook.Domain.entities.Message;
import Myfriendbook.Domain.entities.Picture;
import Myfriendbook.Domain.entities.Thumpup;
import Myfriendbook.Repositories.AccountRepository;
import Myfriendbook.Repositories.MessageRepository;
import Myfriendbook.Repositories.PictureRepository;
import Myfriendbook.Repositories.ThumpupRepository;
import java.security.Principal;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author petri
 */
@Controller
public class ThumpupController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ThumpupRepository thumpupRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PictureRepository pictureRepository;

    @PostMapping("/{profilename}/{friendId}/thumpup/message/{id}")
    public String addThumpupMessage(@PathVariable Long id, @PathVariable String profilename,
            @PathVariable Long friendId, Principal principal) {
        Account currentUser = accountRepository.findByUsername(principal.getName());
        Message message = messageRepository.getOne(id);

        Thumpup thumpup = new Thumpup();
        thumpup.setMessage(message);
        thumpup.setTimeAdded(LocalDateTime.now());
        thumpup.setWhoThumpups(currentUser);
        thumpupRepository.save(thumpup);

        return "redirect:/MyFriendbook/{profilename}/friend/{friendId}/";
    }

    @PostMapping("/{profilename}/{friendId}/thumpup/picture/{id}")
    public String addThumpupPicture(@PathVariable Long id, @PathVariable String profilename,
            @PathVariable Long friendId, Principal principal) {
        Account currentUser = accountRepository.findByUsername(principal.getName());
        Picture picture = pictureRepository.getOne(id);
        
        
        Thumpup thumpup = new Thumpup();        
        thumpup.setPicture(picture);
        thumpup.setTimeAdded(LocalDateTime.now());
        thumpup.setWhoThumpups(currentUser);
        thumpupRepository.save(thumpup);

        return "redirect:/MyFriendbook/{profilename}/friend/{friendId}/pictures";
    }
}
