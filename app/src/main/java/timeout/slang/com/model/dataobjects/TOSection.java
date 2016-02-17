package timeout.slang.com.model.dataobjects;

import android.text.Html;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import timeout.slang.com.model.dataobjects.TOCategoryItem;
import timeout.slang.com.model.helper.HelperHTMLSanitizer;

/**
 * Represents a 'zone' on the website. Has sub-categories
 */
public class TOSection {

    /* ------------------------------------------------------------------------------------------
     * Member Data
     * ------------------------------------------------------------------------------------------ */

    /**
     * List of subcategories
     */
    private List<TOCategoryItem> mCategoryItems;

    /**
     * Section title
     */
    private String mTitle;

    /**
     * Convert escaped ascii chars
     */
    private HelperHTMLSanitizer mHtmlSanitizer;

    /* ------------------------------------------------------------------------------------------
     * Construction + Getters & Setters
     * ------------------------------------------------------------------------------------------ */

    @Inject
    public TOSection(String title, HelperHTMLSanitizer sanitizer) {
        mHtmlSanitizer = sanitizer;
        mCategoryItems = new ArrayList<>();
        setTitle(title);
    }

    public TOSection setTitle(String title) {
        mTitle = (title == null ? null : mHtmlSanitizer.sanitizeHTML(title));
        return this;
    }

    public String getTitle() {
        return mTitle;
    }

    public List<TOCategoryItem> getCategoryItems() {
        return mCategoryItems;
    }

    public void addItem(TOCategoryItem item) {
        if(item != null) {
            mCategoryItems.add(item);
        }
    }
}
