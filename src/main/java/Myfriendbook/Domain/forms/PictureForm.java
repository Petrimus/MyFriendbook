package Myfriendbook.Domain.forms;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author petri
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PictureForm {

    @NotBlank(message = "Description cannot be blank")
    @Size(max = 5000, message = "Text cannot be more than 5000 characters")
    private String description;

    @NotNull(message = "File cannot be null")
    private MultipartFile file;

    @Override
    public String toString() {
        return this.description;
    }
}
