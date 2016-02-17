package timeout.slang.com.model.scraper.common.handlers;

import rx.functions.Action1;
import timeout.slang.com.model.scraper.common.CharStream;

/**
 * Created by MrLenovo on 17/02/2016.
 */
public class HandlerCaptureBetween extends HandlerMatchStart {

    private StringBuilder mBuilder = new StringBuilder();

    private int mEndPtr = -1;

    private String mEnd;

    private Action1<String> mActionFunc;

    public HandlerCaptureBetween(String start, String end, Action1<String> actionFunc) {
        super(start);
        mEnd = end;
        mActionFunc = actionFunc;
    }

    @Override
    public HandlerBase handleChar(char c, CharStream cpp) {

        if(mEndPtr == -1) {
            super.handleChar(c, cpp);
        } else {
            mBuilder.append(c);

            if(c == mEnd.charAt(mEndPtr)) {
                mEndPtr++;
            } else {
                mEndPtr = 0;
            }
        }

        if(mEnd != null && mEnd.length() > 0 && mEndPtr == mEnd.length()) {
            mBuilder.setLength(mBuilder.length() - mEnd.length());
            mActionFunc.call(mBuilder.toString());

            setActiveHandler();
            return this;
        }

        return this;
    }

    protected HandlerBase startMatched() {
        mEndPtr = 0;
        return null;
    }

    @Override
    public void setActiveHandler() {
        super.setActiveHandler();
        mEndPtr = -1;
        mBuilder.setLength(0);
    }
}