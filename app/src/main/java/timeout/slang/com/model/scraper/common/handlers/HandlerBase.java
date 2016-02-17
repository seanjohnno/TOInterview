package timeout.slang.com.model.scraper.common.handlers;

import timeout.slang.com.model.scraper.common.CharStream;

/**
 * Created by MrLenovo on 17/02/2016.
 */
public abstract class HandlerBase implements CharStream.ICharHandler {

    protected HandlerBase mFollowingState;

    public abstract HandlerBase handleChar(char c, CharStream cpp);

    public abstract void setActiveHandler();

    public HandlerBase setFollowingState(HandlerBase followingState) {
        mFollowingState = followingState;
        return followingState;
    }
}