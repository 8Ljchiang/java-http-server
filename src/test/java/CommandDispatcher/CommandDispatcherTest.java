package CommandDispatcher;

import CommandHandler.ICommandHandlerLambda;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class CommandDispatcherTest {

    private String listenString = "listenLambda.operation()";
    private String errorString = "errorLambda.operation()";
    private String connectionString = "connectionLambda.operation()";
    private ArrayList<String> functionCalls = new ArrayList<>();
    private ICommandHandlerLambda listenLambda = (HashMap<String, Object> payload) -> {
        functionCalls.add(listenString);
    };

    private ICommandHandlerLambda connectionLambda = (HashMap<String, Object> payload) -> {
        functionCalls.add(connectionString);
    };

    private ICommandHandlerLambda errorLambda = (HashMap<String, Object> payload) -> {
        functionCalls.add(errorString);
    };

    @Before
    public final void setup() {
        functionCalls = new ArrayList<>();
    }

    @Test
    public final void testRegisterCommandHandler() {

    }

    @Test
    public final void testOn() {

    }

    @Test
    public final void testProcess() {

    }
}
