package Myfriendbook.Controllers;

import Myfriendbook.Domain.entities.Account;
import Myfriendbook.Domain.entities.Comment;
import Myfriendbook.Domain.entities.Message;
import Myfriendbook.Domain.forms.CommentForm;
import Myfriendbook.Domain.forms.MessageForm;
import Myfriendbook.Repositories.AccountRepository;
import Myfriendbook.Repositories.CommentRepository;
import Myfriendbook.Repositories.MessageRepository;
import Myfriendbook.Services.ProfileServices;
import java.security.Principal;
import java.time.LocalDateTime;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author petri
 */
@Controller
public class MessageController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ProfileServices profileServices;

    @Autowired
    private CommentRepository commentRepository;

    @PostMapping("/MyFriendbook/{profilename}/message")
    public String create(
            @Valid @ModelAttribute MessageForm messageForm,
            BindingResult bindingResult, @PathVariable String profilename,
            Principal principal) {

        if (bindingResult.hasErrors()) {
            return "homepage";
        }

        Account from = accountRepository.findByUsername(principal.getName());
        Account to = accountRepository.findByProfilename(profilename);

        if (!profileServices.isAllowed(from, to)) {
            return "redirect:/MyFriendbook/{profilename}";
        }

        Message message = new Message();
        message.setContent(messageForm.getContent());
        message.setTitle(messageForm.getTitle());
        message.setTimeAdded(LocalDateTime.now());
        message.setCreator(from);
        message.setToWho(to);

        messageRepository.save(message);

        if (profileServices.isProfilenameCurrentUser(profilename, from)) {
            return "redirect:/MyFriendbook/{profilename}";
        } else {
            return "redirect:/MyFriendbook/" + from.getProfilename() + "/friend/" + to.getId();
        }
    }

    @PostMapping("/MyFriendbook/{profilename}/message/{id}/comment")
    public String createComment(
            @Valid @ModelAttribute CommentForm commentForm,
            BindingResult bindingResult, @PathVariable String profilename, @PathVariable Long id,
            Principal principal) {

        if (bindingResult.hasErrors()) {
            return "homepage";
        }
        Account from = accountRepository.findByUsername(principal.getName());
        Account to = accountRepository.findByProfilename(profilename);

        if (!profileServices.isAllowed(from, to)) {
            return "redirect:/MyFriendbook/{profilename}";
        }

        Comment comment = new Comment();
        comment.setAuthor(from);
        comment.setContent(commentForm.getContent());
        comment.setMessage(messageRepository.getOne(id));
        comment.setTimeAdded(LocalDateTime.now());

        commentRepository.save(comment);

        if (profileServices.isProfilenameCurrentUser(profilename, from)) {
            return "redirect:/MyFriendbook/{profilename}";
        } else {
            return "redirect:/MyFriendbook/" + from.getProfilename() + "/friend/" + to.getId();
        }
    }
}
