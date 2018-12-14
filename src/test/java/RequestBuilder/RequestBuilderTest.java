package RequestBuilder;

import Request.Request;
import org.junit.Test;
import org.junit.Before;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;


public class RequestBuilderTest {

    String method = "GET";
    String path = "/home";
    String type = "HTTP/1.1";
    String headerString = "Content-Type: text/plain\n"
            + "host: localhost:9000\n";
    String body = "hello world\n";
    String requestString = method + " " + path + " " + type + "\n"
            + headerString
            + "\n" + body;
    HashMap<String, String> headers;

    Request request;

    @Before
    public final void setup() {
        headers = new HashMap<>();
        headers.put("Content-Type", "text/plain");
        headers.put("host", "localhost:9000");
        request = RequestBuilder.createRequest(requestString);
    }

    @Test
    public final void testCreateRequest() {
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
        String[] requestStringArray = requestString.split("\n");
        assertEquals(method + " " + path + " " + type, RequestBuilder.getFirstLine(requestStringArray));
    }

    @Test
    public final void testGetHeaders() {
        String[] requestStringArray = requestString.split("\n");
        HashMap<String, String> requestHeaders = RequestBuilder.getHeaders(requestStringArray);

        for (HashMap.Entry<String, String> entry : headers.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            assertTrue(requestHeaders.containsKey(key));
            assertEquals(value, requestHeaders.get(key));
        }
    }

    @Test
    public final void testGetBody() {
        String[] requestStringArray = requestString.split("\n");
        assertEquals(body, RequestBuilder.getBody(requestStringArray));
    }

    @Test
    public final void testIsValidRequestString() {
        String validRequestString = requestString;
        assertTrue(RequestBuilder.isValidRequestString(validRequestString));

        String invalidRequestString1 = "";
        assertFalse(RequestBuilder.isValidRequestString(invalidRequestString1));

        String invalidRequestString2 = "GET";
        assertFalse(RequestBuilder.isValidRequestString(invalidRequestString2));

        String invalidRequestString3 = "GET /home";
        assertFalse(RequestBuilder.isValidRequestString(invalidRequestString3));

        String invalidRequestString4 = "GET /home HTTP/1.1";
        assertFalse(RequestBuilder.isValidRequestString(invalidRequestString4));

        String invalidRequestString5 = "ASDF /home HTTP/1.1";
        assertFalse(RequestBuilder.isValidRequestString(invalidRequestString5));

        String invalidRequestString6 = "GET /home ASDF/1.1";
        assertFalse(RequestBuilder.isValidRequestString(invalidRequestString6));
    }
}
