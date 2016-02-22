package timeout.slang.com;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import java.util.List;

import timeout.slang.com.ui.FragmentBase;
import timeout.slang.com.ui.loading.FragmentSplash;

/**
 * Container activity for fragments
 */
public class ActivityFragmentController extends FragmentActivity implements IFragmentController {

    /* ------------------------------------------------------------------------------------------
     * From FragmentActivity
     * ------------------------------------------------------------------------------------------ */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fragment_controller);
    }

    @Override
    public void onBackPressed() {
        // Loop through fragments
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if(!fragments.isEmpty()) {

            // Pull last fragment, -1 seems to be null...
            Fragment last = null;
            for(int i = fragments.size() - 1; last == null && i > -1; i--) {
                last = fragments.get(i);
            }

            // They should be all FragmentBase but check any
            if(last instanceof FragmentBase) {

                // If any return true then back click has been handled
                if( ((FragmentBase)last).handleBackPress() == true) {
                    return;
                }
            }
        }
        super.onBackPressed();
    }

    /* ------------------------------------------------------------------------------------------
     * From IFragmentController
     * ------------------------------------------------------------------------------------------ */

    @Override
    public void handleLoadingInitialContent() {
        if((FragmentSplash)getSupportFragmentManager().findFragmentByTag(FragmentSplash.TAG) == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.activity_root, new FragmentSplash(), FragmentSplash.TAG);
            ft.commit();
        }
    }

    @Override
    public void handleInitialContentLoaded() {
        FragmentSplash fs = (FragmentSplash)getSupportFragmentManager().findFragmentByTag(FragmentSplash.TAG);
        if(fs != null) {
            fs.requestLoadingFragmentFinish();
        }
    }
}
