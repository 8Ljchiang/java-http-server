package Server;

import Request.IRequest;
import RequestBuilder.RequestBuilder;
import Response.IResponse;
import ResponseBuilder.ResponseBuilder;
import Router.Router;
import ServerListener.ServerListener;
import SocketConnection.SocketConnection;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class Server {

    private Router router;
    private Charset charset = StandardCharsets.UTF_8;

    public Server(Router router) {
        this.router = router;
    }

    public void listen(int port) throws IOException {
        // 1. Initialize ServerConnectionListener.
        ServerListener serverListener = new ServerListener();
        serverListener.bindAndListen(port);
        showServerInfo(port, "JAVA-HTTP-SERVER");

        try {
            while (true) {
                // 2. Accept SocketConnection(s).
                Socket socket = serverListener.acceptSocketConnection();
                SocketConnection socketConnection = new SocketConnection(socket, charset);
                try {
                    // 3. Read input data from SocketConnection.
                    String clientRequestString = socketConnection.readFromInputStream();
                    logRequest(clientRequestString);

                    // 4. Parse input data into a Request
                    IRequest clientRequest = RequestBuilder.createRequest(clientRequestString);

                    // 5. Router processes Request.
                    // 6. Router Returns a Response.
                    IResponse response = router.handleRequest(clientRequest);

                    // 7. Build response string from Response object.
                    String responseString = ResponseBuilder.createResponseString(response);

                    // 8. Write output data (response string) to SocketConnection.
                    socketConnection.writeToOutputStream(responseString);
                } finally {
                    // 9. Close SocketConnection.
                    socketConnection.closeSocketConnection();
                }
            }
        } finally {
            // 10. Close ServerConnectionListener.
            serverListener.closeListener();
            showClosingSocketInfo();
        }
    }

    private void showClosingSocketInfo() {
        String dashes = "-----";
        System.out.println(dashes + " closing connection " + dashes + "\n\n");
    }

    private void showServerInfo(int port, String serverName) {
        System.out.println(serverName + ": Listening on port " + port + "\n\n");
    }

    private void logRequest(String request) {
        Date now = new Date();
        String dashes = "-----";

        System.out.println(dashes + " client connected on " + now.toString() + " " + dashes);
        System.out.println(" *** Server: Logging Client Request *** ");
        System.out.println(request);
    }
}