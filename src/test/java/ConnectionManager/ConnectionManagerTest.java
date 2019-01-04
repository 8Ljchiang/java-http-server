package ConnectionManager;

import SocketConnection.ISocketConnection;
import org.junit.Before;
import org.junit.Test;


import java.io.IOException;

import static org.junit.Assert.*;

public class ConnectionManagerTest {

    private ConnectionManager connectionManager;
    private String method = "GET";
    private String path = "/";
    private String protocol = "HTTP/1.1";
    private String headers = "Host: localhost:5000\n"
            + "User-Agent: junit/5.4\n"
            + "Accept: */*\n";
    private String mockDataString = method + " " + path + " " + protocol + "\n"
            + headers;

    public class MockSocketConnection implements ISocketConnection {
        public String outputData = null;
        public boolean isClosed = false;

        public MockSocketConnection() { }

        @Override
        public String readInputData() throws IOException {
            return mockDataString;
        }

        @Override
        public void sendOutputData(String text) throws IOException {
            outputData = text;
        }

        @Override
        public void closeSocketConnection() throws IOException {
            isClosed = true;
        }
    }

    @Before
    public final void setup() {
        connectionManager = new ConnectionManager();
    }


    @Test
    public final void testHandleMethod() {
        // Arrange
        ISocketConnection mockClientConnection = new MockSocketConnection();

        // Act
        connectionManager.handleConnection(mockClientConnection);

        // Assert
        // 1. Assert that data was sent back to the connection.
        assertEquals(mockDataString, ((MockSocketConnection) mockClientConnection).outputData);

        // 2. Assert that the connection is also closed.
        assertFalse(((MockSocketConnection) mockClientConnection).isClosed);
    }

    @Test
    public final void testRegisterApp() {
        // Arrange
        App app = new App();
        String appKey = "HTTP/1.1";

        // Act
        connectionManager.registerApp(appKey, app);

        // Assert
        // 1. Assert that the app object has been added to the hashmap.
        assertTrue(connectionManager.apps.get(appKey));

        // 2. Assert that the hashmap has the key.
        assertTrue(connectionManager.apps.containsKey(appKey));
    }
}
