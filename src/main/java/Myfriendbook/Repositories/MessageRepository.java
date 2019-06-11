
package Myfriendbook.Repositories;

import Myfriendbook.Domain.entities.Account;
import Myfriendbook.Domain.entities.Message;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author petri
 */
public interface MessageRepository extends JpaRepository<Message, Long> {
    
    
    List<Message> findAllByToWho(Account toWho, Pageable pageable);
    
}
