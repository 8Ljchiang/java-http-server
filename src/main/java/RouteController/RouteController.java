package RouteController;

import Request.IRequest;
import Response.IResponse;

import java.io.IOException;

public class RouteController implements IRouteController {
    public IRouteControllerLambda routeLambda;
    public RouteController(IRouteControllerLambda lambda) {
        this.routeLambda = lambda;
    }
    public void handle(IRequest request, IResponse response) throws IOException {
        this.routeLambda.operation(request, response);
    }
}
