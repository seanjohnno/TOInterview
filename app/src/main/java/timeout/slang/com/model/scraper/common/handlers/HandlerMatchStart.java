package timeout.slang.com.model.scraper.common.handlers;

import rx.functions.Action1;
import timeout.slang.com.model.scraper.common.CharStream;

public class HandlerMatchStart extends HandlerBase {

    private String mStart;

    private int mPtr;

    private Action1<HandlerMatchStart> mMatchAction;

    public HandlerMatchStart(String start) {
        this(start, null);
    }

    public HandlerMatchStart(String start, Action1<HandlerMatchStart> matchAction) {
        mStart = start;
        mMatchAction = matchAction;
    }

    @Override
    public HandlerBase handleChar(char c, CharStream cpp) {
        // If we match
        if (mStart != null && mStart.length() > 0 && c == mStart.charAt(mPtr)) {

            // If we've entered captured state
            if (++mPtr == mStart.length()) {
                if(mMatchAction != null) {
                    mMatchAction.call(this);
                }
                setActiveHandler();
                return startMatched();
            }
        } else {
            mPtr = 0;
        }
        return this;
    }

    protected HandlerBase startMatched() {
        return mFollowingState;
    }

    public void setActiveHandler() {
        mPtr = 0;
    }
}
