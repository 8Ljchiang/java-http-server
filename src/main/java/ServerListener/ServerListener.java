package ServerListener;

import SocketConnection.ISocketConnection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListener {
    private ServerSocket serverListener;

    public ServerListener() {}

    public void bindAndListen(int port) throws IOException {
        serverListener = new ServerSocket(port);
    }

    public Socket acceptSocketConnection() throws IOException {
        return serverListener.accept();
    }

    public void closeListener() throws IOException {
        serverListener.close();
    }
}

//public class SocketManager {
//
//    ConnectionManager cm;
//    CommandDispathcer cd;
//
//    public SocketManager (ISocketListener sl) {}
//
//    public SocketManager () {}
//
//    public start() {
        // 1. Initialize listener
//        ServerSocket listener = new ServerListener();

//        while (true) {
//            handleConnection(sc);
//        }
//    }

//    public void handleConnection(ISocketConnection sc) {
        // 2. Accept connection
        // 3. Decode data
        // 4. Build request object
//        string requestString = RequestBuilder.
        // 5. Process and route request
        // 6. Build response
        // 7. Format response.
        // 8. Encode response
        // 9. Write to connection.
        // 10. Finish connection.
//    }
//}

// 1. Connection Layer
// 2. Decode Layer
// 3. Process and Router Layer
// 4. Encoding Layer
// 5. Back to connection layer