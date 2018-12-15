package ResponseBuilder;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import Response.IResponse;
import Response.Response;

public class ResponseBuilderTest {



    @Test
    public final void testCreateResponseStringFromDefaultResponse() {
        String expectResponseString = "";

        IResponse defaultResponse = new Response();
        String resultString = ResponseBuilder.createResponseString(defaultResponse);

        assertEquals(resultString, expectResponseString);
    }

    @Test
    public final void testCreateResponseStringFromResponseWithBody() {

    }

    @Test
    public final void testCreateResponseStringFromResponseWithMultiHeaderAndBody() {

    }
}
