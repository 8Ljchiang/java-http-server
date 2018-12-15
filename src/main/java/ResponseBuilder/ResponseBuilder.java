package ResponseBuilder;

import Response.IResponse;

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

//        for (HashMap.Entry<String, String> entry : headers.entrySet()) {
//            String key = entry.getKey();
//            String value = entry.getValue();
//            sb.append(key + ": " + value + "\n");
//        }

        sb.append("\n" + body + "\n");

        String result = sb.toString();
        return result;
    }
}
