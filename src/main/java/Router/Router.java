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

    public IResponse handleRequest(IRequest clientRequest) {
        Response defaultResponse = new Response();
        String requestType = clientRequest.getType().split("/")[0];
        String requestMethod = clientRequest.getMethod().trim().toUpperCase();
        if (isValidRequest(clientRequest) && requestType.equals("HTTP")) {
            if (containsPath(clientRequest)) {
                if (containsRoute(clientRequest) || requestMethod.equals(Methods.HEAD.toString()) || requestMethod.equals(Methods.OPTIONS.toString())) {
                    if (clientRequest.getMethod().equals(Methods.HEAD.toString())) {
                        return handleHeadRequest(clientRequest, defaultResponse);
                    } else if (clientRequest.getMethod().equals(Methods.OPTIONS.toString())) {
                        return handleOptionRequest(clientRequest, defaultResponse);
                    } else {
                        Route rt = getMatchingRoute(clientRequest);
                        if (rt != null) {
                            return rt.process(clientRequest, defaultResponse);
                        } else {
                            return handleMissingRoute(clientRequest, defaultResponse);
                        }
                    }
                } else {
                    return handleMissingRoute(clientRequest, defaultResponse);
                }
            } else {
                return handleMissingPath(clientRequest, defaultResponse);
            }
        } else {
            return handleInvalidRequest(clientRequest, defaultResponse);
        }
    }

    private IResponse handleHeadRequest(IRequest clientRequest, Response response) {
        Route route = getMatchingRoute(Methods.GET.toString(), clientRequest.getPath());
        response.setBody("");
        if (route != null) {
            return route.process(clientRequest, response);
        }
        return handleMissingRoute(clientRequest, response);
    }

    private IResponse handleOptionRequest(IRequest clientRequest, Response response) {
        ArrayList<Route> routes = getRoutes(clientRequest.getPath());
        ArrayList<String> methods = new ArrayList<>();

        for (Route route:routes) {
            methods.add(route.getMethod());
        }

        String methodsString = String.join(", ", methods);
        response.addHeader("Allow", methodsString);
        return response;
    }

    private IResponse handleMissingRoute(IRequest clientRequest, Response response) {
        response.setStatus("405 Method Not Allowed");
        return response;
    }

    private IResponse handleInvalidRequest(IRequest clientRequest, Response response) {
        response.setStatus("400 Bad Request");
        return response;
    }

    private IResponse handleMissingPath(IRequest clientRequest, Response response) {
        response.setStatus("404 Not Found");
        return response;
    }

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

    @Nullable
    private Route getMatchingRoute(String method, String path) {
        ArrayList<Route> routes = getRoutes(path);
        for (Route route: routes) {
            if (route.getMethod().toUpperCase().equals(method)) {
                return route;
            }
        }
        return null;
    }

    private boolean containsPath(IRequest req) {
        return routes.containsKey(req.getPath());
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
