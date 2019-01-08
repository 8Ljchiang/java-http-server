package CommandHandler;

import java.util.HashMap;

@FunctionalInterface
public interface ICommandHandlerLambda {
    void operation(HashMap<String, Object> payload);
}