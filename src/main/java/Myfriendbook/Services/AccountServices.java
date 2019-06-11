
package Myfriendbook.Services;

import Myfriendbook.Domain.entities.Account;
import Myfriendbook.Repositories.AccountRepository;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author petri
 */
@Service
public class AccountServices {
    
    @Autowired
    private AccountRepository accountRepository;
    
    
    
    public List<Account> searchFindings(String name) {        
        
        List<Account> profilename = accountRepository.findByProfilenameStartingWith(name);
        List<Account> realname = accountRepository.findByNameStartingWith(name);
        
        List<Account> newList = Stream.concat(profilename.stream(), realname.stream())
                             .collect(Collectors.toList());
        
        return newList;
    }
    
}
