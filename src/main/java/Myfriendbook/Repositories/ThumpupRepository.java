
package Myfriendbook.Repositories;

import Myfriendbook.Domain.entities.Account;
import Myfriendbook.Domain.entities.Message;
import Myfriendbook.Domain.entities.Picture;
import Myfriendbook.Domain.entities.Thumpup;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author petri
 */
public interface ThumpupRepository extends JpaRepository<Thumpup, Long> {
    
    Long countByMessage(Message message);
    
    Long countByPicture(Picture picture);
    
    Optional<Thumpup> findByMessageAndWhoThumpups(Message message, Account whoThumoups);
    
    Optional<Thumpup> findByPictureAndWhoThumpups(Picture picture, Account whoThumoups);
}
