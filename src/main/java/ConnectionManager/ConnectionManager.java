package ConnectionManager;

import DataParser.DataParser;
import Request.IRequest;
import RequestProcessor.RequestProcessor;
import Response.IResponse;
import ResponseBuilder.ResponseBuilder;
import SocketConnection.ISocketConnection;

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
