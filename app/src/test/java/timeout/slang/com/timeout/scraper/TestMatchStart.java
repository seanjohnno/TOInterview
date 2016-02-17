package timeout.slang.com.timeout.scraper;

import junit.framework.Assert;

import org.hamcrest.BaseMatcher;
import org.junit.Test;

import rx.functions.Action1;
import timeout.slang.com.model.scraper.common.CharStream;
import timeout.slang.com.model.scraper.common.handlers.HandlerBase;
import timeout.slang.com.model.scraper.common.handlers.HandlerMatchStart;
import timeout.slang.com.reusable.Func;

/**
 * Created by MrLenovo on 17/02/2016.
 */
public class TestMatchStart {

    public static final String TEST = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";

    @Test
    public void testNullMatch() {
       HandlerBase start = createHandler(null);
       int ptr = 0;
        while(ptr < TEST.length()) {
            start.handleChar(TEST.charAt(ptr++), null);
        }
    }

    @Test
    public void testEmptyMatch() {
        HandlerBase start = createHandler("");
        int ptr = 0;
        while(ptr < TEST.length()) {
            start.handleChar(TEST.charAt(ptr++), null);
        }
    }

    @Test
    public void testNoMatch() {
        HandlerBase start = createHandler("aejfejfjke");
        int ptr = 0;
        while(ptr < TEST.length()) {
            start.handleChar(TEST.charAt(ptr++), null);
        }
    }

    @Test
    public void testMatchNoCall() {
        HandlerBase start = createHandler("dummy text");
        int ptr = 0;
        while(ptr < TEST.length()) {
            HandlerBase hb = start.handleChar(TEST.charAt(ptr++), null);
        }
    }

    @Test
    public void testMatchCall() {
        final SettableBoolean sb = new SettableBoolean();
        HandlerBase start = createHandler("dummy text", new Action1<HandlerMatchStart>() {
            @Override
            public void call(HandlerMatchStart handlerMatchStart) {
                sb.Val = true;
            }
        });
        int ptr = 0;
        while(ptr < TEST.length()) {
            HandlerBase hb = start.handleChar(TEST.charAt(ptr++), null);
        }
        Assert.assertTrue(sb.Val);
    }

    protected HandlerBase createHandler(String str) {
        return new HandlerMatchStart(str);
    }

    protected HandlerBase createHandler(String str, Action1<HandlerMatchStart> actionFunc) {
        return new HandlerMatchStart(str, actionFunc);
    }

    protected class SettableBoolean {
        public boolean Val;
    }
}
