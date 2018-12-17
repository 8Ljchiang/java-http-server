package CommandDispatcher;

import CommandHandler.ICommandHandlerLambda;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class CommandDispatcher {

    private ExecutorService executor = Executors.newFixedThreadPool(10);
    private HashMap<String, ArrayList<ICommandHandlerLambda>> commands = new HashMap<>();

    public CommandDispatcher() { }

    public void process(String commandType, HashMap<String, Object> payload) {
        if (containsCommandType(commandType)) {
            System.out.println("DISPATCHER: PROCESS - " + commandType);
            ArrayList<ICommandHandlerLambda> commandHandlers = commands.get(commandType);

            for (ICommandHandlerLambda handler : commandHandlers) {
                handler.operation(payload);
            }
        } else {
            System.out.println("DISPATCHER: Invalid - " + commandType);
        }
    }

    public void registerCommandHandler(String commandType, ICommandHandlerLambda commandHandler) {
        if (containsCommandType(commandType)) {
            ArrayList<ICommandHandlerLambda> commandHandlers = commands.get(commandType);
            commandHandlers.add(commandHandler);
        } else {
            ArrayList<ICommandHandlerLambda> newCommandHandlers = new ArrayList<>();
            newCommandHandlers.add(commandHandler);
            commands.put(commandType, newCommandHandlers);
        }
    }

    public void on(String commandType, ICommandHandlerLambda commandHandler) {
        registerCommandHandler(commandType, commandHandler);
    }

    public void stopExecution() {
        executor.shutdown();
    }

    private boolean containsCommandType(String commandType) {
        return commands.containsKey(commandType);
    }
}
