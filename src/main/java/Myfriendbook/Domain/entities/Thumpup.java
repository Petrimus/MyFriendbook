
package Myfriendbook.Domain.entities;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author petri
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Thumpup extends AbstractPersistable<Long> {
    
    private LocalDateTime timeAdded;
    
    @ManyToOne
    private Picture picture;
    
    @ManyToOne
    private Message message;
}
