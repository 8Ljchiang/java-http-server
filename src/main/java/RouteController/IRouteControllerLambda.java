package RouteController;

import java.io.IOException;

import Request.IRequest;
import Response.IResponse;

public interface IRouteControllerLambda {
    IResponse operation(IRequest req, IResponse res);
}
