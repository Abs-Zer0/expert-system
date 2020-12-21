package chess.ai.models.db;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "Lines")
public class Line {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id = 0L;

    @NotNull
    @Size(min = 1)
    String label = "description";

    @NotNull
    Integer dx = 0;
    @NotNull
    Integer dy = 0;
    @NotNull
    @Min(1)
    Integer maxLength = 1;


    public Line inverse() {
        final Line result = new Line();
        result.dx = -this.dx;
        result.dy = -this.dy;
        result.maxLength = this.maxLength;

        return result;
    }
}
