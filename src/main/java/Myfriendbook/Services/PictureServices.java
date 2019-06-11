/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Myfriendbook.Services;

import Myfriendbook.Domain.entities.Account;
import Myfriendbook.Domain.entities.Picture;
import Myfriendbook.Domain.forms.PictureForm;
import Myfriendbook.Repositories.AccountRepository;
import Myfriendbook.Repositories.PictureRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
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
