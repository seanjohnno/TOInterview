package timeout.slang.com;

import java.util.List;

import timeout.slang.com.model.dataobjects.TOSection;
import timeout.slang.com.reusable.Func;

/**
 * Interface passed to fragments, allows them to inform implementing controller of events
 */
public interface IFragmentController {

    public static final String KingClick = "localclick://";

    public void handleLoadingInitialContent();

    public void handleInitialContentLoaded();

}
