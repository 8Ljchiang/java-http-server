package DataParser;

import com.github.chiangj8L.WebServer.Exception.InvalidRequestStringException;
import com.github.chiangj8L.WebServer.Exception.ProtocolNotSupportedException;
import com.github.chiangj8L.WebServer.Request.IRequest;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DataParserTest {

    private String method = "GET";
    private String path = "/";
    private String userAgent = "junit/5.4";
    private String protocol = "HTTP/1.1";
    private String host = "localhost:5000";
    private String headers = "Host: " + host + "\n"
            + "User-Agent: " + userAgent + "\n"
            + "Accept: */*\n";
    private String mockDataString = method + " " + path + " " + protocol + "\n"
            + headers;

    @Test
    public void testGetProtocolMethod () {
        String expectedProtocol = "HTTP/1.1";

        String actualProtocol = DataParser.getProtocol(mockDataString);

        assertEquals(expectedProtocol, actualProtocol);
    }

    @Test
    public void testParseDataToRequest() throws InvalidRequestStringException, ProtocolNotSupportedException {
        String expectedBody = "";
        String expectedPath = path;
        String expectedMethod = method;
        String expectedProtocol = protocol;

        int expectedHeaderCount = 3;
        HashMap<String, String> expectedHeaders = new HashMap<>();
        expectedHeaders.put("Host", host);
        expectedHeaders.put("User-Agent", userAgent);
        expectedHeaders.put("Accept", "*/*");

        IRequest clientRequest = DataParser.parseDataToRequest(mockDataString, protocol);

        assertTrue(clientRequest instanceof IRequest);
        assertEquals(expectedMethod, clientRequest.getMethod());
        assertEquals(expectedPath, clientRequest.getPath());
        assertEquals(expectedProtocol, clientRequest.getType());
        assertEquals(mockDataString, clientRequest.getRequestString());
        assertEquals(expectedBody, clientRequest.getBody());

        assertEquals(expectedHeaderCount, clientRequest.getHeaders().size());
        assertTrue(clientRequest.getHeaders().containsKey("Host"));
        assertTrue(clientRequest.getHeaders().containsKey("User-Agent"));
        assertTrue(clientRequest.getHeaders().containsKey("Accept"));
        assertEquals(host, clientRequest.getHeader("Host"));
        assertEquals(userAgent, clientRequest.getHeader("User-Agent"));
        assertEquals("*/*", clientRequest.getHeader("Accept"));
    }

    @Test (expected = ProtocolNotSupportedException.class)
    public void testParseDataToRequestWithNonHTTPRequest() throws InvalidRequestStringException,ProtocolNotSupportedException {
        String invalidProtocol = "INVALID/1.1";

        IRequest request = DataParser.parseDataToRequest(mockDataString, invalidProtocol);

        // Only for junit 5.
        //assertThrows(ProtocolNotSupportedException, () -> DataParser.parseDataRequest(mockDataString, protocol));
    }

    @Test (expected = InvalidRequestStringException.class)
    public void testParseDataToRequestWithInvalidRequest() throws InvalidRequestStringException, ProtocolNotSupportedException {
        String invalidProtocol = "HTTP/1.1";
        String invalidPath = "invalid";
        String invalidMethod = "INVALID";
        String invalidDataString = invalidMethod + " " + invalidPath + " " + protocol + "\n"
                + headers;
        IRequest request = DataParser.parseDataToRequest(invalidDataString, invalidProtocol);

        // Only for junit 5.
        //assertThrows(ProtocolNotSupportedException, () -> DataParser.parseDataRequest(mockDataString, protocol));
    }
}
