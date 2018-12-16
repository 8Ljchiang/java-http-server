package RouteController;

import java.io.IOException;

import Request.IRequest;
import Response.IResponse;

public interface IRouteControllerLambda {
    public IResponse operation(IRequest req, IResponse res);
}