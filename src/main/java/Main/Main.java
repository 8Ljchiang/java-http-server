package Main;

import Request.IRequest;
import Response.IResponse;
import RouteController.IRouteControllerLambda;
import RouteController.RouteController;
import Router.Router;
import Server.Server;

import java.io.IOException;

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

        IRouteControllerLambda postEchoHandler = (IRequest req, IResponse res) -> {
            res.addHeader("Content-Type", "application/json");
            res.setBody("{ \"response\" : \"" + req.getRequestString() + "\" }");
            return res;
        };

        RouteController getDefaultRouteController = new RouteController(getDefaultHandler);
        RouteController postDefaultRouteController = new RouteController(postDefaultHandler);
        RouteController postEchoRouteController = new RouteController(postEchoHandler);
        RouteController getIndexRouteController = new RouteController(getIndexHandler);

        Router router = new Router();
        router.get("/", getDefaultRouteController);
        router.post("/", postDefaultRouteController);
        router.get("/index.html", getIndexRouteController);
        router.post("/echo", postEchoRouteController);
        router.get("/echo", postEchoRouteController);

        Server server = new Server(router);
        server.listen(9000);


//        catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
