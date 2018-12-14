package Route;

import Request.IRequest;
import Response.IResponse;
import RouteController.IRouteController;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class RouteTest {

    private Route route;
    private String method = "GET";
    private String path = "/home";
    private ArrayList<String> methodCalls = new ArrayList<>();
    private String routeControllerHandleCallString = "routeController.handle()";

    public class RouteControllerMock implements IRouteController {
        @Override
        public void handle(IRequest request, IResponse response) {
            methodCalls.add(routeControllerHandleCallString);
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

        @Override
        public Boolean isValid() {
            return null;
        }
    }

    public class ResponseMock implements IResponse {

        @Override
        public void status(String status) {

        }

        @Override
        public void addHeader(String key, String value) {

        }

        @Override
        public void contentType(String type) {

        }

        @Override
        public void send(String body) {

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
    public final void testProcess() throws IOException {
        route.process(new RequestMock(), new ResponseMock());
        assertTrue(methodCalls.contains(routeControllerHandleCallString));
    }
}
