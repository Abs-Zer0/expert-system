package chess.ai.models.kb;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Entity
@Table(name = "Productions")
public class Production {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id = 0L;

    @NotBlank
    String label = "description";

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z]+\\(([A-Z][a-zA-Z]*)(,\\s*[A-Z][a-zA-Z]*)*\\)$",
            message = "Выберите один факт из приведённых")
    String target = "";

    @NotBlank
    @Pattern(regexp = "^([a-zA-Z]+\\(([A-Z][a-zA-Z]*)(,\\s*[A-Z][a-zA-Z]*)*\\))(,\\s*[a-zA-Z]+\\(([A-Z][a-zA-Z]*)(,\\s*[A-Z][a-zA-Z]*)*\\))*$",
            message = "Перечислите факты через запятую из приведённых")
    String facts = "";
}
