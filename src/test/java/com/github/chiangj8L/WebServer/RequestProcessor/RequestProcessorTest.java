package com.github.chiangj8L.WebServer.RequestProcessor;

import com.github.chiangj8L.WebServer.Request.Request;
import com.github.chiangj8L.WebServer.Request.IRequest;
import com.github.chiangj8L.WebServer.Response.IResponse;
import com.github.chiangj8L.WebServer.RouteController.IRouteControllerLambda;
import com.github.chiangj8L.WebServer.RouteController.RouteController;
import com.github.chiangj8L.WebServer.Router.Router;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RequestProcessorTest {

    private RequestProcessor requestProcessor;
    private String expectedResponseBody = "{ \"response\": \"hello world\" }";
    private String expectedResponseStatus = "200 OK";
    private String headerField1 = "Handler-Name";
    private String headerValue1 = "defaultHandler";
    private String headerField2 = "Content-Type";
    private String headerValue2 = "application/json";

    @Before
    public void setup() {
        IRouteControllerLambda defaultHandlerLambda = (IRequest req, IResponse res) -> {
            res.setBody(expectedResponseBody);
            res.addHeader(headerField1, headerValue1);
            res.setStatus(expectedResponseStatus);
            res.addHeader(headerField2, headerValue2);
            return res;
        };

        RouteController defaultController = new RouteController(defaultHandlerLambda);

        Router router = new Router();
        router.get("/", defaultController);
        requestProcessor = new RequestProcessor(router);
    }

    @Test
    public void testProcessRequest() {
        // Arrange
        String method = "GET";
        String path = "/";
        String protocol = "HTTP/1.1";
        String body = "";
        String headers = "Host: localhost:5000\n" + "User-Agent: junit/5.4\n" + "Accept: */*\n";
        String requestString = method + " " + path + " " + protocol + "\n"
                + headers
                + "\n" + body;

        HashMap<String, String> headersHash = new HashMap<>();

        IRequest clientRequest = new Request(requestString, method, path, protocol, headersHash, body);

        // Act
        IResponse response = requestProcessor.processRequest(clientRequest);

        // Assert
        assertTrue(response instanceof IResponse);

        assertEquals(expectedResponseStatus, response.getStatus());
        assertEquals(expectedResponseBody, response.getBody());

        assertEquals(2, response.getHeaders().size());
        assertTrue(response.getHeaders().containsKey(headerField1));
        assertEquals(headerValue1, response.getHeaders().get(headerField1));
        assertTrue(response.getHeaders().containsKey(headerField2));
        assertEquals(headerValue2, response.getHeaders().get(headerField2));
    }
}
