/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Myfriendbook.Services;

import Myfriendbook.Domain.PictureMessage;
import Myfriendbook.Domain.entities.Account;
import Myfriendbook.Domain.entities.Picture;
import Myfriendbook.Repositories.AccountRepository;
import Myfriendbook.Repositories.CommentRepository;
import Myfriendbook.Repositories.PictureRepository;
import Myfriendbook.Repositories.ThumpupRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author petri
 */
@Service
public class PictureServices {

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CommentRepository commentRepository;
    
    @Autowired
    private ThumpupRepository thumpupRepository;

    public List<PictureMessage> getPictureMessages(Account currentUser, Account whosPictures) {
        // Account currentUser = accountRepository.findByUsername(principal.getName());

        List<PictureMessage> pictureMessages = new ArrayList<>();
        Pageable pageableMessage = PageRequest.of(0, 10, Sort.by("timeAdded").descending());
        List<Picture> pictures = pictureRepository.findAllByOwner(whosPictures, pageableMessage);
        Pageable pageableComment = PageRequest.of(0, 10, Sort.by("timeAdded").descending());

        pictures.forEach((Picture pic) -> {
            PictureMessage picMes = new PictureMessage();
            picMes.setId(pic.getId());
            picMes.setName(pic.getName());
            picMes.setDescription(pic.getDescription());
            picMes.setComments(commentRepository.findByPicture(pic, pageableComment));
            picMes.setContentType(pic.getContentType());
            picMes.setTimeAdded(pic.getTimeAdded());
            picMes.setThumpups(thumpupRepository.countByPicture(pic));
            
            
            if (!Objects.equals(currentUser.getId(), whosPictures.getId())) {
                if (thumpupRepository.findByPictureAndWhoThumpups(pic, currentUser).isPresent()) {
                    picMes.setLikeable(false);
                } else {
                    picMes.setLikeable(true);
                }
            } else {
                picMes.setLikeable(false);
            }
            
            pictureMessages.add(picMes);
        });

        return pictureMessages;
    }

    public void save(Picture picture, Account account) {

        List<Picture> images = account.getAlbumPictures().stream()
                .sorted((img1, img2) -> img2.getTimeAdded().compareTo(img2.getTimeAdded()))
                .collect(Collectors.toCollection(ArrayList::new));

        if (images.size() >= 10) {
            images.remove(9);
        }
        pictureRepository.save(picture);
    }

}
