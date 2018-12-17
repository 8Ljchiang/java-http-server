package CommandHandler;

import org.junit.Test;
import org.junit.Before;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class CommandHandlerTest {

    @Test
    public final void testCommandHandlerLambda() {
        String resultString = "commandHandler.operation()";
        ArrayList<String> functionCalls = new ArrayList<>();

        ICommandHandlerLambda commandHandlerLambda = (HashMap<String, Object> payload) -> {
            functionCalls.add(resultString);
        };

        CommandHandler commandHandler = new CommandHandler(commandHandlerLambda);

        HashMap<String, Object> testPayload = new HashMap<>();
        testPayload.put("port", 9000);
        commandHandler.execute(testPayload);

        assertEquals(resultString, functionCalls.get(0));
    }
}
