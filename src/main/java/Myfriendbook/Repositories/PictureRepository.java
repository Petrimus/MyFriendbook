
package Myfriendbook.Repositories;

import Myfriendbook.Domain.entities.Account;
import Myfriendbook.Domain.entities.Picture;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author petri
 */
public interface PictureRepository extends JpaRepository<Picture, Long> {
    
     List<Picture> findAllByOwner(Account owner, Pageable pageable);
    
}
