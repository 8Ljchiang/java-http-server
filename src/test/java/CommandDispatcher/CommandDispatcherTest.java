package CommandDispatcher;

import CommandHandler.ICommandHandlerLambda;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CommandDispatcherTest {

    private String listenType = "listen";
    private String errorType = "error";
    private String connectionType = "connection";
    private String listenString1 = "listenLambda1.operation";
    private String listenString2 = "listenLambda2.operation";
    private String errorString = "errorLambda.operation";
    private String connectionString = "connectionLambda.operation";
    private ArrayList<String> functionCalls = new ArrayList<>();
    private ICommandHandlerLambda listenLambda = (HashMap<String, Object> payload) -> {
        functionCalls.add(listenString1 + "(" + payload.get("test") + ")");
    };

    private ICommandHandlerLambda listenLambda2 = (HashMap<String, Object> payload) -> {
        functionCalls.add(listenString2 + "(" + payload.get("test") + ")");
    };

    private ICommandHandlerLambda connectionLambda = (HashMap<String, Object> payload) -> {
        functionCalls.add(connectionString + "(" + payload.get("test") + ")");
    };

    private ICommandHandlerLambda errorLambda = (HashMap<String, Object> payload) -> {
        functionCalls.add(errorString + "(" + payload.get("test") + ")");
    };

    @Before
    public final void setup() {
        functionCalls = new ArrayList<>();
    }

    @Test
    public final void testRegisterCommandHandler() throws IllegalAccessException, NoSuchFieldException {
        CommandDispatcher dispatcher = new CommandDispatcher();
        dispatcher.registerCommandHandler(listenType, listenLambda);
        dispatcher.registerCommandHandler(errorType, errorLambda);
        dispatcher.registerCommandHandler(connectionType, connectionLambda);
        dispatcher.registerCommandHandler(listenType, listenLambda2);

        int expectedCountOfCommandTypes = 3;
        int expectedCountOfListenHandlers = 2;
        int expectedCountOfErrorHandlers = 1;
        int expectedCountOfConnectionHandlers = 1;

        final Field field = dispatcher.getClass().getDeclaredField("commands");
        field.setAccessible(true);

        HashMap<String, ArrayList<ICommandHandlerLambda>> commands = (HashMap<String, ArrayList<ICommandHandlerLambda>>)field.get(dispatcher);

        // Check for command types.
        assertEquals(commands.size(), expectedCountOfCommandTypes);
        assertTrue(commands.containsKey(listenType));
        assertTrue(commands.containsKey(errorType));
        assertTrue(commands.containsKey(connectionType));

        // Check that there are the number of handlers for each type.
        assertEquals(commands.get(listenType).size(), expectedCountOfListenHandlers);
        assertEquals(commands.get(errorType).size(), expectedCountOfErrorHandlers);
        assertEquals(commands.get(connectionType).size(), expectedCountOfConnectionHandlers);
    }

    @Test
    public final void testOn() throws IllegalAccessException, NoSuchFieldException {
        CommandDispatcher dispatcher = new CommandDispatcher();
        dispatcher.on(listenType, listenLambda);
        dispatcher.on(errorType, errorLambda);
        dispatcher.on(connectionType, connectionLambda);
        dispatcher.on(listenType, listenLambda2);

        int expectedCountOfCommandTypes = 3;
        int expectedCountOfListenHandlers = 2;
        int expectedCountOfErrorHandlers = 1;
        int expectedCountOfConnectionHandlers = 1;

        final Field field = dispatcher.getClass().getDeclaredField("commands");
        field.setAccessible(true);

        HashMap<String, ArrayList<ICommandHandlerLambda>> commands = (HashMap<String, ArrayList<ICommandHandlerLambda>>)field.get(dispatcher);

        // Check for command types.
        assertEquals(commands.size(), expectedCountOfCommandTypes);
        assertTrue(commands.containsKey(listenType));
        assertTrue(commands.containsKey(errorType));
        assertTrue(commands.containsKey(connectionType));

        // Check that there are the number of handlers for each type.
        assertEquals(commands.get(listenType).size(), expectedCountOfListenHandlers);
        assertEquals(commands.get(errorType).size(), expectedCountOfErrorHandlers);
        assertEquals(commands.get(connectionType).size(), expectedCountOfConnectionHandlers);
    }

    @Test
    public final void testProcess() throws IllegalAccessException, NoSuchFieldException {
        CommandDispatcher dispatcher = new CommandDispatcher();

        HashMap<String, ArrayList<ICommandHandlerLambda>> commands = new HashMap<>();

        ArrayList<ICommandHandlerLambda> listenHandlers = new ArrayList<>();
        listenHandlers.add(listenLambda);
        listenHandlers.add(listenLambda2);

        ArrayList<ICommandHandlerLambda> errorHandlers = new ArrayList<>();
        errorHandlers.add(errorLambda);

        ArrayList<ICommandHandlerLambda> connectionHandlers = new ArrayList<>();
        connectionHandlers.add(connectionLambda);

        commands.put(listenType, listenHandlers);
        commands.put(errorType, errorHandlers);
        commands.put(connectionType, connectionHandlers);

        final Field field = dispatcher.getClass().getDeclaredField("commands");
        field.setAccessible(true);
        field.set(dispatcher, commands);

        String testString = "test";
        HashMap<String, Object> testPayload = new HashMap<>();
        testPayload.put(testString, testString);

        dispatcher.process(listenType, testPayload);
        dispatcher.process(connectionType, testPayload);
        dispatcher.process(connectionType, testPayload);
        dispatcher.process(errorType, testPayload);
        dispatcher.process(connectionType, testPayload);

        // Check that the lambda methods were called in the right order.
        assertEquals(listenString1 + "(" + testString + ")", functionCalls.get(0));
        assertEquals(listenString2 + "(" + testString + ")", functionCalls.get(1));
        assertEquals(connectionString + "(" + testString + ")", functionCalls.get(2));
        assertEquals(connectionString + "(" + testString + ")", functionCalls.get(3));
        assertEquals(errorString + "(" + testString + ")", functionCalls.get(4));
        assertEquals(connectionString + "(" + testString + ")", functionCalls.get(5));
    }
}
