package Server;

import CommandDispatcher.CommandDispatcher;
import CommandHandler.ICommandHandlerLambda;
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
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Server {

    private Router router;
    private Boolean isBlocking;
    private ServerListener serverListener;
    private CommandDispatcher dispatcher;
    private Charset charset = StandardCharsets.UTF_8;

    public Server(Router router, Boolean isBlocking, int maxThreads) {
        this.router = router;
        this.isBlocking = isBlocking;
        this.dispatcher = new CommandDispatcher(maxThreads);
        init();
    }

    public void listen(int port) {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("port", port);
        callDispatch("listen", payload);
//        dispatcher.process("listen", payload);

        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        run();
    }

    public void on(String eventType, ICommandHandlerLambda commandHandler) {
        dispatcher.registerCommandHandler(eventType, commandHandler);
    }

    private void init() {
        ICommandHandlerLambda errorHandler = (HashMap<String, Object> payload) -> {
            this.handleError((String)payload.get("error"));
        };

        ICommandHandlerLambda listenHandler = (HashMap<String, Object> payload) -> {
            this.handleListen((int)payload.get("port"));
        };

        ICommandHandlerLambda newConnectionHandler = (HashMap<String, Object> payload) -> {
            this.handleConnection((Socket)payload.get("socket"));
        };

        ICommandHandlerLambda closeServerHandler = (HashMap<String, Object> payload) -> {
            dispatcher.stopExecution();
            this.handleCloseServer();
            // What is command for exit process();
        };

        String listenType = "listen";
        String errorType = "error";
        String connectionType = "connection";
        String closeServerType = "close";

        dispatcher.registerCommandHandler(listenType, listenHandler);
        dispatcher.registerCommandHandler(errorType, errorHandler);
        dispatcher.registerCommandHandler(connectionType, newConnectionHandler);
        dispatcher.registerCommandHandler(closeServerType, closeServerHandler);
    }

    private void run() {
        while (true) {
            // 2. Accept SocketConnection(s).
            HashMap<String, Object> payload = new HashMap<>();
            try {
                Socket socket = serverListener.acceptSocketConnection();
                payload.put("socket", socket);
            } catch (IOException e) {
                HashMap<String, Object> errorPayload = new HashMap<>();
                errorPayload.put("error", e.getMessage());
                callDispatch("error", errorPayload);
//                handleError(e.getMessage());
            } finally {
                callDispatch("connection", payload);
//                dispatcher.processWithExecutionService("connection", payload);
            }
//            acceptConnection(serverListener);
        }
    }

    private void callDispatch(String commandType, HashMap<String, Object> payload) {
        if (isBlocking) {
            dispatcher.process(commandType, payload);
        } else {
            dispatcher.processWithExecutionService(commandType, payload);
        }
    }

    private void handleCloseServer() {
        closeServerListener(serverListener);
    }

    private void handleListen(int port) {
        // 1. Initialize ServerConnectionListener.
        serverListener = new ServerListener();

        try {
            serverListener.bindAndListen(port);
        } catch (IOException e) {
            HashMap<String, Object> errorPayload = new HashMap<>();
            errorPayload.put("error", e.getMessage());
            callDispatch("error", errorPayload);
//                handleError(e.getMessage());
        }
    }

    private void handleConnection(Socket socket) {
        SocketConnection socketConnection = new SocketConnection(socket, charset);

        String inputData = receive(socketConnection);

        IResponse response = compute(inputData);

        sendResponse(response, socketConnection);

        closeConnectionWithClient(socketConnection);
    }

    private String receive(SocketConnection socketConnection) {
        return readInputData(socketConnection);
    }

    private IResponse compute(String text) {
        return routeInputData(text);
    }

    private void sendResponse(IResponse response, SocketConnection socketConnection) {
        try {
            // 7. Build response string from Response object.
            String responseString = ResponseBuilder.createResponseString(response);
            logResponse(responseString);

            // 8. Write output data (response string) to SocketConnection.
            socketConnection.writeToOutputStream(responseString);
        } catch (IOException e) {
            HashMap<String, Object> errorPayload = new HashMap<>();
            errorPayload.put("error", e.getMessage());
            callDispatch("error", errorPayload);
//                handleError(e.getMessage());
        }
    }

    private void closeConnectionWithClient(SocketConnection socketConnection) {
        try {
            // 9. Close SocketConnection.
            socketConnection.closeSocketConnection();
            logClosingSocketInfo();
        } catch (IOException e) {
            HashMap<String, Object> errorPayload = new HashMap<>();
            errorPayload.put("error", e.getMessage());
            callDispatch("error", errorPayload);
//                handleError(e.getMessage());
        }
    }

    private void closeServerListener(ServerListener serverListener) {
        try {
            // 10. Close ServerConnectionListener.
            serverListener.closeListener();
        } catch (IOException e) {
            HashMap<String, Object> errorPayload = new HashMap<>();
            errorPayload.put("error", e.getMessage());
            callDispatch("error", errorPayload);
//                handleError(e.getMessage());
        }
    }

    private String readInputData(SocketConnection socketConnection) {
        try {
            // 3. Read input data from SocketConnection.
            String clientRequestString = socketConnection.readFromInputStream();
            logRequest(clientRequestString);
            return clientRequestString;
        } catch (IOException e) {
            HashMap<String, Object> errorPayload = new HashMap<>();
            errorPayload.put("error", e.getMessage());
            callDispatch("error", errorPayload);
//            handleError(e.getMessage());
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

    private void handleError(String errorMessage) {
        System.out.println(" [[[ Error: " + errorMessage + " ]]] ");
    }

    private void logClosingSocketInfo() {
        String dashes = "-----";
        System.out.println(dashes + " closing connection " + dashes + "\n\n");
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

    //    private void showServerInfo(int port, String serverName) {
//        System.out.println(serverName + ": Listening on port " + port + "\n\n");
//    }

    //    private void acceptConnection(ServerListener serverListener) {
//        try {
//            Socket socket = serverListener.acceptSocketConnection();
//            handleConnection(socket);
//        } catch (IOException e) {
//            handleError(e.getMessage());
//        }
//    }

}