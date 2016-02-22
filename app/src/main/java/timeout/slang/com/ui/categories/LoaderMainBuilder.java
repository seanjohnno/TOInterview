package timeout.slang.com.ui.categories;

import android.support.v4.app.FragmentActivity;

import javax.inject.Inject;

import timeout.slang.com.model.ITimeoutModel;
import timeout.slang.com.ui.LoaderTimeoutModel;

/**
 * Just here to make the DI injection less awkward and not create a loader class if we don't need one
 */
public class LoaderMainBuilder {

    /* ------------------------------------------------------------------------------------------
     * Constants
     * ------------------------------------------------------------------------------------------ */

    /**
     * Base URL for timeout data (urls on clicks are relative from here)
     */
    private static final String BASE_URL = "http://www.timeout.com";

    /**
     * URL of main page
     */
    private static final String START_URL = BASE_URL + "/london";

    /* ------------------------------------------------------------------------------------------
     * Member Data
     * ------------------------------------------------------------------------------------------ */

    private FragmentActivity mContext;

    private ITimeoutModel mModel;

    @Inject
    public LoaderMainBuilder(FragmentActivity context, ITimeoutModel model) {
        mContext = context;
        mModel = model;
    }

    public LoaderTimeoutModel build() {
        return new LoaderTimeoutModel(mContext, mModel, START_URL);
    }

    public LoaderTimeoutModel build(String url) {
        return new LoaderTimeoutModel(mContext, mModel, BASE_URL + url);
    }
}
