package com.github.chiangj8L.WebServer.Request;
import java.util.HashMap;

public class Request implements IRequest {

    private String requestString;
    public String method;
    public String type;
    public String path;
    public String body;
    public HashMap<String, String> headers;

    public Request(String requestString, String method, String path, String type, HashMap<String, String> headers, String body) {
        this.requestString = requestString;
        this.method = method;
        this.type = type;
        this.path = path;
        this.body = body;
        this.headers = headers;
    }

    public String getType() {
        return type;
    }

    public String getPath() {
        return path;
    }

    public String getMethod() {
        return method;
    }

    public String getBody() {
        return body;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public String getHeader(String headerName) {
        return headers.get(headerName);
    }

    public String getRequestString() {
        return requestString;
    }
}
