package CommandHandler;

import java.util.HashMap;

@FunctionalInterface
public interface ICommandHandlerLambda {
    public void operation(HashMap<String, Object> payload);
}