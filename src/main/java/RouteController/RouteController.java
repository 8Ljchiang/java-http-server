package RouteController;

import Request.IRequest;
import Request.Request;
import Response.IResponse;
import Response.Response;

import java.io.IOException;

public class RouteController implements IRouteController {
    public IRouteControllerLambda routeLambda;
    public RouteController(IRouteControllerLambda lambda) {
        this.routeLambda = lambda;
    }
    public IResponse handle(IRequest request, IResponse response) {
        return this.routeLambda.operation(request, response);
    }
}
