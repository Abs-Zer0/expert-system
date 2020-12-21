package chess.ai.models.kb;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Entity
@Table(name = "Facts")
public class Fact {
    @Id
    @NotBlank
    String name = "fact";
    @NotNull
    @Pattern(regexp = "^([A-Z][a-zA-Z]*)(,\\s*[A-Z][a-zA-Z]*)*$", message = "Must be words with a capital latter separated by a comma")
    String args = "X";
    @NotNull
    Boolean canRemoved = false;
}
