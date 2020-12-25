package chess.ai.models.kb;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "Facts")
public class Fact {
    @Id
    @NotBlank
    String name = "fact";
    @NotNull
    @Min(1)
    Integer argsAmount = 1;
    @NotNull
    Boolean isTarget = false;
    @NotNull
    Boolean canRemoved = false;
}
