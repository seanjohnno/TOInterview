package timeout.slang.com.timeout.scraper;

import junit.framework.Assert;

import org.junit.Test;

import rx.functions.Action1;
import timeout.slang.com.model.scraper.common.handlers.HandlerBase;
import timeout.slang.com.model.scraper.common.handlers.HandlerCaptureBetween;
import timeout.slang.com.model.scraper.common.handlers.HandlerMatchStart;

/**
 * Created by MrLenovo on 17/02/2016.
 */
public class TestCaptureBetween {


    public static final String TEST = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";

    @Test
    public void testNullMatch() {
        createAndLoopThrough(null, null);
        createAndLoopThrough(null, "");
        createAndLoopThrough("", null);
        createAndLoopThrough("", "");
        createAndLoopThrough("", "ajadflddlk");
        createAndLoopThrough("csjashjcsasj", "");

    }

    @Test
    public void testMatchNoCall() {
        createAndLoopThrough("simply", "text");
    }


    @Test
    public void testMatchCall() {
        final StringBuilder sb = new StringBuilder();
        HandlerBase start = new HandlerCaptureBetween("printing", "industry", new Action1<String>() {
            @Override
            public void call(String handlerMatchStart) {
                sb.append(handlerMatchStart);
            }
        });
        int ptr = 0;
        while(ptr < TEST.length()) {
            HandlerBase hb = start.handleChar(TEST.charAt(ptr++), null);
        }
        Assert.assertEquals(sb.toString(), " and typesetting ");
    }


    protected void createAndLoopThrough(String start, String end) {
        HandlerBase hb = createHandler(null, null);
        int ptr = 0;
        while(ptr < TEST.length()) {
            hb.handleChar(TEST.charAt(ptr++), null);
        }
    }

    protected HandlerBase createHandler(String start, String end) {
        return new HandlerCaptureBetween(start, end, null);
    }

    protected class SettableBoolean {
        public boolean Val;
    }
}
