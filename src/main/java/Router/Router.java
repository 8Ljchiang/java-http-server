package Router;

import Methods.*;
import Request.IRequest;
import Response.Response;
import Response.IResponse;
import Route.Route;
import RouteController.RouteController;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class Router {

    private HashMap<String, ArrayList<Route>> routes = new HashMap<>();

    public Router() {}

    public void use(String method, String path, RouteController controller) {
        Route newRoute = new Route(method, path, controller);
        if (routes.get(path) == null ) {
            ArrayList<Route> routeArray = new ArrayList<Route>();
            routeArray.add(newRoute);
            routes.put(path, routeArray);
        } else {
            ArrayList<Route> routeArray = getRoutes(path);
            routeArray.add(newRoute);
        }
    }

    public void get(String path, RouteController controller) {
        String method = Methods.GET.toString();
        use(method, path, controller);
    }

    public void put(String path, RouteController controller) {
        String method = Methods.PUT.toString();
        use(method, path, controller);
    }

    public void post(String path, RouteController controller) {
        String method = Methods.POST.toString();
        use(method, path, controller);
    }

    public void delete(String path, RouteController controller) {
        String method = Methods.DELETE.toString();
        use(method, path, controller);
    }

    @Nullable
    public IResponse handleRequest(IRequest clientRequest) {
        Response defaultResponse = new Response();
        String requestType = clientRequest.getType().split("/")[0];
        if (isValidRequest(clientRequest) && requestType.equals("HTTP") && containsRoute(clientRequest)) {
            if (clientRequest.getMethod().equals(Methods.HEAD.toString())) {
//                handleHeadRequest(clientRequest, res);
            } else if (clientRequest.getMethod().equals(Methods.OPTIONS.toString())) {
//                handleOptionRequest(clientRequest, res);
            } else {
                // Find matching route
                Route r = getMatchingRoute(clientRequest);
                // Have route handle the information and send request and response objects.
                if (r != null) {
                    return r.process(clientRequest, defaultResponse);
                } else {
                    // HandleMissingRoute;
                }
            }
        } else {
            // HandleInvalidRequest
//            System.out.println(" *** Router: ROUTE NOT FOUND *** ");
//            res.contentType("application/json");
//            res.status("404 Not Found");
//            res.send("{ \"error\" : \"unable to find resource\" }");
        }
        return null;
    }
//
//    private void handleHeadRequest(Request clientRequest, Response res) {
//
//    }
//
//    private void handleOptionRequest(Request clientRequest, Response res) {
//
//    }

    private ArrayList<Route> getRoutes(String path) {
        return this.routes.get(path);
    }

    @Nullable
    private Route getMatchingRoute(IRequest req) {
        String reqPath = req.getPath();
        String reqMethod = req.getMethod();

        ArrayList<Route> rts = routes.get(reqPath);

        if (rts != null && rts.size() > 0) {
            for (Route route:rts) {
                if (route.getMethod().equals(reqMethod) && route.getPath().equals(reqPath)) {
                    return route;
                }
            }
        }
        return null;
    }

    private boolean containsRoute(IRequest req) {
        Route result = getMatchingRoute(req);
        if (result == null) {
            return false;
        }
        return true;
    }

    private boolean isValidRequest(IRequest req) {
        String method = req.getMethod();
        String path = req.getPath();
        return (method.equals(Methods.GET.toString())
                || method.equals(Methods.PUT.toString())
                || method.equals(Methods.POST.toString())
                || method.equals(Methods.DELETE.toString())
                || method.equals(Methods.OPTIONS.toString())
                || method.equals(Methods.HEAD.toString()))
                && (path != null && path.length() > 0);
    }
}
