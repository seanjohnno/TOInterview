package timeout.slang.com;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import java.util.List;

import dagger.ObjectGraph;
import timeout.slang.com.subcontroller.SubControllerLoadMain;
import timeout.slang.com.di.ModuleContext;
import timeout.slang.com.model.dataobjects.TOSection;
import timeout.slang.com.reusable.Func;
import timeout.slang.com.subcontroller.SubControllerDrillDown;
import timeout.slang.com.view.fragments.FragmentBase;

/**
 * Container activity for fragments
 */
public class ActivityFragmentController extends FragmentActivity implements IFragmentController {

    /* ------------------------------------------------------------------------------------------
     * Constants
     * ------------------------------------------------------------------------------------------ */

    public static final String KingClick = "local://kingclick";

    /**
     * Controller class to control display of FragmentLoadingMain
     */
    private SubControllerLoadMain mLoadMain;

    /**
     * Controller class to control display of sub-categories
     */
    private SubControllerDrillDown mLoadDrillDown;

    /**
     * Dagger ObjectGraph - used to inject this context, scoped so its released when the Activity is destroyed
     */
    private ObjectGraph mActivityGraph;

    /* ------------------------------------------------------------------------------------------
     * From FragmentActivity
     * ------------------------------------------------------------------------------------------ */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Dagger injections
        mActivityGraph = TOApp.getInstance().createScopedGraph(new ModuleContext(TOApp.getInstance().getGraph(), this));
        mLoadMain = mActivityGraph.get(SubControllerLoadMain.class);
        mLoadDrillDown = mActivityGraph.get(SubControllerDrillDown.class);

        //
        setContentView(R.layout.activity_fragment_controller);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivityGraph = null;
    }

    @Override
    public void onBackPressed() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if(!fragments.isEmpty()) {
            Fragment last = fragments.get(fragments.size()-1);
            if(last instanceof FragmentBase) {
                ((FragmentBase)last).handleBackPress();
            }
        }
        super.onBackPressed();
    }

    /* ------------------------------------------------------------------------------------------
     * From IViewPopulator
     * ------------------------------------------------------------------------------------------ */

    /**
     * Load main feed and display loading fragment while it's loading
     * @param callback      Callback function to pass results to
     */
    public void loadMainFeed(Func<List<TOSection>> callback) {
        mLoadMain.loadMainFeed(callback);
    }


    public void subCategoryClicked(String url) {
        if(KingClick.equals(url)) {
            Toast.makeText(this, R.string.yosser, Toast.LENGTH_LONG).show();
        } else {
            mLoadDrillDown.pushLoadingFragment(url);
        }
    }

    /**
     * Tell controller that a request (likely from a list click) has been made
     */
    public void loadContentRequest(Func<List<TOSection>> callback, String url) {
        mLoadDrillDown.loadFeed(callback, url);
    }

    public void releaseContentRequest(String url) {
        mLoadDrillDown.releaseFeed(url);
    }

}
