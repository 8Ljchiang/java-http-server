package com.github.chiangj8L.WebServer.CommandDispatcher;

import com.github.chiangj8L.WebServer.Command.Command;
import com.github.chiangj8L.WebServer.CommandHandler.ICommandHandlerLambda;
import com.github.chiangj8L.WebServer.CommandPayloadType.CommandPayloadType;
import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CommandDispatcher {

    private int nThreads = 10;
    private ExecutorService executor;
    private HashMap<Command, ArrayList<ICommandHandlerLambda>> commands = new HashMap<>();

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

    public void process(Command commandType, HashMap<CommandPayloadType, Object> payload) {
        if (containsCommandType(commandType)) {
            ArrayList<ICommandHandlerLambda> commandHandlers = commands.get(commandType);

            for (ICommandHandlerLambda handler : commandHandlers) {
                handler.operation(payload);
            }
        }
    }

    public void processWithExecutionService(Command commandType, HashMap<CommandPayloadType, Object> payload) {
        Runnable runnableTask = () -> {
            process(commandType, payload);
        };

        executor.execute(runnableTask);
    }

    public void registerCommandHandler(Command commandType, ICommandHandlerLambda commandHandler) {
        if (containsCommandType(commandType)) {
            ArrayList<ICommandHandlerLambda> commandHandlers = commands.get(commandType);
            commandHandlers.add(commandHandler);
        } else {
            ArrayList<ICommandHandlerLambda> newCommandHandlers = new ArrayList<>();
            newCommandHandlers.add(commandHandler);
            commands.put(commandType, newCommandHandlers);
        }
    }

    public void on(Command commandType, ICommandHandlerLambda commandHandler) {
        registerCommandHandler(commandType, commandHandler);
    }

    public void stopExecution() {
        executor.shutdown();
    }

    @Contract(pure = true)
    private boolean containsCommandType(Command commandType) {
        return commands.containsKey(commandType);
    }
}
