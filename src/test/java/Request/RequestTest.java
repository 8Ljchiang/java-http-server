package Request;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

public class RequestTest {

    String path = "/home";
    String method = "GET";
    String type = "HTTP/1.1";
    String body = "\n" + "hello world";
    String headerString = "Content-Type: text/plain\n"
            + "Host: localhost:9000\n";
    String requestString =  method + " " + path + " " + type + "\n"
            + headerString
            + body;
    HashMap<String, String> headers = new HashMap<>();

    private Request request;

    @Before
    public void setup() {
        headers.put("Content-Type", "text/plain");
        headers.put("Host", "localhost:9000");
        request = new Request(requestString, method, path, type, headers, body);
    }

    @Test
    public final void testGetType() {
        assertEquals(type, request.getType());
    }

    @Test
    public final void testGetMethod() {
        assertEquals(type, request.getMethod());
    }

    @Test
    public final void testGetPath() {
        assertEquals(path, request.getPath());
    }

    @Test
    public final void testGetHeaders() {
        HashMap<String, String> requestHeaders = request.getHeaders();

        for (HashMap.Entry<String, String> entry : headers.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            assertTrue(requestHeaders.containsKey(key);
            assertEquals(value, requestHeaders.get(key));
        }
    }

    @Test
    public final void testGetHeader() {
        for (HashMap.Entry<String, String> entry: headers.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            String fieldName = key;
            String fieldValue = request.getHeader(fieldName);
            assertEquals(value, fieldValue);
        }
    }

    @Test
    public final void testGetBody() {
        assertEquals(body, request.getBody());
    }

    @Test
    public final void testGetRequestString() {
        assertEquals(requestString, request.getRequestString());
    }

    @Test
    public final void testIsValid() {
        
    }
}
