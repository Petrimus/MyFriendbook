
package Myfriendbook.Repositories;

import Myfriendbook.Domain.entities.Account;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author petri
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
    
    Account findByUsername(String username);
    
    @Query("SELECT u FROM Account u WHERE u.username = ?1 OR u.profilename = ?2")
    Optional<Account> findByUsernameAndProfilename(String username, String profilename);
    
    List<Account> findByProfilenameStartingWith(String search);
    
    List<Account> findByNameStartingWith(String search);
    
    List<Account> findByNameContaining(String search);

    public Account findByProfilename(String profilename);
}
