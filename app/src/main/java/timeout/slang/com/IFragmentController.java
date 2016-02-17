package timeout.slang.com;

import java.util.List;

import timeout.slang.com.model.dataobjects.TOSection;
import timeout.slang.com.reusable.Func;

/**
 * Interface passed to fragments, allows them to inform implementing controller of events
 */
public interface IFragmentController {

    /**
     * Tell controller that we're loading the main feed
     */
    public void loadMainFeed(Func<List<TOSection>> callback);

    /**
     * @param url       URL of category clicked
     */
    public void subCategoryClicked(String url);

    /**
     * Tell controller that a request (likely from a list click) has been made
     */
    public void loadContentRequest(Func<List<TOSection>> callback, String url);

    /**
     *
     */
    public void releaseContentRequest(String url);

}
