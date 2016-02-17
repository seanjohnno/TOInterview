package timeout.slang.com.timeout.scraper;


import org.junit.Assert;
import org.junit.Test;

import timeout.slang.com.model.scraper.common.CharStream;

public class TestCharStream implements CharStream.ICharHandler {

    @Test
    public void testNullObserver() {
        CharStream cs = new CharStream("A test string", null);
        cs.stream();
    }

    @Test
    public void testNullString() {
        CharStream cs = new CharStream(null, this);
        cs.stream();
    }

    @Test
    public void testEmptyString() {
        CharStream cs = new CharStream("", this);
        cs.stream();
    }

    @Test
    public void testStream() {
        String hello = "hello";
        final StringBuilder sb = new StringBuilder();

        CharStream cs = new CharStream(hello, new CharStream.ICharHandler() {
            @Override
            public CharStream.ICharHandler handleChar(char c, CharStream cpp) {
                sb.append(c);
                return this;
            }

            @Override
            public void setActiveHandler() {

            }
        });
        cs.stream();
        Assert.assertEquals(hello, sb.toString());
    }

    @Test
    public void testActiveHandlerSetOnFirst() {
        String hello = "hello";
        final SettableBoolean sb = new SettableBoolean();

        CharStream cs = new CharStream(hello, new CharStream.ICharHandler() {
            @Override
            public CharStream.ICharHandler handleChar(char c, CharStream cpp) {
                return this;
            }

            @Override
            public void setActiveHandler() {
                sb.Val = true;
            }
        });
        cs.stream();
        Assert.assertTrue(sb.Val);
    }

    @Test
    public void testActiveHandlerSetOnSubsequent() {
        String hello = "hello";
        final SettableBoolean sb = new SettableBoolean();

        final CharStream.ICharHandler second = new CharStream.ICharHandler() {
            @Override
            public CharStream.ICharHandler handleChar(char c, CharStream cpp) {
                return this;
            }

            @Override
            public void setActiveHandler() {
                sb.Val = true;
            }
        };

        CharStream cs = new CharStream(hello, new CharStream.ICharHandler() {
            int charWait = 3;
            int count = 0;
            @Override
            public CharStream.ICharHandler handleChar(char c, CharStream cpp) {
                if(count++ >= charWait) {
                    return second;
                }
                return this;
            }

            @Override
            public void setActiveHandler() {

            }
        });
        cs.stream();
        Assert.assertTrue(sb.Val);
    }




    @Override
    public CharStream.ICharHandler handleChar(char c, CharStream cpp) {
        return this;
    }

    @Override
    public void setActiveHandler() {

    }

    private class SettableBoolean {
        public boolean Val;
    }
}
