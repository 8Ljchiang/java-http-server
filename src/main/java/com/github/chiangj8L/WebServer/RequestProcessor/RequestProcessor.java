package com.github.chiangj8L.WebServer.RequestProcessor;

import com.github.chiangj8L.WebServer.Request.IRequest;
import com.github.chiangj8L.WebServer.Response.IResponse;
import com.github.chiangj8L.WebServer.Router.Router;

public class RequestProcessor {

    private Router router;

    public RequestProcessor(Router router) {
        this.router = router;
    }

    public IResponse processRequest(IRequest clientRequest) {
        // TODO: Pre routing.
        return router.handleRequest(clientRequest);
        // TODO: Post routing.
    }
}
