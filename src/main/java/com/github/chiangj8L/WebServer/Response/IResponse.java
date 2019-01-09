package com.github.chiangj8L.WebServer.Response;

import java.util.HashMap;

public interface IResponse {
    String getStatus();
    void setStatus(String status);
    void addHeader(String key, String value);
    HashMap<String, String> getHeaders();
    void setBody(String text);
    String getProtocol();
    void setProtocol(String protocol);
    String getBody();
}
