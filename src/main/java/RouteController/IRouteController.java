package RouteController;

import Request.IRequest;
import Response.IResponse;

public interface IRouteController {
     IResponse handle(IRequest request, IResponse response);
}
