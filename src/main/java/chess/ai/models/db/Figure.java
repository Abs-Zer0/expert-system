package chess.ai.models.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "Figures")
public class Figure {
    @Id
    @NotBlank
    String name = "figure";
    @NotNull
    @Min(1)
    Long rank = 1L;

    @ManyToMany
    @JoinTable(name = "figure_travelLine",
            joinColumns = @JoinColumn(name = "figure_name"),
            inverseJoinColumns = @JoinColumn(name = "line_id"))
    @JsonIgnore
    @NotNull
    List<Line> travelLine = new ArrayList<>();
    @ManyToMany
    @JoinTable(name = "figure_fireLine",
            joinColumns = @JoinColumn(name = "figure_name"),
            inverseJoinColumns = @JoinColumn(name = "line_id"))
    @JsonIgnore
    @NotNull
    List<Line> fireLine = new ArrayList<>();

    @org.springframework.data.annotation.Transient
    @Transient
    @NotNull
    Boolean isEnemy = false;
}
