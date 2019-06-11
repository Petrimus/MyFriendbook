
package Myfriendbook.Repositories;

import Myfriendbook.Domain.entities.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author petri
 */
public interface PictureRepository extends JpaRepository<Picture, Long> {
    
}
