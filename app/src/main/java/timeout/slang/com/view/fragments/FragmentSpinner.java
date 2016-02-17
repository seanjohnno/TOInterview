package timeout.slang.com.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import timeout.slang.com.R;

/**
 * Displays a spinning loader
 */
public class FragmentSpinner extends FragmentBase {

    public FragmentSpinner() {
        // Required empty public constructor
    }

    /* ------------------------------------------------------------------------------------------
     * From FragmentBase
     * ------------------------------------------------------------------------------------------ */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_spinner, container, false);

        // Start spinner animation
        Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate);
        v.findViewById(R.id.icon_spinner).setAnimation(anim);
        anim.start();

        return v;
    }
}
