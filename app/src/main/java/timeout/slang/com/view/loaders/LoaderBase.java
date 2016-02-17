package timeout.slang.com.view.loaders;

import android.content.Context;
import android.support.v4.content.Loader;

/**
 * Base class for loaders - Just allows us to query whether loader already has data
 */
public abstract class LoaderBase<T> extends Loader<T> {

    /* ------------------------------------------------------------------------------------------
     * Private Members
     * ------------------------------------------------------------------------------------------*/

    // Store result data
    private T mResultData;

    /* ------------------------------------------------------------------------------------------
     * Construction & From Loader<T>
     * ------------------------------------------------------------------------------------------*/

    /**
     * Constructor
     */
    protected LoaderBase(Context context) {
        super(context);
    }

    /**
     * Called by subclasses when they're delivering the data
     * @param data      Result data
     */
    @Override
    public synchronized void deliverResult(T data) {
        mResultData = data;
        super.deliverResult(data);
    }

    /* ------------------------------------------------------------------------------------------
     * Public Methods
     * ------------------------------------------------------------------------------------------*/

    /**
     * @return      true if we already have result data
     */
    public synchronized boolean hasResultData() {
        return mResultData != null;
    }
}
