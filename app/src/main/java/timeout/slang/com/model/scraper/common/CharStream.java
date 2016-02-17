package timeout.slang.com.model.scraper.common;

/**
 * Char stream that passes characters along to handler but also allows it to pull them
 */
public class CharStream {

    /* ------------------------------------------------------------------------------------------
     * Interface for handlers to implement
     * ------------------------------------------------------------------------------------------ */

    public interface ICharHandler {
        public ICharHandler handleChar(char c, CharStream cpp);
        public void setActiveHandler();
    }

    /* ------------------------------------------------------------------------------------------
     * Member Data
     * ------------------------------------------------------------------------------------------ */

    /**
     * Handler to pass chars to
     */
    private ICharHandler mHandler;

    /**
     * HTML string we're looping through
     */
    private String mStr;

    /**
     * Current string index
     */
    private int mPtr;

    /* ------------------------------------------------------------------------------------------
     * Constructor + Public Methods
     * ------------------------------------------------------------------------------------------ */

    /**
     * @param str       HTML string
     * @param handler   Initial handler
     */
    public CharStream(String str, ICharHandler handler) {
        mStr = str;
        mHandler = handler;
    }

    /**
     * Start looping through HTML string and passing characters to current handler
     */
    public void stream() {
        if(mStr != null) {
            if(mHandler != null) mHandler.setActiveHandler();
            for (mPtr = 0; mPtr < mStr.length(); mPtr++) {
                try {
                    ICharHandler handler = mHandler.handleChar(mStr.charAt(mPtr), this);
                    if (handler != mHandler) {
                        handler.setActiveHandler();
                        mHandler = handler;
                    }
                } catch (Exception e) {
                    return;
                }
            }
        }
    }
}
