package Main;

import CommandHandler.ICommandHandlerLambda;
import Request.IRequest;
import Response.IResponse;
import RouteController.IRouteControllerLambda;
import RouteController.IRouteController;
import RouteController.RouteController;
import Router.Router;
import Server.Server;

import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        IRouteController postDefaultHandler = (IRequest req, IResponse res) -> {
            String result = req.getBody();
            res.addHeader("Content-Type", "application/json");
            res.setBody("{ \"response\" : \"" + result.trim() + "\" }");
            return res;
        };

        IRouteController getIndexHandler = (IRequest req, IResponse res) -> {
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

        IRouteController getDefaultHandler = (IRequest req, IResponse res) -> {
            res.addHeader("Content-Type", "application/json");
            res.setStatus("200 OK");
            res.setBody("{ \"response\" : \"hello world\" }");
            return res;
        };

        IRouteController echoRequestHandler = (IRequest req, IResponse res) -> {
            res.addHeader("Content-Type", "application/json");
            res.setBody("{ \"response\" : \"" + req.getRequestString() + "\" }");
            return res;
        };

        IRouteController postEchoHandler = (IRequest req, IResponse res) -> {
            res.addHeader("Content-Type", req.getHeader("Content-Type"));
            res.setBody(req.getBody().trim());
            return res;
        };

        IRouteController simpleGetHandler = (IRequest req, IResponse res) -> {
            return res;
        };

        IRouteController getWithBodyHandler = (IRequest req, IResponse res) -> {
            return res;
        };

        IRouteController methodOptions2Handler = (IRequest req, IResponse res) -> {
            return res;
        };

        IRouteController methodOptionsHandler = (IRequest req, IResponse res) -> {
            return res;
        };

        IRouteController redirectHandler = (IRequest req, IResponse res) -> {
            res.setStatus("301 Moved Permanently");
            res.addHeader("Location", "http://0.0.0.0:5000/simple_get");
            return res;
        };

//        RouteController getDefaultRouteController = new RouteController(getDefaultHandler);
//        RouteController postDefaultRouteController = new RouteController(postDefaultHandler);
//        RouteController echoRequestController = new RouteController(echoRequestHandler);
//        RouteController postEchoRouteController = new RouteController(postEchoHandler);
//        RouteController getIndexRouteController = new RouteController(getIndexHandler);
//        RouteController simpleGetController = new RouteController(simpleGetHandler);
//        RouteController methodOptions2Controller = new RouteController(methodOptions2Handler);
//        RouteController methodOptionsController = new RouteController(methodOptionsHandler);
//        RouteController redirectController = new RouteController(redirectHandler);
//        RouteController getWithBodyController = new RouteController(getWithBodyHandler);

        Router router = new Router();
        router.get("/", getDefaultHandler);
        router.post("/", postDefaultHandler);
        router.get("/index.html", getIndexHandler);
        router.get("/echo", echoRequestHandler);
        router.post("/echo", echoRequestHandler);

        router.post("/echo_body", postEchoHandler);
        router.get("/simple_get",simpleGetHandler);
        router.get("/get_with_body", getWithBodyHandler);
        router.get("/method_options", methodOptionsHandler);

        router.get("/method_options2", methodOptions2Handler);
        router.put("/method_options2", methodOptions2Handler);
        router.post("/method_options2", methodOptions2Handler);
        router.get("/redirect", redirectHandler);


        int port = 5000;
        Server server = new Server(router, false, 12);

        ICommandHandlerLambda showServerInfoHandler1 = (HashMap<String, Object> payload) -> {
            System.out.println("*1* JAVA-HTTP-SERVER: Listening on port " + port);
        };

        ICommandHandlerLambda showServerInfoHandler2 = (HashMap<String, Object> payload) -> {
            System.out.println("*2* JAVA-HTTP-SERVER: Completed connection processing.");
        };

        String names = "listen, close, error, connection";
        server.on("listen", showServerInfoHandler1);
        server.on("connection", showServerInfoHandler2);

        server.listen(port);
    }
}
