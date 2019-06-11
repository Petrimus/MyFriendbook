
package Myfriendbook.Repositories;

import Myfriendbook.Domain.entities.Comment;
import Myfriendbook.Domain.entities.Message;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author petri
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
    
    public List<Comment> findByMessage(Message message, Pageable pageable);
}
