package Route;

import Request.IRequest;
import Response.IResponse;
import RouteController.IRouteController;

import java.io.IOException;

public class Route {
    private String method;
    private String path;
    private IRouteController controller;

    public Route(String method, String path, IRouteController routeController) {
        this.method = method;
        this.path = path;
        this.controller = routeController;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public IResponse process(IRequest req, IResponse res) throws IOException {
        return this.controller.handle(req, res);
    }
}
