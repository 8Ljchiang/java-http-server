package hello;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HelloWorldTest {

    @Test
    public void sayHelloShouldReturnHelloWorld() {
        Greeter greeter = new Greeter();

        // assert statements
        assertEquals("testing sayHello()", greeter.sayHello(), "Hello world!");
    }
}
