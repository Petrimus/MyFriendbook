
package Myfriendbook.Services;

import Myfriendbook.Domain.entities.Account;
import Myfriendbook.Domain.entities.Relationship;
import Myfriendbook.Repositories.AccountRepository;
import Myfriendbook.Repositories.RelationshipRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author petri
 */
@Service
public class RelationshipServices {    
    
    @Autowired
    private RelationshipRepository relationshipRepository;
    
    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    public void makeFriendRequest(Account currentUser, Account friend) {        
     List<Relationship> queed = relationshipRepository.findByRelationship(currentUser, friend);
     
     if (!queed.isEmpty()) {
         return;         
     }
     
     Relationship newRequest = new Relationship(LocalDateTime.now(), 0, currentUser, friend);
     currentUser.getSentFriendrequests().add(newRequest);
     friend.getRecievedFriendrequests().add(newRequest);
     relationshipRepository.save(newRequest);
    }

    public List<Account> findFriends(Account currentUser) {
        List<Relationship> friendRelations = relationshipRepository.findByFriends(currentUser);
        List<Account> friends = friendRelations.stream()
                .map((Relationship rel) -> {                   
                if (rel.getReceiver().getId().equals(currentUser.getId())) {
                    return rel.getSender();
                } else {
                    return rel.getReceiver();
                }                
                }).collect(Collectors.toCollection(ArrayList::new));
                
              return friends;  
    }

    public List<Account> findPending(Account currentUser) {
        List<Relationship> requests = currentUser.getRecievedFriendrequests();
        List<Account> pending = requests.stream()
                .filter(req -> req.getType() == 0)
                .map(acc -> acc.getSender())
                .collect(Collectors.toCollection(ArrayList::new));
        
        return pending;
    }
    
}
