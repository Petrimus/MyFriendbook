package Myfriendbook.Services;

import Myfriendbook.Domain.WallMessage;
import Myfriendbook.Domain.entities.Account;
import Myfriendbook.Domain.entities.Message;
import Myfriendbook.Domain.entities.Relationship;
import Myfriendbook.Repositories.AccountRepository;
import Myfriendbook.Repositories.CommentRepository;
import Myfriendbook.Repositories.MessageRepository;
import Myfriendbook.Repositories.RelationshipRepository;
import Myfriendbook.Repositories.ThumpupRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
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
public class ProfileServices {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private RelationshipRepository relationshipRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ThumpupRepository thumpupRepository;

    @Autowired
    private AccountRepository accountRepository;

    public List<Message> getPrincipalMessages(Account account) {
        Pageable pageableMessage = PageRequest.of(0, 25, Sort.by("timeAdded").descending());
        List<Message> messages = messageRepository.findAllByToWho(account, pageableMessage);

        return messages;
    }

    public List<WallMessage> getWallMessages(Account currentUser, Account whosMessges) {
        // Account currentUser = accountRepository.findByUsername(principal.getName());
        List<WallMessage> wallMessages = new ArrayList<>();
        Pageable pageableMessage = PageRequest.of(0, 25, Sort.by("timeAdded").descending());
        List<Message> messages = messageRepository.findAllByToWho(whosMessges, pageableMessage);
        Pageable pageableComment = PageRequest.of(0, 10, Sort.by("timeAdded").descending());

        messages.forEach((Message mes) -> {
            WallMessage wm = new WallMessage();
            wm.setId(mes.getId());
            wm.setAuthor(mes.getCreator());
            wm.setComments(commentRepository.findByMessage(mes, pageableComment));
            // System.out.println(mes.getId());
            // System.out.println(wm.getComments());
            wm.setContent(mes.getContent());
            wm.setThumpups(thumpupRepository.countByMessage(mes));
            wm.setTimeAdded(mes.getTimeAdded());
            wm.setTitle(mes.getTitle());
            
            if (!Objects.equals(currentUser.getId(), whosMessges.getId())) {
                if (thumpupRepository.findByMessageAndWhoThumpups(mes, currentUser).isPresent()) {
                    wm.setLikeable(false);
                } else {
                    wm.setLikeable(true);
                }
            } else {
                wm.setLikeable(false);
            }
            
            wallMessages.add(wm);
        });

        return wallMessages;
    }

    public boolean isAllowed(Account currentUser, Account target) {
        if (Objects.equals(currentUser.getId(), target.getId())) {
            return true;
        }

        Optional<Relationship> rel = relationshipRepository.findByIsFriend(currentUser, target);

        return rel.isPresent();
    }

    public boolean isProfilenameCurrentUser(String profilename, Account currentUser) {
        return currentUser.getProfilename().equals(profilename);
    }
}
