package Response;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;

public class ResponseTest {

    public class SocketConnectionMock implements ISocketConnection {

    }

    private SocketConnectionMock socketConnection = new SocketConnectionMock();
    private Response response;

    @Before
    public final void setup() {
        response = new Response(socketConnection);
    }

    @Test
    public final void testSetStatus() {
        String notFoundStatus = "404 Not Found";
        response.setStatus(notFoundStatus);

        //then
        final Field field = response.getClass().getDeclaredField("status");
        field.setAccessible(true);
        assertEquals("Fields didn't match", field.get(response), notFoundStatus);
    }

    @Test
    public final void tesGetStatus() {
        //given
        String notFoundStatus = "404 Not Found";
        final Request finalizedRequest = request;
        final Field field = finalizedRequest.getClass().getDeclaredField("status");
        field.setAccessible(true);
        field.set(finalizedRequest, notFoundStatus);

        //when
        final String result = finalizedRequest.getStatus();

        //then
        assertEquals("field wasn't retrieved properly", result, notFoundStatus);
    }

    @Test
    public final void testSetContentType() {

    }

    @Test
    public final void testGetContentType() {

    }

    @Test
    public final void testAddHeader() {

    }

    @Test
    public final void testSetBody() {

    }

    @Test
    public final void testSend() {

    }

}
