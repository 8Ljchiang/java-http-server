package ConnectionManager;

import DataParser.DataParser;
import Request.IRequest;
import Response.IResponse;
import ResponseBuilder.ResponseBuilder;
import SocketConnection.ISocketConnection;

import java.io.IOException;
import java.util.HashMap;

public class ConnectionManager {

    HashMap<String, App> apps = new HashMap<>();

    public ConnectionManager() { }

    public void registerApp(String appKey, App app) {
        apps.put(appKey, app);
    }

    public void handleConnection(ISocketConnection connection) {
        try {
            String dataString = connection.readInputData();
            String protocol = DataParser.getProtocol(dataString);
            IRequest clientRequest = DataParser.parseDataToRequest(dataString, protocol);
            App app = apps.get(protocol);
            IResponse response = app.handleRequest(clientRequest);
            String responseString = ResponseBuilder.createResponseString(response);
            connection.sendOutputData(responseString);
            connection.closeSocketConnection();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            System.out.println("End of handleConnection()");
        }
    }
}
