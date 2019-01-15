package com.github.chiangj8L.WebServer.CommandHandler;

import com.github.chiangj8L.WebServer.CommandPayloadType.CommandPayloadType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class CommandHandlerTest {

    @Test
    public final void testCommandHandlerLambda() {
        String resultString = "commandHandler.operation()";
        ArrayList<String> functionCalls = new ArrayList<>();

        ICommandHandlerLambda commandHandlerLambda = (HashMap<CommandPayloadType, Object> payload) -> {
            functionCalls.add(resultString);
        };

        CommandHandler commandHandler = new CommandHandler(commandHandlerLambda);

        HashMap<CommandPayloadType, Object> testPayload = new HashMap<>();
        testPayload.put(CommandPayloadType.PORT, 9000);
        commandHandler.execute(testPayload);

        assertEquals(resultString, functionCalls.get(0));
    }
}
