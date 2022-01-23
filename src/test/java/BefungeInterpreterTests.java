import static org.junit.Assert.*;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import org.junit.Test;

public class BefungeInterpreterTests {
    @Test
    public void quine() {
        String code = "01->1# +# :# 0# g# ,# :# 5# 8# *# 4# +# -# _@";
        assertEquals(
                String.format("Code: <pre>%s</pre>", code),
                code,
                new BefungeInterpreter().interpret(code));
    }

    @Test
    public void sieve() {
        String code = """
                2>:3g" "-!v\\  g30          <
                 |!`"&":+1_:.:03p>03g+:"&"`|
                 @               ^  p3\\" ":<
                2 2345678901234567890123456789012345678""";
        assertEquals(
                String.format("Code: <pre>%s</pre>", code),
                "23571113171923293137",
                new BefungeInterpreter().interpret(code));
    }

    @Test
    public void factorial() {
        String code = """
                08>:1-:v v *_$.@
                  ^    _$>\\:^" +
                  ^    _$>\\:^""";
        assertEquals(
                String.format("Code: <pre>%s</pre>", code),
                "40320",
                new BefungeInterpreter().interpret(code));
    }

    @Test
    public void helloWorld() {
        String code = """
                >25*"!dlroW olleH":v
                                v:,_@
                                >  ^""";
        assertEquals(
                String.format("Code: <pre>%s</pre>", code),
                "Hello World!\n",
                new BefungeInterpreter().interpret(code));
    }

    @Test
    public void sampleFromDescription() {
        String code = """
                >987v>.v
                v456<  :
                >321 ^ _@""";
        assertEquals(
                String.format("Code: <pre>%s</pre>", code),
                "123456789",
                new BefungeInterpreter().interpret(code));
    }

    @Test
    public void testRandomDirection() {
        AtomicInteger ones = new AtomicInteger(0);
        AtomicInteger twos = new AtomicInteger(0);
        String testCode = """
                v@.<
                 >1^
                >?<^
                 >2^""";
        int total = 2000;
        IntStream.range(0, total)
                .forEach(time -> {
                    String result = new BefungeInterpreter().interpret(testCode);
                    assertEquals("result failed for random test", 1, result.length());
                    switch (result.charAt(0)) {
                        case '1':
                            ones.incrementAndGet();
                            break;
                        case '2':
                            twos.incrementAndGet();
                            break;
                        default:
                            fail("Code: " + testCode);
                    }
                });
        double result = Math.abs(ones.get() / (double)total - 0.5);
        assertTrue(
                String.format("Code: <pre>%s</pre> Did not come up with 1s between 45%% and 55%% of the time", testCode),
                result < 0.05
        );
    }

}