package Myfriendbook.Domain.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
public class Picture extends AbstractPersistable<Long> {

    private String description;
    private boolean profile = false;
    private String name;
    private String contentType;
    private LocalDateTime timeAdded;
    
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] content;

    @OneToMany(mappedBy = "picture")
    private List<Thumpup> thumpups = new ArrayList<>();

    @ManyToOne
    private Account owner;

    @Override
    public String toString() {
        return this.name;
    }
}
