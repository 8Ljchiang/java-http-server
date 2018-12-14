package Route;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

public class RouteTest {

    private Route route;
    private String method = "GET";
    private String path = "/home";
    private ArrayList<String> methodCalls = new ArrayList<>();

    @Before
    public void setup() {
        IRouteController routeController = new RouteControllerMock();
        route = new Route(method, path, routeController);
    }

    @Test
    public final void testGetMethod() {
        assertEquals(method, route.getMethod());
    }

    @Test
    public final void testGetPath() {
        assertEquals(path, route.getPath());
    }

    @Test
    public final void testProcess() {
        assertEquals(true, methodCalls.contains("routeController.handle()"));
    }
}
