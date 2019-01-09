package com.github.chiangj8L.WebServer.ConnectionManager;

import DataParser.DataParser;
import com.github.chiangj8L.WebServer.Request.IRequest;
import com.github.chiangj8L.WebServer.RequestProcessor.RequestProcessor;
import com.github.chiangj8L.WebServer.Response.IResponse;
import com.github.chiangj8L.WebServer.ResponseBuilder.ResponseBuilder;
import com.github.chiangj8L.WebServer.SocketConnection.ISocketConnection;

import java.util.HashMap;

public class ConnectionManager {

    public HashMap<String, RequestProcessor> requestProcessors = new HashMap<>();

    public ConnectionManager() { }

    public void registerRequestProcessor(String requestProtocol, RequestProcessor requestProcessor) {
        requestProcessors.put(requestProtocol, requestProcessor);
    }

    public void handleConnection(ISocketConnection connection) {
        try {
            String dataString = connection.readInputData();
            String protocol = DataParser.getProtocol(dataString);
            IRequest clientRequest = DataParser.parseDataToRequest(dataString, protocol);

            RequestProcessor requestProcessor = requestProcessors.get(protocol);
            IResponse response = requestProcessor.processRequest(clientRequest);

            String responseString = ResponseBuilder.createResponseString(response);
            connection.sendOutputData(responseString);
            connection.closeSocketConnection();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
