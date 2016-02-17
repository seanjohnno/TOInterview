package timeout.slang.com.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import java.util.List;

import javax.inject.Inject;

import timeout.slang.com.R;
import timeout.slang.com.TOApp;
import timeout.slang.com.model.dataobjects.TOCategoryItem;
import timeout.slang.com.model.dataobjects.TODataObjectProvider;
import timeout.slang.com.model.dataobjects.TOSection;
import timeout.slang.com.reusable.Func;
import timeout.slang.com.view.adapters.AdapterMain;
import timeout.slang.com.view.helper.HelperGetMeAJob;
import timeout.slang.com.view.views.ViewTOImage;

/**
 * Used to hold the RecyclerView and display the main feed
 */
public class FragmentMain extends FragmentBase implements Func<List<TOSection>> {

    /* ------------------------------------------------------------------------------------------
     * Private Members
     * ------------------------------------------------------------------------------------------ */

    private RecyclerView mListView;

    private AdapterMain mListAdapter;

    @Inject
    TODataObjectProvider mDataObjectProvider;

    @Inject
    HelperGetMeAJob mHelper;

    /* ------------------------------------------------------------------------------------------
     * Construction & From FragmentBase
     * ------------------------------------------------------------------------------------------ */

    public FragmentMain() {
        TOApp.getInstance().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        mListView = (RecyclerView)v.findViewById(R.id.main_list_id);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        requestFeed();
    }

    /* ------------------------------------------------------------------------------------------
     * From Func<List<TOSection>>
     * ------------------------------------------------------------------------------------------ */

    /**
     * @param data      Data sent along by controller
     */
    @Override
    public void call(List<TOSection> data) {

        if(mListAdapter == null) {
            mListView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

            // We don't have a handler/parser for this type of data yet, this is a bit of a hack in
            // lieu of proper error handling...
            if(data.size() == 0) {
                TOCategoryItem categoryItem = mDataObjectProvider.createTOCategory();
                categoryItem.setCategoryName(getString(R.string.glum));
                categoryItem.setAspectRatio(TOCategoryItem.AspectRatio.Ratio_4_3);
                categoryItem.setCategoryImage(ViewTOImage.LOCAL_RSC + R.drawable.grumpy);

                TOSection section = mDataObjectProvider.createTOSection(getString(R.string.error));
                section.addItem(categoryItem);

                data.add(section);

            // We have data
            } else {
                data = addJobSection(data);
            }

            mListView.setAdapter(mListAdapter = new AdapterMain(data));
        }
    }

    /* ------------------------------------------------------------------------------------------
     * Protected Methods
     * ------------------------------------------------------------------------------------------ */

    protected void requestFeed() {
        getFragmentController().loadMainFeed(this);
    }

    protected List<TOSection> addJobSection(List<TOSection> sections) {
        return mHelper.wrap(getResources(), sections);
    }
}
