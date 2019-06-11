
package Myfriendbook.Domain.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
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
public class Account extends AbstractPersistable<Long> {
    
    @NotEmpty
    @Size(min = 4, max = 40)
    private String username;
    @NotEmpty
    @Size(min = 4, max = 40)
    private String name;
    @NotEmpty
    @Size(min = 4, max = 40)
    private String profilename;
    @NotEmpty    
    private String password;
    
    private Long profilePicture;
    
    @OneToMany(mappedBy = "receiver")
    private List<Relationship> recievedFriendrequests = new ArrayList<>();
    
    @OneToMany(mappedBy = "sender")
    private List<Relationship> sentFriendrequests = new ArrayList<>();
    
    @OneToMany(mappedBy = "toWho")
    private List<Message> toWall = new ArrayList<>();
    
    @OneToMany(mappedBy = "creator")
    private List<Message> messages = new ArrayList<>(); 
    
    @OneToMany(mappedBy = "owner")
    private List<Picture> albumPictures = new ArrayList<>();
    
    @Override
    public String toString() {
        return this.name + " " + this.profilename;
    }
}
