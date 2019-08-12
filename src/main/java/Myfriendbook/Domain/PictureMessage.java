
package Myfriendbook.Domain;

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

public class PictureMessage {
    private Long id;    
    private String description;   
    private String name;
    private String contentType;
    private LocalDateTime timeAdded;
    private Long thumpups;
    private boolean likeable;
    private List<Comment> comments = new ArrayList<>();
}


