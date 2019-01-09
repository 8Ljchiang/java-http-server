package com.github.chiangj8L.WebServer.Route;

import com.github.chiangj8L.WebServer.Request.IRequest;
import com.github.chiangj8L.WebServer.Response.IResponse;
import com.github.chiangj8L.WebServer.RouteController.IRouteController;

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

    public IResponse process(IRequest req, IResponse res) {
        return this.controller.handle(req, res);
    }
}
