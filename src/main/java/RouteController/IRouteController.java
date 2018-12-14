package RouteController;

import Request.IRequest;
import Response.IResponse;

import java.io.IOException;

public interface IRouteController {
     public void handle(IRequest request, IResponse response) throws IOException;
}
