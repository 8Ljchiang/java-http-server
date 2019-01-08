package ServerListener;

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
