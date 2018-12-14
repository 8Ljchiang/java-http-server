package RouteController;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import Request.IRequest;
import Response.IResponse;

public class RouteControllerTest {

    public class RequestMock implements IRequest{
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

    @Test
    public final void handleCallsLambdaOperation() throws Exception {

        String methodCallString = "mockControllerLambda";
        ArrayList<String> methodCalls = new ArrayList<>();

        // 0. Create a mock lambda function for the controller constructor.
        IRouteControllerLambda mockControllerLambda = (IRequest req, IResponse res) -> {
            methodCalls.add(methodCallString);
        };

        // 1. Initialize the Route Controller.
        RouteController routeController = new RouteController(mockControllerLambda);

        // 2. Create mock Request and Response objects to pass into the handle function.
        IRequest mockRequest = new RequestMock();
        IResponse mockResponse = new ResponseMock();

        // 3. Call RouteController handle method.
        routeController.handle(mockRequest, mockResponse);

        // 4. Assert that the lambda method was called once.
        assertEquals(true, methodCalls.contains(methodCallString));
    }
}
