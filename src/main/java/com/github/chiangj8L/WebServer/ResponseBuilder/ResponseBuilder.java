package com.github.chiangj8L.WebServer.ResponseBuilder;

import com.github.chiangj8L.WebServer.Response.IResponse;

import java.util.Arrays;
import java.util.HashMap;

public class ResponseBuilder {
    public static String createResponseString(IResponse response) {
        StringBuilder sb = new StringBuilder();

        String protocol = "HTTP/1.1";
        String status = response.getStatus();
        HashMap<String, String> headers = response.getHeaders();
        String body = response.getBody();

        sb.append(protocol + " " + status + "\n");

        Object[] keys = headers.keySet().toArray();
        Arrays.sort(keys);

        for (Object key : keys) {
            String headerField = (String)key;
            String headerFieldValue = headers.get(headerField);
            sb.append(headerField + ": " + headerFieldValue + "\n");
        }

        if (body != null && body.length() > 0 && !body.equals("")) {
            sb.append("\n");
            sb.append(body);
        }

        String result = sb.toString();
        return result;
    }
}
