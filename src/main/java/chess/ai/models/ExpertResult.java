package chess.ai.models;

import lombok.Value;

import java.util.Collection;

@Value
public class ExpertResult {

    Collection<ExpertSolution> solutions;
}
