package timeout.slang.com.subcontroller;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import timeout.slang.com.R;
import timeout.slang.com.model.dataobjects.TOSection;
import timeout.slang.com.reusable.Func;
import timeout.slang.com.view.fragments.FragmentLoadingMain;
import timeout.slang.com.view.fragments.FragmentSubCategory;
import timeout.slang.com.view.loaders.LoaderMain;
import timeout.slang.com.view.loaders.LoaderMainBuilder;

/**
 * Created by MrLenovo on 17/02/2016.
 */
public class SubControllerDrillDown {

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
    public SubControllerDrillDown(FragmentActivity context, LoaderMainBuilder loaderMainBuilder) {
        mContext = context;
        mLoaderBuilder = loaderMainBuilder;
    }

    public void pushLoadingFragment(String url) {
        FragmentTransaction ft = mContext.getSupportFragmentManager().beginTransaction();
        ft.add(R.id.activity_root, FragmentSubCategory.newInstance(url));
        ft.addToBackStack(null);
        ft.commit();
    }

    /**
     * Start the loader
     * @param callback
     */
    public void loadFeed(final Func<List<TOSection>> callback, final String url) {

        // Create the loader
        mContext.getSupportLoaderManager().initLoader(url.hashCode(), null, new LoaderManager.LoaderCallbacks<List<TOSection>>() {

            /**
             * Called once to create loader
             */
            @Override
            public android.support.v4.content.Loader<List<TOSection>> onCreateLoader(int id, Bundle args) {
                return mLoaderBuilder.build(url);
            }

            /**
             * Called when data loaded or on config change. Pass data to callback and pop loading fragment
             */
            @Override
            public void onLoadFinished(android.support.v4.content.Loader<List<TOSection>> loader, List<TOSection> data) {
                callback.call(data);
            }

            @Override
            public void onLoaderReset(android.support.v4.content.Loader<List<TOSection>> loader) {}
        });
    }

    public void releaseFeed(String url) {
        mContext.getSupportLoaderManager().destroyLoader( url.hashCode() );
    }
}
