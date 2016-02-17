package timeout.slang.com.model.dataobjects;

import javax.inject.Inject;

import timeout.slang.com.model.helper.HelperHTMLSanitizer;

/**
 * Created by MrLenovo on 17/02/2016.
 */
public class TODataObjectProvider {

    private HelperHTMLSanitizer mSanitizer;

    @Inject
    public TODataObjectProvider(HelperHTMLSanitizer sanitizer) {
        mSanitizer = sanitizer;
    }

    public TOSection createTOSection(String title) {
        return new TOSection(title, mSanitizer);
    }

    public TOCategoryItem createTOCategory() {
        return new TOCategoryItem(mSanitizer);
    }
}
