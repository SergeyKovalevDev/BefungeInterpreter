import org.junit.Test;

import static org.junit.Assert.*;

public class BefungeInterpreterTest {

    @Test
    public void tests() {
        assertEquals(
                "123456789",
                new BefungeInterpreter().interpret(">987v>.v\nv456<  :\n>321 ^ _@"));
        assertEquals(
                "FizzBuzz",
                new BefungeInterpreter().interpret("0v       v    <\n" +
                        " >91+91+*>:1-:|\n" +
                        "              $    >\"zziF\",,,,v\n" +
                        "              >:3%!|v         <\n" +
                        "                         >\"zzuB\",,,,v           @\n" +
                        "                   >>:5%!|v         <    >91+,:!|\n" +
                        "                               >$        ^      \n" +
                        "                         >>:3%!|    >$   ^ \n" +
                        "                               >:5%!|\n" +
                        "                                    >.   ^            \n" +
                        "              ^                                 <"));
    }
}