package RequestBuilder;

import Request.Request;
import org.junit.Test;
import org.junit.Before;

import java.util.HashMap;

import static org.junit.Assert.*;

public class RequestBuilderTest {

    String method = "GET";
    String path = "/home";
    String type = "HTTP/1.1";
    String headerString = "";
    String body = "";
    String requestString = "";
    HashMap<String, String> headers = new HashMap<>();

    @Test
    public final void testCreateRequest() {
        Request request = RequestBuilder.createRequest(requestString);

        assertNotNull(request);
        assertEquals(method, request.getMethod());
        assertEquals(path, request.getPath());
        assertEquals(type, request.getType());
        assertEquals(body, request.getBody());

        HashMap<String, String> requestHeaders = request.getHeaders();

        for (HashMap.Entry<String, String> entry : headers.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            assertTrue(requestHeaders.containsKey(key));
            assertEquals(value, requestHeaders.get(key));
        }
    }

    @Test
    public final void testGetFirstLine() {
        assertEquals(method + " " + path + " " + type + "\n", request.getFirstLine());
    }

    @Test
    public final void testGetHeaders() {
        HashMap<String, String> requestHeaders = request.getHeaders();

        for (HashMap.Entry<String, String> entry : headers.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            assertTrue(requestHeaders.containsKey(key));
            assertEquals(value, requestHeaders.get(key));
        }
    }

    @Test
    public final void testGetBody() {
        assertEquals(body, request.getBody());
    }
}
