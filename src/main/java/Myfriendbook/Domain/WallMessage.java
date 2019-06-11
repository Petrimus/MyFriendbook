
package Myfriendbook.Domain;

import Myfriendbook.Domain.entities.Account;
import Myfriendbook.Domain.entities.Comment;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author petri
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WallMessage {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime timeAdded;       
    private Account author;    
    private Long thumpups;
    private boolean likeable;    
    private List<Comment> comments = new ArrayList<>();
    
}
