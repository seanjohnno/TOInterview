package timeout.slang.com.view.loaders;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import timeout.slang.com.model.ITimeoutModel;
import timeout.slang.com.model.dataobjects.TOSection;

/**
 * Used to load the main feed
 */
public class LoaderMain extends LoaderBase<List<TOSection>> {

    /* ------------------------------------------------------------------------------------------
     * Constants
     * ------------------------------------------------------------------------------------------*/

    public static final int LOADER_ID = "LoaderMain".hashCode();

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
    private String mUrl;

    /* ------------------------------------------------------------------------------------------
     * Construction & From LoaderBase
     * ------------------------------------------------------------------------------------------*/

    public LoaderMain(FragmentActivity context, ITimeoutModel model, String url) {
        super(context);
        mTimeoutModel = model;
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();



        // Get categories from ITimeoutModel and subscribe on the UI thread, call() is invoked on
        // the UI thread with the TOSection list delivered from the model
        mTimeoutModel.getCategories(mUrl)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<TOSection>>() {
                    @Override
                    public void call(List<TOSection> toSections) {
                        deliverResult(toSections);
                    }
                });
    }
}
