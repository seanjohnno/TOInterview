package timeout.slang.com.model.scraper.common.handlers;

import java.util.ArrayList;
import java.util.List;

import timeout.slang.com.model.scraper.common.CharStream;

/**
 * Created by MrLenovo on 17/02/2016.
 */
public class HandlerBroadcast extends HandlerBase {

    private List<HandlerBase> mSubHandlers = new ArrayList<>();

    public HandlerBroadcast() {

    }

    public HandlerBroadcast addSubHandler(HandlerBase handler) {
        if(handler != null) {
            mSubHandlers.add(handler);
        }
        return this;
    }

    public HandlerBroadcast removeSubHandler(HandlerBase handler) {
        if(handler != null) {
            mSubHandlers.remove(handler);
        }
        return this;
    }

    public int getSize() {
        return mSubHandlers.size();
    }

    @Override
    public HandlerBase handleChar(char c, CharStream cpp) {
        for(int i = 0; mSubHandlers != null && i < mSubHandlers.size(); i++) {
            try {
                mSubHandlers.get(i).handleChar(c, cpp);
                // So one handler doesn't bring everything else down, downside is its silent :/
            }catch(Exception e){}
        }
        return this;
    }

    @Override
    public void setActiveHandler() {
        for(int i = 0; mSubHandlers != null && i < mSubHandlers.size(); i++) {
            try {
                mSubHandlers.get(i).setActiveHandler();
                // So one handler doesn't bring everything else down, downside is its silent :/
            }catch(Exception e){}
        }
    }
}
