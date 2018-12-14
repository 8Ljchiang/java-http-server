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

    Request request;

    @Before
    public final void setup() {
        request = RequestBuilder.createRequest(requestString);
    }

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
        String[] requestStringArray = requestString.split("\n");
        assertEquals(method + " " + path + " " + type + "\n", RequestBuilder.getFirstLine(requestStringArray));
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
