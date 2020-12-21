package chess.ai.models.kb;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "Facts")
@Inheritance
public class Fact {
    @Id
    String name;
    @ElementCollection
    List<String> args = new ArrayList<>();
    boolean canRemoved;
}
