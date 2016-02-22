package timeout.slang.com.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import java.util.List;

import javax.inject.Inject;

import timeout.slang.com.IFragmentController;
import timeout.slang.com.TOApp;
import timeout.slang.com.model.ITimeoutModel;
import timeout.slang.com.model.dataobjects.TOSection;
import timeout.slang.com.ui.categories.ILoaderDataConnector;

/**
 * Used to store reference to IViewPopulator for access in subclasses
 */
public abstract class FragmentBase extends Fragment implements ITimeoutModel.ITimeoutModelObserver {

    /* ------------------------------------------------------------------------------------------
     * Private Members
     * ------------------------------------------------------------------------------------------ */

    private IFragmentController mController;

    private Context mActivity;

    @Inject
    ITimeoutModel mModel;

    /* ------------------------------------------------------------------------------------------
     * Construction & From FragmentBase
     * ------------------------------------------------------------------------------------------ */

    public FragmentBase() {
        TOApp.getInstance().inject(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mController = (IFragmentController)context;
        mActivity = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mController = null;
        mActivity = null;
    }

    /* ------------------------------------------------------------------------------------------
     * Public Methods
     * ------------------------------------------------------------------------------------------ */

    public boolean handleBackPress() {
        return false;
    }

    public void performRequest(final CharSequence url) {
        getLoaderManager().initLoader(url.hashCode(), null, new LoaderManager.LoaderCallbacks<ILoaderDataConnector>() {

            @Override
            public Loader<ILoaderDataConnector> onCreateLoader(int id, Bundle args) {
                return new LoaderTimeoutModel(mActivity, mModel, url);
            }

            @Override
            public void onLoadFinished(Loader<ILoaderDataConnector> loader, ILoaderDataConnector data) {
                data.passResponse(FragmentBase.this);
            }

            @Override
            public void onLoaderReset(Loader<ILoaderDataConnector> loader) {}
        });
    }

    public void releaseRequest(CharSequence url) {
        getLoaderManager().destroyLoader(url.hashCode());
    }

    public boolean hasDataForRequest(CharSequence url) {
        return getLoaderManager().getLoader(url.hashCode()) != null;
    }

    /* ------------------------------------------------------------------------------------------
     * Protected Methods
     * ------------------------------------------------------------------------------------------ */

    protected IFragmentController getFragmentController() {
        return mController;
    }

    /* ------------------------------------------------------------------------------------------
     * Abstract Methods
     * ------------------------------------------------------------------------------------------ */

    @Override
    public abstract void handleCategories(List<TOSection> sections);

    @Override
    public abstract void handleFailure(String url, Throwable t);
}
