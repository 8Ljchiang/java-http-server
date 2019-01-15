package com.github.chiangj8L.WebServer.Server;

import com.github.chiangj8L.WebServer.Command.Command;
import com.github.chiangj8L.WebServer.CommandDispatcher.CommandDispatcher;
import com.github.chiangj8L.WebServer.CommandHandler.ICommandHandlerLambda;
import com.github.chiangj8L.WebServer.CommandPayloadType.CommandPayloadType;
import com.github.chiangj8L.WebServer.Exception.InvalidRequestStringException;
import com.github.chiangj8L.WebServer.Request.IRequest;
import com.github.chiangj8L.WebServer.RequestBuilder.RequestBuilder;
import com.github.chiangj8L.WebServer.Response.IResponse;
import com.github.chiangj8L.WebServer.ResponseBuilder.ResponseBuilder;
import com.github.chiangj8L.WebServer.Router.Router;
import com.github.chiangj8L.WebServer.ServerListener.ServerListener;
import com.github.chiangj8L.WebServer.SocketConnection.SocketConnection;

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

    public Server(Router router, CommandDispatcher dispatcher) {
        this.router = router;
        this.dispatcher = dispatcher;
        init();
    }

    public void on(Command eventType, ICommandHandlerLambda commandHandler) {
        dispatcher.registerCommandHandler(eventType, commandHandler);
    }

    public void listen(int port) {
        HashMap<CommandPayloadType, Object> payload = new HashMap<>();
        payload.put(CommandPayloadType.PORT, port);
        callDispatch(Command.LISTEN, payload);

        if (!isBlocking) {
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        acceptClientConnections();
    }

    private void acceptClientConnections() {
        boolean cancelationToken = true;
        while (cancelationToken) {
            HashMap<CommandPayloadType, Object> payload = new HashMap<>();
            try {
                Socket socket = serverListener.acceptSocketConnection();
                payload.put(CommandPayloadType.SOCKET, socket);
            } catch (IOException e) {
                HashMap<CommandPayloadType, Object> errorPayload = new HashMap<>();
                errorPayload.put(CommandPayloadType.ERROR, e.getMessage());
                callDispatch(Command.ERROR, errorPayload);
            } finally {
                callDispatch(Command.CONNECTION, payload);
            }
        }
    }

    private void callDispatch(Command commandType, HashMap<CommandPayloadType, Object> payload) {
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
        serverListener = new ServerListener();

        try {
            serverListener.bindAndListen(port);
        } catch (IOException e) {
            HashMap<CommandPayloadType, Object> errorPayload = new HashMap<>();
            errorPayload.put(CommandPayloadType.ERROR, e.getMessage());
            callDispatch(Command.ERROR, errorPayload);
        }
    }

    private void handleError(String errorMessage) {
        System.out.println(" [[[ Error: " + errorMessage + " ]]] ");
    }

    private void handleConnection(Socket socket)  {
        SocketConnection socketConnection = new SocketConnection(socket, charset);

        try {
            String clientRequestString = readInputData(socketConnection);
            IRequest clientRequest = RequestBuilder.createRequest(clientRequestString);
            IResponse response = router.handleRequest(clientRequest);
            sendResponse(response, socketConnection);
        } catch (InvalidRequestStringException e) {
            HashMap<CommandPayloadType, Object> errorPayload = new HashMap<>();
            errorPayload.put(CommandPayloadType.ERROR, e.toString());
            callDispatch(Command.ERROR, errorPayload);
        } catch (IOException e) {
            HashMap<CommandPayloadType, Object> errorPayload = new HashMap<>();
            errorPayload.put(CommandPayloadType.ERROR, e.toString());
            callDispatch(Command.ERROR, errorPayload);
        } finally {
            closeConnectionWithClient(socketConnection);
        }
    }

    private void sendResponse(IResponse response, SocketConnection socketConnection) {
        try {
            String responseString = ResponseBuilder.createResponseString(response);
            logResponse(responseString);

            socketConnection.writeToOutputStream(responseString);
        } catch (IOException e) {
            HashMap<CommandPayloadType, Object> errorPayload = new HashMap<>();
            errorPayload.put(CommandPayloadType.ERROR, e.getMessage());
            callDispatch(Command.ERROR, errorPayload);
        }
    }

    private void closeConnectionWithClient(SocketConnection socketConnection) {
        try {
            socketConnection.closeSocketConnection();
            logClosingSocketInfo();
        } catch (IOException e) {
            HashMap<CommandPayloadType, Object> errorPayload = new HashMap<>();
            errorPayload.put(CommandPayloadType.ERROR, e.getMessage());
            callDispatch(Command.ERROR, errorPayload);
        }
    }

    private void closeServerListener(ServerListener serverListener) {
        try {
            serverListener.closeListener();
        } catch (IOException e) {
            HashMap<CommandPayloadType, Object> errorPayload = new HashMap<>();
            errorPayload.put(CommandPayloadType.ERROR, e.getMessage());
            callDispatch(Command.ERROR, errorPayload);
        }
    }

    private String readInputData(SocketConnection socketConnection) throws IOException {
        String clientRequestString = socketConnection.readFromInputStream();
        logRequest(clientRequestString);
        return clientRequestString;
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

    private void init() {
        ICommandHandlerLambda errorHandler = (HashMap<CommandPayloadType, Object> payload) -> {
            this.handleError((String)payload.get(CommandPayloadType.ERROR));
        };

        ICommandHandlerLambda listenHandler = (HashMap<CommandPayloadType, Object> payload) -> {
            this.handleListen((int)payload.get(CommandPayloadType.PORT));
        };

        ICommandHandlerLambda newConnectionHandler = (HashMap<CommandPayloadType, Object> payload) -> {
            this.handleConnection((Socket)payload.get(CommandPayloadType.SOCKET));
        };

        ICommandHandlerLambda closeServerHandler = (HashMap<CommandPayloadType, Object> payload) -> {
            dispatcher.stopExecution();
            this.handleCloseServer();
        };

        dispatcher.registerCommandHandler(Command.LISTEN, listenHandler);
        dispatcher.registerCommandHandler(Command.ERROR, errorHandler);
        dispatcher.registerCommandHandler(Command.CONNECTION, newConnectionHandler);
        dispatcher.registerCommandHandler(Command.CLOSE, closeServerHandler);
    }
}
