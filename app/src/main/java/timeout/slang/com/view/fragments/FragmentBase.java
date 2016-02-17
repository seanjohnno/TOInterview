package timeout.slang.com.view.fragments;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import timeout.slang.com.IFragmentController;

/**
 * Used to store reference to IViewPopulator for access in subclasses
 */
public abstract class FragmentBase extends Fragment {

    /* ------------------------------------------------------------------------------------------
     * Private Members
     * ------------------------------------------------------------------------------------------ */

    private IFragmentController mController;

    private Context mActivity;

    /* ------------------------------------------------------------------------------------------
     * Construction & From FragmentBase
     * ------------------------------------------------------------------------------------------ */

    public FragmentBase() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mController = (IFragmentController)context;
        mActivity = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mController = null;
        mActivity = null;
    }

    /* ------------------------------------------------------------------------------------------
     * Public Methods
     * ------------------------------------------------------------------------------------------ */

    public void handleBackPress() {

    }

    /* ------------------------------------------------------------------------------------------
     * Protected Methods
     * ------------------------------------------------------------------------------------------ */

    protected IFragmentController getFragmentController() {
        return mController;
    }
}
