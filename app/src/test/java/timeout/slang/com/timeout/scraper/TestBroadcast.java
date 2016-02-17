package timeout.slang.com.timeout.scraper;

import org.junit.Assert;
import org.junit.Test;

import timeout.slang.com.model.scraper.common.CharStream;
import timeout.slang.com.model.scraper.common.handlers.HandlerBase;
import timeout.slang.com.model.scraper.common.handlers.HandlerBroadcast;

/**
 * Created by MrLenovo on 17/02/2016.
 */
public class TestBroadcast {

    public static final String TEST = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";


    @Test
    public void testEmptyBroadcast() {
        HandlerBroadcast hb = new HandlerBroadcast();
        int ptr = 0;
        while(ptr < TEST.length()) {
            hb.handleChar(TEST.charAt(ptr++), null);
        }
    }

    @Test
    public void testNullAdds() {
        HandlerBroadcast hb = new HandlerBroadcast();
        hb.addSubHandler(null);
        int ptr = 0;
        while(ptr < TEST.length()) {
            hb.handleChar(TEST.charAt(ptr++), null);
        }
    }

    @Test
    public void testExceptionThrower() {
        String testStr = "hello";

        HandlerBroadcast hb = new HandlerBroadcast();
        hb.addSubHandler(new ExceptionThrower());
        StringBuilderImpl sbi = new StringBuilderImpl();
        hb.addSubHandler(sbi);

        int ptr = 0;
        while(ptr < testStr.length()) {
            hb.handleChar(testStr.charAt(ptr++), null);
        }

        Assert.assertEquals(testStr, sbi.toString());
    }

    @Test
    public void testNullRemoves() {
        HandlerBroadcast hb = new HandlerBroadcast();
        hb.addSubHandler(new ExceptionThrower());
        hb.addSubHandler(new ExceptionThrower());
        hb.removeSubHandler(null);
        Assert.assertEquals(hb.getSize(), 2);
    }

    @Test
    public void testRemove() {
        HandlerBroadcast hb = new HandlerBroadcast();
        ExceptionThrower et = new ExceptionThrower();
        hb.addSubHandler(et);
        hb.addSubHandler(new ExceptionThrower());
        hb.removeSubHandler(et);
        Assert.assertEquals(hb.getSize(), 1);
    }

    @Test
    public void checkActiveSetOnAll() {
        HandlerBroadcast hb = new HandlerBroadcast();
        ActiveSet[] et = new ActiveSet[]{ new StringBuilderImpl(), new ExceptionThrower(), new StringBuilderImpl() };

        for(ActiveSet as : et) {
            hb.addSubHandler(as);
        }
        hb.setActiveHandler();

        for(ActiveSet as : et) {
            if(as instanceof StringBuilderImpl) {
                Assert.assertTrue(as.Active);
            }
        }
    }


    public abstract class ActiveSet extends HandlerBase {

        public boolean Active;

        @Override
        public void setActiveHandler() {
            Active = true;
        }
    }

    public class ExceptionThrower extends ActiveSet {
        @Override
        public StringBuilderImpl handleChar(char c, CharStream cpp) {
            throw new NullPointerException("Just because");
        }

        @Override
        public void setActiveHandler() {
            throw new NullPointerException("Just because");
        }
    }

    public class StringBuilderImpl extends ActiveSet {

        private StringBuilder sb = new StringBuilder();

        @Override
        public StringBuilderImpl handleChar(char c, CharStream cpp) {
            sb.append(c);
            return this;
        }

        public String toString() {
            return sb.toString();
        }
    };
}
