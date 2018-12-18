package Main;

import CommandHandler.ICommandHandlerLambda;
import Request.IRequest;
import Response.IResponse;
import RouteController.IRouteControllerLambda;
import RouteController.RouteController;
import Router.Router;
import Server.Server;

import java.io.IOException;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        IRouteControllerLambda postDefaultHandler = (IRequest req, IResponse res) -> {
            String result = req.getBody();
            res.addHeader("Content-Type", "application/json");
            res.setBody("{ \"response\" : \"" + result.trim() + "\" }");
            return res;
        };

        IRouteControllerLambda getIndexHandler = (IRequest req, IResponse res) -> {
            String htmlResponse = "<!DOCTYPE HTML PUBLIC>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "\n" +
                    "<title>Page Title</title>\n" +
                    "\n" +
                    "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n" +
                    "\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "\n" +
                    "<h2>Site Heading (H2) - Welcome to the My Website</h2>\n" +
                    "\n" +
                    "<p>This paragraph tests whether there can be test shown on a web page...\n" +
                    "</p>\n" +
                    "</body>\n" +
                    "</html>\n";
            res.setBody(htmlResponse);
            return res;
        };

        IRouteControllerLambda getDefaultHandler = (IRequest req, IResponse res) -> {
            res.addHeader("Content-Type", "application/json");
            res.setStatus("200 OK");
            res.setBody("{ \"response\" : \"hello world\" }");
            return res;
        };

        IRouteControllerLambda echoRequestHandler = (IRequest req, IResponse res) -> {
            res.addHeader("Content-Type", "application/json");
            res.setBody("{ \"response\" : \"" + req.getRequestString() + "\" }");
            return res;
        };

        IRouteControllerLambda postEchoHandler = (IRequest req, IResponse res) -> {
            res.addHeader("Content-Type", req.getHeader("Content-Type"));
            res.setBody(req.getBody().trim());
            return res;
        };

        IRouteControllerLambda simpleGetHandler = (IRequest req, IResponse res) -> {
            return res;
        };

        IRouteControllerLambda getWithBodyHandler = (IRequest req, IResponse res) -> {
            return res;
        };

        IRouteControllerLambda methodOptions2Handler = (IRequest req, IResponse res) -> {
            return res;
        };

        IRouteControllerLambda methodOptionsHandler = (IRequest req, IResponse res) -> {
            return res;
        };

        IRouteControllerLambda redirectHandler = (IRequest req, IResponse res) -> {
            res.setStatus("301 Moved Permanently");
            res.addHeader("Location", "http://0.0.0.0:5000/simple_get");
            return res;
        };

        RouteController getDefaultRouteController = new RouteController(getDefaultHandler);
        RouteController postDefaultRouteController = new RouteController(postDefaultHandler);
        RouteController echoRequestController = new RouteController(echoRequestHandler);
        RouteController postEchoRouteController = new RouteController(postEchoHandler);
        RouteController getIndexRouteController = new RouteController(getIndexHandler);
        RouteController simpleGetController = new RouteController(simpleGetHandler);
        RouteController methodOptions2Controller = new RouteController(methodOptions2Handler);
        RouteController methodOptionsController = new RouteController(methodOptionsHandler);
        RouteController redirectController = new RouteController(redirectHandler);
        RouteController getWithBodyController = new RouteController(getWithBodyHandler);

        Router router = new Router();
        router.get("/", getDefaultRouteController);
        router.post("/", postDefaultRouteController);
        router.get("/index.html", getIndexRouteController);
        router.get("/echo", echoRequestController);
        router.post("/echo", postEchoRouteController);

        router.post("/echo_body", postEchoRouteController);
        router.get("/simple_get",simpleGetController);
        router.get("/get_with_body", getWithBodyController);
        router.get("/method_options", methodOptionsController);

        router.get("/method_options2", methodOptions2Controller);
        router.put("/method_options2", methodOptions2Controller);
        router.post("/method_options2", methodOptions2Controller);
        router.get("/redirect", redirectController);


        int port = 5000;
        Server server = new Server(router, false, 12);

        ICommandHandlerLambda showServerInfoHandler1 = (HashMap<String, Object> payload) -> {
            System.out.println("*1* JAVA-HTTP-SERVER: Listening on port " + port);
        };

        ICommandHandlerLambda showServerInfoHandler2 = (HashMap<String, Object> payload) -> {
            System.out.println("*2* JAVA-HTTP-SERVER: Listening on port " + port);
        };

        server.on("listen", showServerInfoHandler1);
        server.on("listen", showServerInfoHandler2);

        server.listen(port);
    }
}
