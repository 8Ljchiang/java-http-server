package Response;

import SocketConnection.ISocketConnection;

import java.util.HashMap;

public class Response {
    private ISocketConnection sc;
    private String status = "200 OK";
    private String http = "HTTP/1.1";
    private String type = "text/html";
    private HashMap<String, String> headers = new HashMap<>();

    public Response(ISocketConnection sc) {
        this.sc = sc;
        this.headers.put("Content-Type", type);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() { return this.status; }

    public void addHeader(String key, String value) {
        this.headers.put(key, value);
    }

    public HashMap<String, String> getHeaders() { return this.headers; }

    public void setContentType(String type) {
        this.type = type;
        this.headers.put("Content-Type", type);
    }

    public String getContentType() {
        return this.headers.get("Content-Type");
    }
}
