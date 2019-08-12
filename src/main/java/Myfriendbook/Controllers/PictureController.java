package Myfriendbook.Controllers;

import Myfriendbook.Domain.entities.Account;
import Myfriendbook.Domain.entities.Comment;
import Myfriendbook.Domain.entities.Picture;
import Myfriendbook.Domain.forms.CommentForm;
import Myfriendbook.Domain.forms.PictureForm;
import Myfriendbook.Repositories.AccountRepository;
import Myfriendbook.Repositories.CommentRepository;
import Myfriendbook.Repositories.PictureRepository;
import Myfriendbook.Repositories.RelationshipRepository;
import Myfriendbook.Services.PictureServices;
import Myfriendbook.Services.ProfileServices;
import Myfriendbook.Services.RelationshipServices;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author petri
 */
@Controller
public class PictureController {

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PictureServices pictureServices;

    @Autowired
    private ProfileServices profileServices;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private RelationshipServices relationshipServices;

    @GetMapping("/MyFriendbook/{profilename}/pictures")
    public String getPictureList(@ModelAttribute PictureForm pictureForm,
            @ModelAttribute CommentForm commentForm,
            Principal principal,
            Model model, @PathVariable String profilename) {
        Account currentUser = accountRepository.findByUsername(principal.getName());
        Account whosPictures = accountRepository.findByProfilename(profilename);

        model.addAttribute("currentUserProfilename", currentUser.getProfilename());
        // model.addAttribute("pictures", pictures);
        model.addAttribute("profilename", whosPictures.getProfilename());
        model.addAttribute("friendId", whosPictures.getId());
        model.addAttribute("profilePicture", currentUser.getProfilePicture());
        model.addAttribute("name", currentUser.getName());
        model.addAttribute("pictureMessages", pictureServices.getPictureMessages(currentUser, whosPictures));
        model.addAttribute("friendlist", relationshipServices.findFriends(currentUser));
        model.addAttribute("ownPage", true);

        return "picturealbum";
    }

    @GetMapping("/MyFriendbook/{profilename}/friend/{id}/pictures")
    public String getPictureList(@ModelAttribute PictureForm pictureForm,
            @ModelAttribute CommentForm commentForm,
            Principal principal,
            Model model, @PathVariable String profilename,
            @PathVariable Long id) {

        Account currentUser = accountRepository.findByUsername(principal.getName());
        Account whosPictures = accountRepository.getOne(id);

        if (!profileServices.isAllowed(currentUser, whosPictures)) {
            return "redirect:/MyFriendbook/{profilename}";
        }        

        model.addAttribute("currentUserProfilename", currentUser.getProfilename());
        // model.addAttribute("pictures", pictures);
        model.addAttribute("profilename", whosPictures.getProfilename());
        model.addAttribute("friendId", whosPictures.getId());
        model.addAttribute("profilePicture", whosPictures.getProfilePicture());
        model.addAttribute("pictureMessages", pictureServices.getPictureMessages(currentUser, whosPictures));
        model.addAttribute("friendlist", relationshipServices.findFriends(currentUser));
        model.addAttribute("ownPage", false);

        return "picturealbum";
    }

    @GetMapping("/pictures/{id}/content")
    @ResponseBody
    public byte[] getPictureContent(@PathVariable Long id) {
        return pictureRepository.getOne(id).getContent();
    }

    @PostMapping("/MyFriendbook/{profilename}/pictures")
    public String saveUserPicture(@Valid @ModelAttribute PictureForm pictureForm,
            BindingResult bindingResult, Principal principal,
            @PathVariable String profilename) throws IOException {

        Account account = accountRepository.findByUsername(principal.getName());
        // System.out.println(pictureForm);

        MultipartFile file = pictureForm.getFile();

        if (file.getContentType() == null || !file.getContentType().startsWith("image")) {
            bindingResult.rejectValue("file", "error.picture", "Invalid file type");
        }

        if (bindingResult.hasErrors()) {
            return "redirect:/MyFriendbook/" + profilename + "/pictures";
        }

        Picture picture = new Picture();
        picture.setContent(file.getBytes());
        picture.setContentType(file.getContentType());
        picture.setDescription(pictureForm.getDescription());
        picture.setName(file.getName());
        picture.setOwner(account);
        picture.setTimeAdded(LocalDateTime.now());

        pictureServices.save(picture, account);

        return "redirect:/MyFriendbook/" + profilename + "/pictures";
    }

    @PostMapping("/pictures/profileimg/{id}")
    public String setProfileimg(Principal principal, @PathVariable Long id) {
        Account currentUser = accountRepository.findByUsername(principal.getName());
        currentUser.setProfilePicture(id);
        accountRepository.save(currentUser);

        return "redirect:/MyFriendbook/" + currentUser.getProfilename() + "/pictures";
    }

    @Transactional
    @PostMapping("/pictures/delete/{id}")
    public String deleteImage(@PathVariable Long id, Principal principal) {
        Account currentUser = accountRepository.findByUsername(principal.getName());

        if (Objects.equals(currentUser.getProfilePicture(), id)) {
            currentUser.setProfilePicture(null);
        }
        pictureRepository.deleteById(id);

        return "redirect:/MyFriendbook/" + currentUser.getProfilename()
                + "/pictures";
    }

    @PostMapping("/MyFriendbook/{profilename}/picture/{id}/comment")
    public String createComment(
            @Valid @ModelAttribute CommentForm commentForm,
            BindingResult bindingResult, @PathVariable String profilename, @PathVariable Long id,
            Principal principal) {

        if (bindingResult.hasErrors()) {
            return "picturealbum";
        }
        Account from = accountRepository.findByUsername(principal.getName());
        Account to = accountRepository.findByProfilename(profilename);

        if (!profileServices.isAllowed(from, to)) {
            return "redirect:/MyFriendbook/{profilename}";
        }

        Comment comment = new Comment();
        comment.setAuthor(from);
        comment.setContent(commentForm.getContent());
        comment.setPicture(pictureRepository.getOne(id));
        comment.setTimeAdded(LocalDateTime.now());

        commentRepository.save(comment);

        if (profileServices.isProfilenameCurrentUser(profilename, from)) {
            return "redirect:/MyFriendbook/{profilename}/pictures";
        } else {
            return "redirect:/MyFriendbook/" + from.getProfilename() + "/friend/" + to.getId() + "/pictures";
        }
    }
}
