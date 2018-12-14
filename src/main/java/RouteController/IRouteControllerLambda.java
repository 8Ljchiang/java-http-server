package RouteController;

import java.io.IOException;

public interface IRouteControllerLambda {
    public void operation(IRequest req, IResponse res) throws IOException;
}