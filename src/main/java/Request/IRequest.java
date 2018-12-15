package Request;

import java.util.HashMap;

public interface IRequest {
    public String getType();
    public String getPath();
    public String getMethod();
    public String getBody();
    public HashMap<String, String> getHeaders();
    public String getHeader(String headerName);
    public String getRequestString();
//    public Boolean isValid();
}
