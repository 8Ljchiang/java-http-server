package DataParser;

import Exception.ProtocolNotSupportedException;
import Request.IRequest;
import SocketConnection.ISocketConnection;

import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DataParser {

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

    public class MockSocketConnection implements ISocketConnection {

        public String outputData = null;
        public boolean isClosed = false;

        @Override
        public String readInputData() throws IOException {
            return mockDataString;
        }

        @Override
        public void sendOutputData(String text) throws IOException {
            outputData = text;
        }

        @Override
        public void closeSocketConnection() throws IOException {
            isClosed = true;
        }
    }

    @Test
    public void testGetProtocolMethod () {
        String expectedProtocol = "HTTP/1.1";

        String actualProtocol = DataParser.getProtocol(mockDataString);

        assertEquals(expectedProtocol, actualProtocol);
    }

    @Test
    public void testParseDataToRequest() {
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
        assertEquals("*/*", clientRequest.getHeader("Access"));
    }

    @Test (expected = ProtocolNotSupportedException.class)
    public void testParseDataToRequestWithNonHTTPRequest() throws ProtocolNotSupportedException {
        String invalidProtocol = "INVALID/1.1";
        DataParser.parseDataRequest(mockDataString, invalidProtocol);

        // Only for junit 5.
        //assertThrows(ProtocolNotSupportedException, () -> DataParser.parseDataRequest(mockDataString, protocol));
    }
}
