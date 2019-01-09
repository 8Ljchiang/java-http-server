package com.github.chiangj8L.WebServer.Route;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

import com.github.chiangj8L.WebServer.Request.IRequest;
import com.github.chiangj8L.WebServer.Response.IResponse;
import com.github.chiangj8L.WebServer.RouteController.IRouteController;

public class RouteTest {

    private Route route;
    private String method = "GET";
    private String path = "/home";
    private ArrayList<String> methodCalls = new ArrayList<>();
    private String routeControllerHandleCallString = "routeController.handle()";

    public class RouteControllerMock implements IRouteController {
        @Override
        public IResponse handle(IRequest request, IResponse response) {
            methodCalls.add(routeControllerHandleCallString);
            return response;
        }
    }

    public class RequestMock implements IRequest {

        @Override
        public String getType() {
            return null;
        }

        @Override
        public String getPath() {
            return null;
        }

        @Override
        public String getMethod() {
            return null;
        }

        @Override
        public String getBody() {
            return null;
        }

        @Override
        public HashMap<String, String> getHeaders() {
            return null;
        }

        @Override
        public String getHeader(String headerName) {
            return null;
        }

        @Override
        public String getRequestString() {
            return null;
        }
    }

    public class ResponseMock implements IResponse {

        @Override
        public String getStatus() {
            return null;
        }

        @Override
        public void setStatus(String status) {

        }

        @Override
        public void addHeader(String key, String value) {

        }

        @Override
        public HashMap<String, String> getHeaders() {
            return null;
        }

        @Override
        public void setBody(String text) {

        }

        @Override
        public String getProtocol() {
            return null;
        }

        @Override
        public void setProtocol(String protocol) {

        }

        @Override
        public String getBody() {
            return null;
        }
    }

    @Before
    public void setup() {
        IRouteController routeController = new RouteControllerMock();
        route = new Route(method, path, routeController);
    }

    @Test
    public final void testGetMethod() {
        assertEquals(method, route.getMethod());
    }

    @Test
    public final void testGetPath() {
        assertEquals(path, route.getPath());
    }

    @Test
    public final void testProcess() {
        route.process(new RequestMock(), new ResponseMock());
        assertTrue(methodCalls.contains(routeControllerHandleCallString));
    }
}
