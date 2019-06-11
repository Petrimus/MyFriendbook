
package Myfriendbook.Repositories;

import Myfriendbook.Domain.entities.Message;
import Myfriendbook.Domain.entities.Thumpup;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author petri
 */
public interface ThumpupRepository extends JpaRepository<Thumpup, Long> {
    
    Long countByMessage(Message message);
}
