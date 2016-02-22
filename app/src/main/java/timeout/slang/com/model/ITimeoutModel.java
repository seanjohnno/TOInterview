package timeout.slang.com.model;

import java.util.List;

import rx.Observable;
import timeout.slang.com.model.dataobjects.TOSection;

/**
 * Model class - uses RxJava to do the Future jazz
 */
public interface ITimeoutModel {

    public interface ITimeoutModelObserver {
        public void handleCategories(List<TOSection> sections);
        public void handleFailure(String url, Throwable t);
    }

    public void fetchData(ITimeoutModelObserver observer, CharSequence url);
}
