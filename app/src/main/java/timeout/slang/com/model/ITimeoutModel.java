package timeout.slang.com.model;

import java.util.List;

import rx.Observable;
import timeout.slang.com.model.dataobjects.TOSection;

/**
 * Model class - uses RxJava to do the Future jazz
 */
public interface ITimeoutModel {

    /**
     * @return      Rx Observable, subscribe on this to receive the list of TO categories
     */
    public Observable<List<TOSection>> getCategories(String url);
}
