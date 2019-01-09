package DataParser;

import com.github.chiangj8L.WebServer.Exception.InvalidRequestStringException;
import com.github.chiangj8L.WebServer.Exception.ProtocolNotSupportedException;
import com.github.chiangj8L.WebServer.Request.IRequest;
import com.github.chiangj8L.WebServer.RequestBuilder.RequestBuilder;

public class DataParser {

    public static String getProtocol(String dataString) {
        String[] requestAsArray = convertRequestStringToArray(dataString);
        String[] firstLineParts = requestAsArray[0].split(" ");

        if (firstLineParts.length == 3) {
            return firstLineParts[2];
        }

        return "";
    }

    public static IRequest parseDataToRequest(String dataString, String protocol) throws InvalidRequestStringException, ProtocolNotSupportedException {
        switch(protocol) {
            case "HTTP/1.1":
                return RequestBuilder.createRequest(dataString);
            default:
                throw new ProtocolNotSupportedException("com.github.chiangj8L.WebServer.Request protocol not supported");
        }
    }

    private static String[] convertRequestStringToArray(String dataString) {
        String[] result = dataString.split("\n");
        return result;
    }
}
