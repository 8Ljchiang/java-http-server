package com.github.chiangj8L.WebServer.ResponseBuilder;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import com.github.chiangj8L.WebServer.Response.IResponse;
import com.github.chiangj8L.WebServer.Response.Response;

public class ResponseBuilderTest {

    @Test
    public final void testCreateResponseStringFromDefaultResponse() {
        String protocol = "HTTP/1.1";
        String status = "200 OK";
        String body = "";
        String expectResponseString = protocol + " " + status + "\n"
                + "Content-Type: text/html\n";

        IResponse defaultResponse = new Response();
        String resultString = ResponseBuilder.createResponseString(defaultResponse);

        assertEquals(resultString, expectResponseString);
    }

    @Test
    public final void testCreateResponseStringFromResponseWithMultiHeaderAndBody() {
        String protocol = "HTTP/1.1";
        String status = "200 OK";
        String body = "{ \"response\" : \"hello world\" }";
        String expectResponseString = protocol + " " + status + "\n"
                + "Content-Type: application/json\n"
                + "host: localhost:9000\n"
                + "\n" + body;

        IResponse defaultResponse = new Response();
        defaultResponse.addHeader("host", "localhost:9000");
        defaultResponse.addHeader("Content-Type", "application/json");
        defaultResponse.setBody(body);
        String resultString = ResponseBuilder.createResponseString(defaultResponse);

        assertEquals(expectResponseString, resultString);
    }
}
