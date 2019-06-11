
package Myfriendbook.Domain.forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
public class SearchForm {
    
    @NotNull(message = "Name cannot be null")
    @Size(min = 3, max = 30)
    private String usernameOrProfilename;
    
}
