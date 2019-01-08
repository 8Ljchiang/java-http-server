package RouteController;

import Request.IRequest;
import Response.IResponse;

public class RouteController implements IRouteController {
    public IRouteControllerLambda routeLambda;
    public RouteController(IRouteControllerLambda lambda) {
        this.routeLambda = lambda;
    }
    public IResponse handle(IRequest request, IResponse response) {
        return this.routeLambda.operation(request, response);
    }
}
