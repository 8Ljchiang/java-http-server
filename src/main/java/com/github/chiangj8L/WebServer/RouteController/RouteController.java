package com.github.chiangj8L.WebServer.RouteController;

import com.github.chiangj8L.WebServer.Request.IRequest;
import com.github.chiangj8L.WebServer.Response.IResponse;

public class RouteController implements IRouteController {
    public IRouteControllerLambda routeLambda;
    public RouteController(IRouteControllerLambda lambda) {
        this.routeLambda = lambda;
    }
    public IResponse handle(IRequest request, IResponse response) {
        return this.routeLambda.operation(request, response);
    }
}
