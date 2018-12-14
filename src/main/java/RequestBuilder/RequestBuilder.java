package RequestBuilder;

import Request.Request;

import java.util.HashMap;

public class RequestBuilder {
    public static Request createRequest(String requestString) {
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
        return null;
    }

    public static String getFirstLine(String[] requestStringArray) {
        return requestStringArray[0];
    }

    public static HashMap<String, String> getHeaders(String[] requestStringArray) {
        HashMap<String, String> headers = new HashMap<>();
        if (requestStringArray.length > 0) {
            boolean isDone = false;
            int lineIndex = 1;

            while (!isDone) {
                String line = requestStringArray[lineIndex];

                if (line != null && line.length() > 3 && !isDividerLine(line)) {

                    String[] entry = line.split(":");
                    String key = entry[0];


                    if (key.equals("host")) {
                        String value = "";
                        for (int i = 1; i < entry.length; i++) {
                            value += entry[i];
                        }
                        headers.put(key, value);
                    } else {
                        String value = entry[1];
                        headers.put(key, value);
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
        String[] requestLines = requestStringToArray(requestString);


        if (requestLines != null && requestLines.length > 0) {
            String[] firstLineParts = requestLines[0].split(" ");
            String type = firstLineParts[2].split("/")[0];
            if (firstLineParts.length == 3 && type.equals("HTTP")) {
                return true;
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
        if (line.equals("\n") || line.length() < 2 || line.equals(" \n")) {
            return true;
        }
        return false;
    }
}
