package Myfriendbook.Repositories;
import Myfriendbook.Domain.entities.Account;
import Myfriendbook.Domain.entities.Relationship;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author petri
 */
public interface RelationshipRepository extends JpaRepository<Relationship, Long> {

    @Query("SELECT u FROM Relationship u WHERE (u.sender = ?1 OR u.receiver = ?1) AND (u.sender = ?2 OR u.receiver = ?2) AND (u.type = 0 OR u.type = 1)")
    List<Relationship> findByRelationship(Account user, Account friend);
    
    @Query("SELECT u FROM Relationship u WHERE (u.sender = ?1 OR u.receiver = ?1) AND (u.sender = ?2 OR u.receiver = ?2) AND u.type = 0")
    Relationship findByPendingRelationship(Account user, Account friend);
    
    @Query("SELECT u FROM Relationship u WHERE (u.sender = ?1 OR u.receiver = ?1) AND (u.sender = ?2 OR u.receiver = ?2) AND u.type = 1")
    Optional<Relationship> findByIsFriend(Account user, Account friend);
    

    @Query("SELECT u FROM Relationship u WHERE (u.sender = ?1 OR u.receiver = ?1) AND u.type = 1")
    List<Relationship> findByFriends(Account user);

}
