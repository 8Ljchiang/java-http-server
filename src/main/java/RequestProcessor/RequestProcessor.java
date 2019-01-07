package RequestProcessor;

import Request.IRequest;
import Response.IResponse;
import Router.Router;

public class RequestProcessor {

    private Router router;

    public RequestProcessor(Router router) {
        this.router = router;
    }

    public IResponse processRequest(IRequest clientRequest) {
        // Pre routing.
        return router.handleRequest(clientRequest);
        // Post routing.
    }
}
