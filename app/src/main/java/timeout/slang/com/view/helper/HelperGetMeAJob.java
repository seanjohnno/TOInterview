package timeout.slang.com.view.helper;

import android.content.res.Resources;

import java.util.List;

import javax.inject.Inject;

import timeout.slang.com.ActivityFragmentController;
import timeout.slang.com.R;
import timeout.slang.com.TOApp;
import timeout.slang.com.model.dataobjects.TOCategoryItem;
import timeout.slang.com.model.dataobjects.TODataObjectProvider;
import timeout.slang.com.model.dataobjects.TOSection;
import timeout.slang.com.view.views.ViewTOImage;

/**
 * Created by MrLenovo on 17/02/2016.
 */
public class HelperGetMeAJob {

    private TODataObjectProvider mProvider;

    @Inject
    public HelperGetMeAJob(TODataObjectProvider provider) {
        mProvider = provider;
    }

    public List<TOSection> wrap(Resources rsc, List<TOSection> data) {
        if(data != null) {
            TOCategoryItem categoryItem = mProvider.createTOCategory();
            categoryItem.setCategoryImage(ViewTOImage.LOCAL_RSC + R.drawable.king);
            categoryItem.setCategoryName(rsc.getString(R.string.employment));
            categoryItem.setAspectRatio(TOCategoryItem.AspectRatio.Ratio_4_3);
            categoryItem.setLink(ActivityFragmentController.KingClick);             //

            TOSection section = mProvider.createTOSection(rsc.getString(R.string.recruitment));
            section.addItem(categoryItem);

            // Insert at start of data
            data.add(0, section);
        }
        return data;
    }
}
