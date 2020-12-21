package chess.ai.models.kb;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "Productions")
public class Production {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @ManyToMany
    @JoinTable(name = "production_fact",
            joinColumns = @JoinColumn(name = "production_facts"),
            inverseJoinColumns = @JoinColumn(name = "fact_name"))
    List<Fact> facts = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    Fact target;
}