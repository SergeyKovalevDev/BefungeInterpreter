import org.junit.Test;

import static org.junit.Assert.*;

public class BefungeInterpreterTests {

    @Test
    public void helloWorld() {
        assertEquals(
                "Hello World!\n",
                new BefungeInterpreter().interpret("""
                        >25*"!dlroW olleH":v
                                        v:,_@
                                        >  ^
                                        """));
    }

    @Test
    public void factorial() {
        assertEquals(
                "40320",
                new BefungeInterpreter().interpret("""
                        08>:1-:v v *_$.@
                          ^    _$>\\:^  ^    _$>\\:^
                          """));
    }

    @Test
    public void sampleFromDescription() {
        assertEquals(
                "123456789",
                new BefungeInterpreter().interpret("""
                        >987v>.v
                        v456<  :
                        >321 ^ _@
                        """));
    }

    @Test
    public void testRandomDirection() {
        String a = new BefungeInterpreter().interpret("""
                        v@.<
                         >1^
                        >?<^
                         >2^
                        """);
        assertTrue(a.equals("1") || a.equals("2"));
    }

    @Test
    public void quine() {
        assertEquals(
                "01->1# +# :# 0# g# ,# :# 5# 8# *# 4# +# -# _@",
                new BefungeInterpreter().interpret("""
                        01->1# +# :# 0# g# ,# :# 5# 8# *# 4# +# -# _@
                        """));
    }

    @Test
    public void sieve() {
        assertEquals(
                "23571113171923293137",
                new BefungeInterpreter().interpret("""
                        2>:3g" "-!v\\  g30          <
                         |!`"&":+1_:.:03p>03g+:"&"`|
                         @               ^  p3\\" ":<
                        2 2345678901234567890123456789012345678
                        """));
    }
}