package Request;

import java.util.HashMap;

public interface IRequest {
    String getType();
    String getPath();
    String getMethod();
    String getBody();
    HashMap<String, String> getHeaders();
    String getHeader(String headerName);
    String getRequestString();
}
