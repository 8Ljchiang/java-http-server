package com.github.chiangj8L.WebServer.Router;

import com.github.chiangj8L.WebServer.HttpStatus.HttpStatus;
import com.github.chiangj8L.WebServer.Request.IRequest;
import com.github.chiangj8L.WebServer.Response.IResponse;
import com.github.chiangj8L.WebServer.Response.Response;
import com.github.chiangj8L.WebServer.Route.Route;
import com.github.chiangj8L.WebServer.RouteController.IRouteController;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

import static com.github.chiangj8L.WebServer.Methods.Methods.*;

public class Router {

    private HashMap<String, ArrayList<Route>> routes = new HashMap<>();

    public Router() {}

    public void use(String method, String path, IRouteController controller) {
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

    public void get(String path, IRouteController controller) {
        String method = GET.toString();
        use(method, path, controller);
    }

    public void put(String path, IRouteController controller) {
        String method = PUT.toString();
        use(method, path, controller);
    }

    public void post(String path, IRouteController controller) {
        String method = POST.toString();
        use(method, path, controller);
    }

    public void delete(String path, IRouteController controller) {
        String method = DELETE.toString();
        use(method, path, controller);
    }

    public IResponse handleRequest(IRequest clientRequest) {
        Response defaultResponse = new Response();
        String requestType = clientRequest.getType().split("/")[0];
        String requestMethod = clientRequest.getMethod().trim().toUpperCase();
        if (isValidRequest(clientRequest) && requestType.equals("HTTP")) {
            if (containsPath(clientRequest)) {
                if (containsRoute(clientRequest) || requestMethod.equals(HEAD.toString()) || requestMethod.equals(OPTIONS.toString())) {
                    if (clientRequest.getMethod().equals(HEAD.toString())) {
                        return handleHeadRequest(clientRequest, defaultResponse);
                    } else if (clientRequest.getMethod().equals(OPTIONS.toString())) {
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
        Route route = getMatchingRoute(GET.toString(), clientRequest.getPath());
        response.setBody("");
        if (route != null) {
            return route.process(clientRequest, response);
        }
        return handleMissingRoute(clientRequest, response);
    }

    private IResponse handleOptionRequest(IRequest clientRequest, Response response) {
        ArrayList<Route> routes = getRoutes(clientRequest.getPath());
        ArrayList<String> methods = new ArrayList<>();

        methods.add(OPTIONS.toString());
        methods.add(HEAD.toString());

        for (Route route:routes) {
            methods.add(route.getMethod());
        }

        String methodsString = String.join(",", methods);
        response.addHeader("Allow", methodsString);
        return response;
    }

    private IResponse handleMissingRoute(IRequest clientRequest, Response response) {
        response.setStatus(HttpStatus.METHOD_NOT_ALLOWED.getStatusCode() + " " + HttpStatus.METHOD_NOT_ALLOWED.getStatusDescription());
        return response;
    }

    private IResponse handleInvalidRequest(IRequest clientRequest, Response response) {
        response.setStatus(HttpStatus.BAD_REQUEST.getStatusCode() + " " + HttpStatus.BAD_REQUEST.getStatusDescription());
        return response;
    }

    private IResponse handleMissingPath(IRequest clientRequest, Response response) {
        response.setStatus(HttpStatus.NOT_FOUND.getStatusCode() + " " + HttpStatus.NOT_FOUND.getStatusDescription());
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
        return result != null;
    }

    private boolean isValidRequest(IRequest req) {
        String method = req.getMethod();
        String path = req.getPath();
        return (method.equals(GET.toString())
                || method.equals(PUT.toString())
                || method.equals(POST.toString())
                || method.equals(DELETE.toString())
                || method.equals(OPTIONS.toString())
                || method.equals(HEAD.toString()))
                && (path != null && path.length() > 0);
    }
}

