package timeout.slang.com.subcontroller;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;

import java.util.List;

import javax.inject.Inject;

import timeout.slang.com.R;
import timeout.slang.com.model.dataobjects.TOSection;
import timeout.slang.com.reusable.Func;
import timeout.slang.com.view.fragments.FragmentLoadingMain;
import timeout.slang.com.view.loaders.LoaderMain;
import timeout.slang.com.view.loaders.LoaderMainBuilder;

/**
 * Helper class for controller, implements a specific action so Controller doesn't get massive
 */
public class SubControllerLoadMain {

    /* ------------------------------------------------------------------------------------------
     * Private Members
     * ------------------------------------------------------------------------------------------ */

    /**
     * Parent activity
     */
    private FragmentActivity mContext;

    /**
     * Used to build Loader if required
     */
    private LoaderMainBuilder mLoaderBuilder;

    /* ------------------------------------------------------------------------------------------
     * Construction + Public Methods
     * ------------------------------------------------------------------------------------------ */

    /**
     * Constructor
     * @param context               Parent activity
     * @param loaderMainBuilder    Loader used to grab main feed
     */
    @Inject
    public SubControllerLoadMain(FragmentActivity context, LoaderMainBuilder loaderMainBuilder) {
        mContext = context;
        mLoaderBuilder = loaderMainBuilder;
    }

    /**
     * Start the loader
     * @param callback
     */
    public void loadMainFeed(final Func<List<TOSection>> callback) {
        // If loader hasn't been created or there's no data yet, launch LoadingFragment
        LoaderMain l = (LoaderMain)mContext.getSupportLoaderManager().<List<TOSection>>getLoader(LoaderMain.LOADER_ID);
        if(l == null || !l.hasResultData()) {
            launchLoadingFragment();
        }

        // Create the loader
        mContext.getSupportLoaderManager().initLoader(LoaderMain.LOADER_ID, null, new LoaderManager.LoaderCallbacks<List<TOSection>>() {

            /**
             * Called once to create loader
             */
            @Override
            public android.support.v4.content.Loader<List<TOSection>> onCreateLoader(int id, Bundle args) {
                return mLoaderBuilder.build();
            }

            /**
             * Called when data loaded or on config change. Pass data to callback and pop loading fragment
             */
            @Override
            public void onLoadFinished(android.support.v4.content.Loader<List<TOSection>> loader, List<TOSection> data) {
                callback.call(data);
                popLoadingFragment();
            }

            @Override
            public void onLoaderReset(android.support.v4.content.Loader<List<TOSection>> loader) {}
        });
    }

    /* ------------------------------------------------------------------------------------------
     * Private Methods
     * ------------------------------------------------------------------------------------------ */

    /**
     * Launch the LoadingFragment while we don't have the data
     */
    private void launchLoadingFragment() {
         // Check we're not already displaying loading fragment before pushing it
        if (mContext.getSupportFragmentManager().findFragmentByTag(FragmentLoadingMain.TAG) == null) {
            FragmentTransaction ft = mContext.getSupportFragmentManager().beginTransaction();
            ft.add(R.id.activity_root, new FragmentLoadingMain(), FragmentLoadingMain.TAG);
            ft.commit();
        }
    }

    /**
     * Remove loading fragment, called once we have data we can display
     */
    private void popLoadingFragment() {
        FragmentLoadingMain loadingMain = (FragmentLoadingMain)mContext.getSupportFragmentManager().findFragmentByTag(FragmentLoadingMain.TAG);
        if(loadingMain != null) {
            loadingMain.requestLoadingFragmentFinish();
        }
    }
}
