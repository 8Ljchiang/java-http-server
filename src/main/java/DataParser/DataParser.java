package DataParser;

import Exception.InvalidRequestStringException;
import Exception.ProtocolNotSupportedException;
import Request.IRequest;
import RequestBuilder.RequestBuilder;

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
                throw new ProtocolNotSupportedException("Request protocol not supported");
        }
    }

    private static String[] convertRequestStringToArray(String dataString) {
        String[] result = dataString.split("\n");
        return result;
    }
}
