package CommandHandler;

import java.util.HashMap;

public class CommandHandler {

    private ICommandHandlerLambda lambdaFunction;

    public CommandHandler(ICommandHandlerLambda lambdaFunc) {
        this.lambdaFunction = lambdaFunc;
    }

    public void execute(HashMap<String, Object> payload) {
        this.lambdaFunction.operation(payload);
    }
}

