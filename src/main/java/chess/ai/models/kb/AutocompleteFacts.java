package chess.ai.models.kb;

import lombok.Value;

import java.util.List;

@Value
public class AutocompleteFacts {
    List<Fact> facts;
}
