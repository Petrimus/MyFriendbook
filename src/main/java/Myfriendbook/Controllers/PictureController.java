package Myfriendbook.Controllers;

import Myfriendbook.Domain.entities.Account;
import Myfriendbook.Domain.entities.Picture;
import Myfriendbook.Domain.forms.PictureForm;
import Myfriendbook.Repositories.AccountRepository;
import Myfriendbook.Repositories.PictureRepository;
import Myfriendbook.Services.PictureServices;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
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

    @GetMapping("/MyFriendbook/{profilename}/pictures")
    public String getPictureList(@ModelAttribute PictureForm pictureForm, Principal principal,
            Model model, @PathVariable String profilename) {
        Account account = accountRepository.findByUsername(principal.getName());
        List<Picture> pictures = account.getAlbumPictures().stream()
                .sorted((img1, img2) -> img2.getTimeAdded().compareTo(img1.getTimeAdded()))
                .collect(Collectors.toCollection(ArrayList::new));

        model.addAttribute("pictures", pictures);
        model.addAttribute("profilename", profilename);
        model.addAttribute("account", account);

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
        System.out.println(pictureForm);

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

}
