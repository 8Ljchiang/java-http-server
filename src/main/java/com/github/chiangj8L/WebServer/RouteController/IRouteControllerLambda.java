package com.github.chiangj8L.WebServer.RouteController;

import com.github.chiangj8L.WebServer.Request.IRequest;
import com.github.chiangj8L.WebServer.Response.IResponse;

public interface IRouteControllerLambda {
    IResponse operation(IRequest req, IResponse res);
}
