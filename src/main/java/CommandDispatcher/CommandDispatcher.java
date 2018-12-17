package CommandDispatcher;

import CommandHandler.CommandHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class CommandDispatcher {

    private HashMap<String, ArrayList<CommandHandler>> commands = new HashMap<>();

    public CommandDispatcher() { }

    public void process(String commandType) {
        if (containsCommandType(commandType)) {
            ArrayList<CommandHandler> commandHandlers = commands.get(commandType);

            for (CommandHandler handler : commandHandlers) {
                handler.execute();
            }
        }
    }

    public void registerCommandHandler(String commandType, CommandHandler commandHandler) {
        if (containsCommandType(commandType)) {
            ArrayList<CommandHandler> commandHandlers = commands.get(commandType);
            commandHandlers.add(commandHandler);
        } else {
            ArrayList<CommandHandler> newCommandHandlers = new ArrayList<CommandHandler>();
            newCommandHandlers.add(commandHandler);
        }
    }

    public void on(String commandType, CommandHandler commandHandler) {
        registerCommandHandler(commandType, commandHandler);
    }

    private boolean containsCommandType(String commandType) {
        return commands.containsKey(commandType);
    }
}
