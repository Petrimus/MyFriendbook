
package Myfriendbook.Domain.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;

import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
public class Message extends AbstractPersistable<Long> {
    private String title;
    private String content;
    private LocalDateTime timeAdded;    
    
    @ManyToOne
    private Account creator;
    
    @ManyToOne
    private Account toWho;
    
    @OneToMany(mappedBy = "message")
    private List<Thumpup> thumpups = new ArrayList<>();
    
    @OneToMany(mappedBy = "message")
    private List<Comment> comments = new ArrayList<>();
}
