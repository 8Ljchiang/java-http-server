package RouteController;

import java.io.IOException;

import Request.IRequest;
import Response.IResponse;

public interface IRouteController {
     public IResponse handle(IRequest request, IResponse response);
}
