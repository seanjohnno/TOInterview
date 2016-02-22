package timeout.slang.com.model.impl;

import com.android.volley.RequestQueue;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timeout.slang.com.TOApp;
import timeout.slang.com.model.ITimeoutModel;
import timeout.slang.com.model.scraper.IScraperMain;
import timeout.slang.com.model.scraper.ScraperBuilder;
import timeout.slang.com.model.scraper.ScraperMainImpl;
import timeout.slang.com.model.dataobjects.TOSection;
import timeout.slang.com.model.rx.RxOnSubscribeVolleyRequest;

/**
 * Implementation if ITimeoutModel
 */
public class TimeoutModelImpl implements ITimeoutModel {

    /* ------------------------------------------------------------------------------------------
     * Member Data
     * ------------------------------------------------------------------------------------------ */

    /**
     * Volley request queue
     */
    private RequestQueue mRequestQueue;

    /**
     * Reference to an implementation of IScraperMain (grabs sections and categories from main page)
     */
    private ScraperBuilder mScraperMain;

    /* ------------------------------------------------------------------------------------------
     * Construction & From ITimeoutModel
     * ------------------------------------------------------------------------------------------ */

    /**
     * Construction
     * @param requestQueue
     * @param scraperMain
     */
    @Inject
    public TimeoutModelImpl(RequestQueue requestQueue, ScraperBuilder scraperMain) {
        mRequestQueue = requestQueue;
        mScraperMain = scraperMain;
    }

    /**
     * @return      Rx Observable, subscribe on this to receive the list of TO categories
     */
    @Override
    public void fetchData(final ITimeoutModelObserver observer, final CharSequence url) {

        // Parses HTML on io thread, then passes TOSection list back on Main thread
        Observable.create(new RxOnSubscribeVolleyRequest(mRequestQueue, url))
                .observeOn(Schedulers.io())
                .map(new Func1<String, List<TOSection>>() {
                    public List<TOSection> call(String s) {
                        return mScraperMain.buildScraper().parseHTML(s);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<TOSection>>() {
                               public void call(List<TOSection> toSections) {
                                   observer.handleCategories(toSections);
                               }
                           }, new Action1<Throwable>() {
                               public void call(Throwable throwable) {
                                   observer.handleFailure(url.toString(), throwable);
                               }
                           }
                );
    }
}
