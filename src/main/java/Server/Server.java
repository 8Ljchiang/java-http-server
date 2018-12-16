package Server;

import Request.IRequest;
import RequestBuilder.RequestBuilder;
import Response.IResponse;
import ResponseBuilder.ResponseBuilder;
import Router.Router;

import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Server {

    private Router router;
    private Charset charset = StandardCharsets.UTF_8;

    public Server(Router router) {
        this.router = router;
    }

    public void listen(int port) {
        // 1. Initialize ServerConnectionListener.
        ServerListener serverListener = new ServerListener();
        serverListener.bindAndListen(port);

        // 2. Accept SocketConnection(s).
        Socket socket = serverListener.acceptSocketConnection();
        SocketConnection socketConnection = new SocketConnection(socket, charset);

        // 3. Read input data from SocketConnection.
        String clientRequestString = socketConnection.readFromInputStream();

        // 4. Parse input data into a Request
        IRequest clientRequest = RequestBuilder.createRequest(clientRequestString);

        // 5. Router processes Request.
        // 6. Router Returns a Response.
        IResponse response = router.handleRequest(clientRequest);

        // 7. Build response string from Response object.
        String responseString = ResponseBuilder.createResponseString(response);

        // 8. Write output data (response string) to SocketConnection.
        socketConnection.writeToOutputStream(responseString);

        // 9. Close SocketConnection.
        socketConnect.closeSocketConnection();

        // 10. Close ServerConnectionListener.
        serverListener.closeListenerSocket();
    }
}