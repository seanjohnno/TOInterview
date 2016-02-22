package timeout.slang.com.ui.categories;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import timeout.slang.com.R;
import timeout.slang.com.TOApp;
import timeout.slang.com.model.dataobjects.TOCategoryItem;
import timeout.slang.com.model.dataobjects.TODataObjectProvider;
import timeout.slang.com.model.dataobjects.TOSection;

import timeout.slang.com.ui.FragmentBase;
import timeout.slang.com.ui.views.ViewTOImage;

/**
 * Used to hold the RecyclerView and display the main feed
 */
public class FragmentCategories extends FragmentBase implements ICategorySelectListener{

    /* ------------------------------------------------------------------------------------------
     * Private Members
     * ------------------------------------------------------------------------------------------ */

    private static final String VIEW_MODEL = "view_model";

    private static final String BASE_URL = "http://www.timeout.com";

    private static final String START_URL = BASE_URL + "/london";

    /* ------------------------------------------------------------------------------------------
     * Private Members
     * ------------------------------------------------------------------------------------------ */

    private ViewModelCategories mViewModel;

    private RecyclerView mListView;

    private View mSpinnerBg;

    private View mSpinner;

    @Inject
    TODataObjectProvider mDataObjectProvider;

    /* ------------------------------------------------------------------------------------------
     * Construction & From FragmentBase
     * ------------------------------------------------------------------------------------------ */

    public FragmentCategories() {
        TOApp.getInstance().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get url stack (or create with base if there isn't one)
        if(savedInstanceState == null || (mViewModel = savedInstanceState.getParcelable(VIEW_MODEL)) == null) {
            mViewModel = new ViewModelCategories();
            mViewModel.push(START_URL);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Write stack
        outState.putParcelable(VIEW_MODEL, mViewModel);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        mListView = (RecyclerView)v.findViewById(R.id.main_list_id);
        mListView.setLayoutManager(new LinearLayoutManager(getContext()));

        mSpinnerBg = v.findViewById(R.id.spinner_container);
        mSpinner = v.findViewById(R.id.icon_spinner);

        mViewModel.creatingFragment();
        sync();

        return v;
    }

    @Override
    public boolean handleBackPress() {
        ViewModelCategories.CategoryLvl cat = mViewModel.pop();
        if(cat != null) {
            releaseRequest(cat.getUrl());
        }

        if(mViewModel.peek() != null) {
            sync();
            return true;
        }

        return false;
    }

    /* ------------------------------------------------------------------------------------------
     * Protected Methods
     * ------------------------------------------------------------------------------------------ */

    @Override
    public void handleCategories(List<TOSection> sections) {
        if(sections == null || sections.size() == 0) {
            handleFailure("", null);
        } else {
            sync(sections);
        }
    }

    @Override
    public void handleFailure(String url, Throwable t) {
        TOCategoryItem categoryItem = mDataObjectProvider.createTOCategory();
        categoryItem.setCategoryName(getString(R.string.glum));
        categoryItem.setAspectRatio(TOCategoryItem.AspectRatio.Ratio_4_3);
        categoryItem.setCategoryImage(ViewTOImage.LOCAL_RSC + R.drawable.grumpy);

        TOSection section = mDataObjectProvider.createTOSection(getString(R.string.error));
        section.addItem(categoryItem);

        // Set sections
        List<TOSection> sections = new ArrayList<>();
        sections.add(section);

        sync(sections);
    }

    /* ------------------------------------------------------------------------------------------
     * From ICatgorySelectListener
     * ------------------------------------------------------------------------------------------ */

    public void handleCategorySelect(String categoryUrl) {
        if(categoryUrl != null) {
            mViewModel.push(BASE_URL + categoryUrl);
            sync();
        } else {
            Toast.makeText(getContext(), R.string.null_url, Toast.LENGTH_LONG).show();
        }
    }

     /* ------------------------------------------------------------------------------------------
     * Private Methods
     * ------------------------------------------------------------------------------------------ */

    private void sync() {
        sync(null);
    }

    private void sync(List<TOSection> sections) {
        ViewModelCategories.CategoryLvl lvl = mViewModel.peek();

        if(sections != null) {
            lvl.setDataReceived();
        }

        // Check if we're awaiting initial content (need to display special loader for that)
        if(mViewModel.isInitialContent() && !hasDataForRequest(lvl.getUrl())) {
            getFragmentController().handleLoadingInitialContent();

        // Check if we've loaded initial content
        } else if(START_URL.equals(mViewModel.peek().getUrl())) {
            getFragmentController().handleInitialContentLoaded();
        }

        // Request hasn't been made yet...
        if(!lvl.isRequestMade()) {
            startLoading();
            lvl.setRequestMade();
            performRequest(lvl.getUrl());

        // Request has been made but we're still waiting on data
        } else if(!lvl.isDataReceived()) {
            startLoading();

        // We have all data requested, stop loading
        } else {
            stopLoading();
            mListView.setAdapter(new AdapterCategories(sections, this));
        }
    }

    private void startLoading() {
        mSpinnerBg.setVisibility(View.VISIBLE);
        mSpinner.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.rotate));
    }

    private void stopLoading() {
        mSpinnerBg.setVisibility(View.INVISIBLE);
        mSpinner.clearAnimation();
    }
}
