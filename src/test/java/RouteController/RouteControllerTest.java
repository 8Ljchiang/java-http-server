package RouteController;

import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import Request.IRequest;
import Response.IResponse;

public class RouteControllerTest {
    @Test
    public void handleCallsLambdaOperation() {

        String methodCallString = "mockControllerLambda";
        ArrayList<String> methodCalls = new ArrayList<>();

        // 0. Create a mock lambda function for the controller constructor.
        IRouteControllerLambda mockControllerLambda = (IRequest req, IResponse res) -> {
            methodCalls.add(methodCallString);
        };

        // 1. Initialize the Route Controller.
        RouteController routeController = new RouteController(mockControllerLambda);

        // 2. Create mock Request and Reseponse objects to pass into the handle function.
        IRequest mockRequest = new RequestMock();
        IResponse mockResponse = new ResponseMock();

        // 3. Call RouteController handle method.
        routeController.handle(mockRequest, mockResponse);

        // 4. Assert that the lambda method was called once.

        // assert statements
        assertEquals(true, methodCalls.contains(methodCallString));
    }
}
