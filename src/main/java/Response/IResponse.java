package Response;

import java.util.HashMap;

public interface IResponse {
    public String getStatus();
    public void setStatus(String status);
    public void addHeader(String key, String value);
    public HashMap<String, String> getHeaders();
    public void setBody(String text);
    public String getProtocol();
    public void setProtocol(String protocol);
    public String getBody();
}
