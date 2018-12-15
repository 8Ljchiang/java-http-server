package Router;

import Methods.*;
import Request.IRequest;
import Response.IResponse;
import Route.Route;
import RouteController.IRouteController;
import RouteController.IRouteControllerLambda;
import RouteController.RouteController;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RouterTest {

    private Router router;

    @Before
    public final void setup() {
        router = new Router();
    }

    @Test
    public final void testUse() throws IllegalAccessException, NoSuchFieldException {
        String path = "/";
        String expectedMethod = Methods.GET.toString();
        int expectedNumOfPaths = 1;
        int expectedNumOfRoutes = 1;

        // Test the creation of a GET route and added to the router.
        IRouteControllerLambda defaultControllerLambda = (IRequest req, IResponse res) -> {
            return res;
        };

        RouteController defaultController = new RouteController(defaultControllerLambda);
        router.use(expectedMethod, path, defaultController);

        // Get HashMap of Routes

        final Field field = router.getClass().getDeclaredField("routes");
        field.setAccessible(true);
        HashMap<String, ArrayList<Route>> routerRoutes = (HashMap<String, ArrayList<Route>>)field.get(router);

        // Check for proper number of keys.
        int numOfPaths = routerRoutes.size();
        assertEquals(expectedNumOfPaths, numOfPaths);

        // Check that router paths all exist.
        assertTrue(routerRoutes.containsKey(path));

        // Check for proper number of routes on path (key).
        int numOfRoutes = routerRoutes.get(path).size();
        assertEquals(expectedNumOfRoutes, numOfRoutes);

        // Check that route has proper fields.
        Route resultRoute = routerRoutes.get(path).get(0);
        assertEquals(path, resultRoute.getPath());
        assertEquals(expectedMethod, resultRoute.getMethod());
    }

    @Test
    public final void testGet() throws IllegalAccessException, NoSuchFieldException {
        String path = "/";
        String expectedMethod = Methods.GET.toString();
        int expectedNumOfPaths = 1;
        int expectedNumOfRoutes = 1;

        // Test the creation of a GET route and added to the router.
        IRouteControllerLambda defaultControllerLambda = (IRequest req, IResponse res) -> {
            return res;
        };

        RouteController defaultController = new RouteController(defaultControllerLambda);
        router.get(path, defaultController);

        // Get HashMap of Routes

        final Field field = router.getClass().getDeclaredField("routes");
        field.setAccessible(true);
        HashMap<String, ArrayList<Route>> routerRoutes = (HashMap<String, ArrayList<Route>>)field.get(router);

        // Check for proper number of keys.
        int numOfPaths = routerRoutes.size();
        assertEquals(expectedNumOfPaths, numOfPaths);

        // Check that router paths all exist.
        assertTrue(routerRoutes.containsKey(path));

        // Check for proper number of routes on path (key).
        int numOfRoutes = routerRoutes.get(path).size();
        assertEquals(expectedNumOfRoutes, numOfRoutes);

        // Check that route has proper fields.
        Route resultRoute = routerRoutes.get(path).get(0);
        assertEquals(path, resultRoute.getPath());
        assertEquals(expectedMethod, resultRoute.getMethod());
    }

    @Test
    public final void testPut() throws IllegalAccessException, NoSuchFieldException {
        String path = "/";
        String expectedMethod = Methods.PUT.toString();
        int expectedNumOfPaths = 1;
        int expectedNumOfRoutes = 1;

        // Test the creation of a GET route and added to the router.
        IRouteControllerLambda defaultControllerLambda = (IRequest req, IResponse res) -> {
            return res;
        };

        RouteController defaultController = new RouteController(defaultControllerLambda);
        router.put(path, defaultController);

        // Get HashMap of Routes

        final Field field = router.getClass().getDeclaredField("routes");
        field.setAccessible(true);
        HashMap<String, ArrayList<Route>> routerRoutes = (HashMap<String, ArrayList<Route>>)field.get(router);

        // Check for proper number of keys.
        int numOfPaths = routerRoutes.size();
        assertEquals(expectedNumOfPaths, numOfPaths);

        // Check that router paths all exist.
        assertTrue(routerRoutes.containsKey(path));

        // Check for proper number of routes on path (key).
        int numOfRoutes = routerRoutes.get(path).size();
        assertEquals(expectedNumOfRoutes, numOfRoutes);

        // Check that route has proper fields.
        Route resultRoute = routerRoutes.get(path).get(0);
        assertEquals(path, resultRoute.getPath());
        assertEquals(expectedMethod, resultRoute.getMethod());
    }

    @Test
    public final void testPost() throws IllegalAccessException, NoSuchFieldException {
        String path = "/";
        String expectedMethod = Methods.POST.toString();
        int expectedNumOfPaths = 1;
        int expectedNumOfRoutes = 1;

        // Test the creation of a GET route and added to the router.
        IRouteControllerLambda defaultControllerLambda = (IRequest req, IResponse res) -> {
            return res;
        };

        RouteController defaultController = new RouteController(defaultControllerLambda);
        router.post(path, defaultController);

        // Get HashMap of Routes

        final Field field = router.getClass().getDeclaredField("routes");
        field.setAccessible(true);
        HashMap<String, ArrayList<Route>> routerRoutes = (HashMap<String, ArrayList<Route>>)field.get(router);

        // Check for proper number of keys.
        int numOfPaths = routerRoutes.size();
        assertEquals(expectedNumOfPaths, numOfPaths);

        // Check that router paths all exist.
        assertTrue(routerRoutes.containsKey(path));

        // Check for proper number of routes on path (key).
        int numOfRoutes = routerRoutes.get(path).size();
        assertEquals(expectedNumOfRoutes, numOfRoutes);

        // Check that route has proper fields.
        Route resultRoute = routerRoutes.get(path).get(0);
        assertEquals(path, resultRoute.getPath());
        assertEquals(expectedMethod, resultRoute.getMethod());
    }

    @Test
    public final void testDelete() throws IllegalAccessException, NoSuchFieldException {
        String path = "/";
        String expectedMethod = Methods.DELETE.toString();
        int expectedNumOfPaths = 1;
        int expectedNumOfRoutes = 1;

        // Test the creation of a GET route and added to the router.
        IRouteControllerLambda defaultControllerLambda = (IRequest req, IResponse res) -> {
            return res;
        };

        RouteController defaultController = new RouteController(defaultControllerLambda);
        router.delete(path, defaultController);

        // Get HashMap of Routes

        final Field field = router.getClass().getDeclaredField("routes");
        field.setAccessible(true);
        HashMap<String, ArrayList<Route>> routerRoutes = (HashMap<String, ArrayList<Route>>)field.get(router);

        // Check for proper number of keys.
        int numOfPaths = routerRoutes.size();
        assertEquals(expectedNumOfPaths, numOfPaths);

        // Check that router paths all exist.
        assertTrue(routerRoutes.containsKey(path));

        // Check for proper number of routes on path (key).
        int numOfRoutes = routerRoutes.get(path).size();
        assertEquals(expectedNumOfRoutes, numOfRoutes);

        // Check that route has proper fields.
        Route resultRoute = routerRoutes.get(path).get(0);
        assertEquals(path, resultRoute.getPath());
        assertEquals(expectedMethod, resultRoute.getMethod());
    }

    @Test
    public final void testMultiRouteCreation() throws IllegalAccessException, NoSuchFieldException {
        String path1 = "/";
        String path2 = "/other";
        String expectedMethod1 = Methods.GET.toString();
        String expectedMethod2 = Methods.PUT.toString();
        String expectedMethod3 = Methods.POST.toString();
        String expectedMethod4 = Methods.DELETE.toString();

        int expectedNumOfPaths = 2;
        int expectedNumOfRoutes = 4;

        // Test the creation of a GET route and added to the router.
        IRouteControllerLambda defaultControllerLambda = (IRequest req, IResponse res) -> {
            return res;
        };

        RouteController defaultController = new RouteController(defaultControllerLambda);
        router.get(path1, defaultController);
        router.put(path1, defaultController);
        router.post(path1, defaultController);
        router.delete(path1, defaultController);
        router.get(path2, defaultController);
        router.put(path2, defaultController);
        router.post(path2, defaultController);
        router.delete(path2, defaultController);

        // Get HashMap of Routes
        final Field field = router.getClass().getDeclaredField("routes");
        field.setAccessible(true);
        HashMap<String, ArrayList<Route>> routerRoutes = (HashMap<String, ArrayList<Route>>)field.get(router);

        // Check for proper number of keys.
        int numOfPaths = routerRoutes.size();
        assertEquals(expectedNumOfPaths, numOfPaths);

        // Check that router paths all exist.
        assertTrue(routerRoutes.containsKey(path1));
        assertTrue(routerRoutes.containsKey(path2));

        // Check for proper number of routes on path (key).
        int numOfRoutes1 = routerRoutes.get(path1).size();
        int numOfRoutes2 = routerRoutes.get(path2).size();
        assertEquals(expectedNumOfRoutes, numOfRoutes1);
        assertEquals(expectedNumOfRoutes, numOfRoutes2);

        // Check that route has proper fields.
        ArrayList<String> path1RouteMethods = new ArrayList<>();
        for (Route route: routerRoutes.get(path1)) {
            path1RouteMethods.add(route.getMethod());
        }

        ArrayList<String> path2RouteMethods = new ArrayList<>();
        for (Route route: routerRoutes.get(path2)) {
            path2RouteMethods.add(route.getMethod());
        }

        assertTrue(path1RouteMethods.indexOf(expectedMethod1) != -1);
        assertTrue(path1RouteMethods.indexOf(expectedMethod2) != -1);
        assertTrue(path1RouteMethods.indexOf(expectedMethod3) != -1);
        assertTrue(path1RouteMethods.indexOf(expectedMethod4) != -1);

        assertTrue(path2RouteMethods.indexOf(expectedMethod1) != -1);
        assertTrue(path2RouteMethods.indexOf(expectedMethod1) != -1);
        assertTrue(path2RouteMethods.indexOf(expectedMethod1) != -1);
        assertTrue(path2RouteMethods.indexOf(expectedMethod1) != -1);
    }

    @Test
    public final void testHandleRequest() {

    }
}
