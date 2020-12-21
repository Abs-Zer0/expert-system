package chess.ai.models.kb;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "Targets")
public class Target {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id = 0L;
    @OneToOne
    @NotNull
    Fact fact;
    @NotNull
    Boolean canRemoved = false;
}
