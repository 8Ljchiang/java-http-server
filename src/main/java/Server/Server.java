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
    private HashMap<String, Lambda>
    private Charset charset = StandardCharsets.UTF_8;

    public Server(Router router) {
        this.router = router;
    }

    public void listen(int port) throws IOException {
        // 1. Initialize ServerConnectionListener.
        ServerListener serverListener = new ServerListener();

        try {
            serverListener.bindAndListen(port);
            showServerInfo(port, "JAVA-HTTP-SERVER");

            while (true) {
                // 2. Accept SocketConnection(s).
                Socket socket = serverListener.acceptSocketConnection();
                handleIncomingConnectionThread(socket);
            }
        } catch (IOException e) {
            handleError(e.getMessage());
        } finally {
            closeServerListener(serverListener);
        }
    }

    private void handleIncomingConnectionThread(Socket socket) {
        SocketConnection socketConnection = new SocketConnection(socket, charset);

        String inputData = readInputData(socketConnection);

        IResponse response = routeInputData(inputData);

        sendResponse(response, socketConnection);

        closeConnectionWithClient(socketConnection);
    }

    private String readInputData(SocketConnection socketConnection) {
        try {
            // 3. Read input data from SocketConnection.
            String clientRequestString = socketConnection.readFromInputStream();
            logRequest(clientRequestString);
            return clientRequestString;
        } catch (IOException e) {
            handleError(e.getMessage());
            return "";
        }
    }

    private IResponse routeInputData(String clientRequestString) {
        // 4. Parse input data into a Request
        IRequest clientRequest = RequestBuilder.createRequest(clientRequestString);

        // 5. Router processes Request.
        // 6. Router Returns a Response.
        IResponse response = router.handleRequest(clientRequest);
        return response;
    }

    private void sendResponse(IResponse response, SocketConnection socketConnection) {
        try {
            // 7. Build response string from Response object.
            String responseString = ResponseBuilder.createResponseString(response);
            logResponse(responseString);

            // 8. Write output data (response string) to SocketConnection.
            socketConnection.writeToOutputStream(responseString);
        } catch (IOException e) {
            handleError(e.getMessage());
        }
    }

    private void closeConnectionWithClient(SocketConnection socketConnection) {
        try {
            // 9. Close SocketConnection.
            socketConnection.closeSocketConnection();
            showClosingSocketInfo();
        } catch (IOException e) {
            handleError(e.getMessage());
        }
    }

    private void closeServerListener(ServerListener serverListener) {
        try {
            // 10. Close ServerConnectionListener.
            serverListener.closeListener();
            showClosingSocketInfo();
        } catch (IOException e) {
            handleError(e.getMessage());
        }
    }

    private void handleError(String errorMessage) {
        System.out.println("Error: " + errorMessage);
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

        System.out.println(dashes + " client connected on " + now.toString() + " " + dashes + "\n");
        System.out.println(" **** START: Client Request **** ");
        System.out.println(request);
        System.out.println(" === END: Client Request === \n");
    }

    private void logResponse(String responseString) {
        System.out.println(" **** START: Response **** ");
        System.out.println(responseString);
        System.out.println(" === END: Response === \n");
    }
}