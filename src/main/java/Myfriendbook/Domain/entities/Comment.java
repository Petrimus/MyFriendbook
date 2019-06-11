
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
public class Comment extends AbstractPersistable<Long> {
    private String content;
    private LocalDateTime timeAdded;
    
    @ManyToOne
    private Account author;
    
    @ManyToOne
    private Message message;
    
    @ManyToOne
    private Picture picture;
    
    @Override
    public String toString() {
        return this.content;
    }
}
