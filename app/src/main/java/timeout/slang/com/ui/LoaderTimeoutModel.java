package timeout.slang.com.ui;

import android.content.Context;
import android.support.v4.content.Loader;

import java.util.List;

import timeout.slang.com.model.ITimeoutModel;
import timeout.slang.com.model.dataobjects.TOSection;
import timeout.slang.com.ui.categories.ILoaderDataConnector;

/**
 * Used to load the main feed
 */
public class LoaderTimeoutModel extends Loader<ILoaderDataConnector> implements ITimeoutModel.ITimeoutModelObserver {

    /* ------------------------------------------------------------------------------------------
     * Private Members
     * ------------------------------------------------------------------------------------------*/

    /**
     * Reference to our model
     * ps I don't like that you have to make this non-private for Dagger, bleh
     */
    private ITimeoutModel mTimeoutModel;

    /**
     * Url we're loading
     */
    private CharSequence mUrl;

    /* ------------------------------------------------------------------------------------------
     * Construction & From LoaderBase
     * ------------------------------------------------------------------------------------------*/

    public LoaderTimeoutModel(Context context, ITimeoutModel model, CharSequence url) {
        super(context);
        mTimeoutModel = model;
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        mTimeoutModel.fetchData(this, mUrl);
    }

    /* ------------------------------------------------------------------------------------------
     * ITimeoutModel.ITimeoutModelObserver
     * ------------------------------------------------------------------------------------------*/

    @Override
    public void handleCategories(final List<TOSection> sections) {
        deliverResult(new ILoaderDataConnector() {
            @Override
            public void passResponse(ITimeoutModel.ITimeoutModelObserver observer) {
                observer.handleCategories(sections);
            }
        });
    }

    @Override
    public void handleFailure(final String url, final Throwable t) {
        deliverResult(new ILoaderDataConnector() {
            @Override
            public void passResponse(ITimeoutModel.ITimeoutModelObserver observer) {
                observer.handleFailure(url, t);
            }
        });
    }
}
