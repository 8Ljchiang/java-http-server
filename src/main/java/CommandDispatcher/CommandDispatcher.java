package CommandDispatcher;

import CommandHandler.ICommandHandlerLambda;
import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CommandDispatcher {

    private int nThreads = 10;
    private ExecutorService executor;
    private HashMap<String, ArrayList<ICommandHandlerLambda>> commands = new HashMap<>();

    public CommandDispatcher() {
        executor = Executors.newFixedThreadPool(nThreads);
    }

    public CommandDispatcher(int maxThreads) {
        if (maxThreads > 0 && maxThreads < 50) {
            executor = Executors.newFixedThreadPool(maxThreads);
        } else {
            executor = Executors.newFixedThreadPool(nThreads);
        }
    }

    public void process(String commandType, HashMap<String, Object> payload) {
        if (containsCommandType(commandType)) {
            ArrayList<ICommandHandlerLambda> commandHandlers = commands.get(commandType);

            for (ICommandHandlerLambda handler : commandHandlers) {
                handler.operation(payload);
            }
        }
    }

    public void processWithExecutionService(String commandType, HashMap<String, Object> payload) {
        Runnable runnableTask = () -> {
            process(commandType, payload);
        };

        executor.execute(runnableTask);
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

    @Contract(pure = true)
    private boolean containsCommandType(String commandType) {
        return commands.containsKey(commandType);
    }
}
