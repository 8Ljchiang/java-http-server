package com.github.chiangj8L.WebServer.Response;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ResponseTest {

    private Response response;

    @Before
    public final void setup() {
        response = new Response();
    }

    @Test
    public final void testSetStatus() throws NoSuchFieldException, IllegalAccessException {
        String notFoundStatus = "404 Not Found";
        response.setStatus(notFoundStatus);

        //then
        final Field field = response.getClass().getDeclaredField("status");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(response), notFoundStatus);
    }

    @Test
    public final void tesGetStatus() throws NoSuchFieldException, IllegalAccessException {
        //given
        String notFoundStatus = "404 Not Found";
        final Response finalizedRequest = response;
        final Field field = finalizedRequest.getClass().getDeclaredField("status");
        field.setAccessible(true);
        field.set(finalizedRequest, notFoundStatus);

        //when
        final String result = finalizedRequest.getStatus();

        //then
        assertEquals("field wasn't retrieved properly", result, notFoundStatus);
    }

    @Test
    public final void testAddHeader() throws NoSuchFieldException, IllegalAccessException {
        HashMap <String, String> expectedHeaders = new HashMap<>();
        expectedHeaders.put("Content-Type", "text/html");
        expectedHeaders.put("host", "localhost:9000");
        response.addHeader("host", "localhost:9000");

        //then
        final Field field = response.getClass().getDeclaredField("headers");
        field.setAccessible(true);

        HashMap<String, String> responseHeaders = (HashMap<String, String>)field.get(response);

        int expectedHeadersSize = expectedHeaders.size();
        int responseHeadersSize = responseHeaders.size();

        assertEquals(expectedHeadersSize, responseHeadersSize);

        for (HashMap.Entry<String, String> entry : expectedHeaders.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            assertTrue(responseHeaders.containsKey(key));
            assertEquals(value, responseHeaders.get(key));
        }
    }

    @Test
    public final void testGetHeaders() throws NoSuchFieldException, IllegalAccessException {
        //given
        HashMap <String, String> expectedHeaders = new HashMap<>();
        expectedHeaders.put("Content-Type", "text/html");
        expectedHeaders.put("host", "localhost:9000");

        final Response finalizedResponse = response;
        final Field field = finalizedResponse.getClass().getDeclaredField("headers");
        field.setAccessible(true);
        field.set(finalizedResponse, expectedHeaders);

        //when
        HashMap<String, String> responseHeaders = response.getHeaders();

        //then
        int expectedHeadersSize = expectedHeaders.size();
        int responseHeadersSize = responseHeaders.size();

        assertEquals(expectedHeadersSize, responseHeadersSize);

        for (HashMap.Entry<String, String> entry : expectedHeaders.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            assertTrue(responseHeaders.containsKey(key));
            assertEquals(value, responseHeaders.get(key));
        }
    }

    @Test
    public final void testSetBody() throws NoSuchFieldException, IllegalAccessException {
        String body = "{ \"response\" : \"Hello world\" }";
        response.setBody(body);

        //then
        final Field field = response.getClass().getDeclaredField("body");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(response), body);
    }

    @Test
    public final void testGetBody() throws NoSuchFieldException, IllegalAccessException {
        //given
        String body = "{ \"response\" : \"Hello world\" }";

        final Response finalizedResponse = response;
        final Field field = finalizedResponse.getClass().getDeclaredField("body");
        field.setAccessible(true);
        field.set(finalizedResponse, body);

        //when
        final String result = finalizedResponse.getBody();

        //then
        assertEquals("field wasn't retrieved properly", result, body);
    }

    @Test
    public final void testGetProtocol() throws NoSuchFieldException, IllegalAccessException {
        //given
        String expectedProtocol = "HTTP/1.1";

        final Response finalizedResponse = response;
        final Field field = finalizedResponse.getClass().getDeclaredField("protocol");
        field.setAccessible(true);
        field.set(finalizedResponse, expectedProtocol);

        //when
        final String result = finalizedResponse.getProtocol();

        //then
        assertEquals("field wasn't retrieved properly", result, expectedProtocol);
    }

    @Test
    public final void testSetProtocol() throws NoSuchFieldException, IllegalAccessException {
        String expectedProtocol = "HTTP/1.1";
        response.setProtocol(expectedProtocol);

        //then
        final Field field = response.getClass().getDeclaredField("protocol");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(response), expectedProtocol);
    }
}
