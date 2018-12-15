package Response;

import SocketConnection.ISocketConnection;

import java.util.HashMap;

public class Response implements IResponse{
    private String status = "200 OK";
    private String protocol = "HTTP/1.1";
    private String type = "text/html";
    private String body = "";
    private HashMap<String, String> headers = new HashMap<>();

    public Response() {
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

    public void setBody(String text) { this.body = text; }

    public String getBody() { return body; }

    public String getProtocol() { return protocol; }

    public void setProtocol(String protocol) { this.protocol = protocol; }
}
