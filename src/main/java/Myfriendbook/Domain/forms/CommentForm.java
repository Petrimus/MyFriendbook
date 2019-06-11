package Myfriendbook.Domain.forms;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author petri
 */
@Getter
@Setter
public class CommentForm {

    @NotEmpty
    @Size(min = 1, max = 1000)
    private String content;

}
