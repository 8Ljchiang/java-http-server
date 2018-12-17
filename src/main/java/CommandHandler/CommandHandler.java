package CommandHandler;

public class CommandHandler {

    private ICommandHandlerLambda lambdaFunction;

    public CommandHandler(ICommandHandlerLambda lambdaFunc) {
        this.lambdaFunction = lambdaFunc;
    }

    public void execute() {
        this.lambdaFunction.operation();
    }
}

