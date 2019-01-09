package com.github.chiangj8L.WebServer.RequestBuilder;

import com.github.chiangj8L.WebServer.Exception.InvalidRequestStringException;
import com.github.chiangj8L.WebServer.Request.Request;

import java.util.Arrays;
import java.util.HashMap;

public class RequestBuilder {
    public static Request createRequest(String requestString) throws InvalidRequestStringException {
        if (isValidRequestString(requestString)) {
            String method = "";
            String path = "";
            String type = "";
            String body = "";
            HashMap<String, String> headers;

            String[] requestStringArray = requestStringToArray(requestString);
            String firstLine = getFirstLine(requestStringArray);

            if (firstLine != null && firstLine.length() > 0) {
                String[] firstLineParts = firstLine.split(" ");
                method = firstLineParts[0];
                path = firstLineParts[1];
                type = firstLineParts[2];
            }

            headers = getHeaders(requestStringArray);
            body += getBody(requestStringArray);

            if (headers != null && method.length() > 0 && path.length() > 0 && type.length() > 0) {
                Request clientRequest = new Request(requestString, method, path, type, headers, body);
                return clientRequest;
            }
        }
        throw new InvalidRequestStringException("Malformed com.github.chiangj8L.WebServer.Request String");
    }

    public static String getFirstLine(String[] requestStringArray) {
        return requestStringArray[0];
    }

    public static HashMap<String, String> getHeaders(String[] requestStringArray) {
        HashMap<String, String> headers = new HashMap<>();
        if (requestStringArray.length > 0) {
            boolean isDone = false;
            int lineIndex = 1;

            while (!isDone && lineIndex < requestStringArray.length) {
                String line = requestStringArray[lineIndex];

                if (line != null && line.length() > 3 && !isDividerLine(line)) {

                    String[] entry = line.split(":");
                    String key = entry[0].trim();


                    if (key.toLowerCase().equals("host")) {
                        String value = (entry[1] + ":" + entry[2]);
                        headers.put(key.trim(), value.trim());
                    } else {
                        String value = entry[1];
                        headers.put(key.trim(), value.trim());
                    }
                }

                if (isDividerLine(line)) {
                    isDone = true;
                }
                lineIndex++;
            }
        }
        return headers;
    }

    public static String getBody(String[] requestStringArray) {
        StringBuilder sb = new StringBuilder();
        if (requestStringArray.length > 0) {
            boolean isBodyStarted = false;

            for(int i = 1; i < requestStringArray.length; i++) {
                String line = requestStringArray[i];

                if (isBodyStarted) {
                    sb.append(requestStringArray[i] + "\n");
                }

                if (isDividerLine(line)) {
                    isBodyStarted = true;
                }
            }
        }
        return sb.toString();
    }


    public static Boolean isValidRequestString(String requestString) {
        String[] requestLines = requestStringToArray(requestString.trim());
        if (requestLines != null && requestLines.length > 1) {
            String[] firstLineParts = requestLines[0].split(" ");

            if (firstLineParts.length == 3) {
                String method = firstLineParts[0];
                String path = firstLineParts[1];
                String type = firstLineParts[2].split("/")[0];

                String[] validMethods = new String[] {"GET", "HEAD", "PUT", "POST", "OPTIONS", "DELETE"};

                return Arrays.asList(validMethods).contains(method) && path.length() > 0 && type.equals("HTTP");
            }
        }
        return false;
    }

    private static String[] requestStringToArray(String requestString) {
        if (requestString != null && requestString.length() > 1) {
            return requestString.split("\n");
        }
        return new String[0];
    }

    private static Boolean isDividerLine(String line) {
        return line.equals("\n") || line.length() < 2 || line.equals(" \n");
    }
}
