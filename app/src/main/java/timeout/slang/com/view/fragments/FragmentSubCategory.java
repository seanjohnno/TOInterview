package timeout.slang.com.view.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.List;

import timeout.slang.com.R;
import timeout.slang.com.model.dataobjects.TOSection;
import timeout.slang.com.view.helper.HelperGetMeAJob;

public class FragmentSubCategory extends FragmentMain {

    private static final String ARG_URL = "url";

    // TODO: Rename and change types of parameters
    private String mUrl;

    private Animation mAnim;

    private View mSpinnerContainer;

    public FragmentSubCategory() {
        // Required empty public constructor
    }

    public static FragmentSubCategory newInstance(String url) {
        FragmentSubCategory fragment = new FragmentSubCategory();
        Bundle args = new Bundle();
        args.putString(ARG_URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void handleBackPress() {
        getFragmentController().releaseContentRequest(mUrl);
        super.handleBackPress();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUrl = getArguments().getString(ARG_URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        // Set visibility of spinner container and start animation
        (mSpinnerContainer = v.findViewById(R.id.spinner_container)).setVisibility(View.VISIBLE);

        mAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate);
        v.findViewById(R.id.icon_spinner).setAnimation(mAnim);
        mAnim.start();

        return v;
    }

    @Override
    protected void requestFeed() {
        getFragmentController().loadContentRequest(this, mUrl);
    }

    /**
     * @param data      Data sent along by controller
     */
    @Override
    public void call(List<TOSection> data) {
        // Stop animation + spinner
        mAnim.cancel();
        mSpinnerContainer.setVisibility(View.INVISIBLE);

        // Pass data to parent
        super.call(data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * Don't alter data,
     * @param sections
     * @return
     */
    protected List<TOSection> addJobSection(List<TOSection> sections) {
        return sections;
    }
}
