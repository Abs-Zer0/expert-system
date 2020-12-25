package chess.ai.models;

import lombok.Data;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

@Value
public class ExpertSolution {
    @NonNull
    String result;

    @NonNull
    List<String> trace;
}
