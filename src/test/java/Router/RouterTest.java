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
    public final void testPut() {

    }

    @Test
    public final void testPost() {

    }

    @Test
    public final void testDelete() {

    }

    @Test
    public final void testHandleRequest() {

    }
}
