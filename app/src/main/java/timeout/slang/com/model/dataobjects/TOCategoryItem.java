package timeout.slang.com.model.dataobjects;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import timeout.slang.com.TOApp;
import timeout.slang.com.model.helper.HelperHTMLSanitizer;

public class TOCategoryItem {

    /* ------------------------------------------------------------------------------------------
     * Member Data
     * ------------------------------------------------------------------------------------------ */

    /**
     * Images on TO site are either 4:3 or 16:9
     */
    public enum AspectRatio { Ratio_4_3, Ratio_16_9, Unknown }

    /**
     * Ratio as it appears in the sites HTML
     */
    public static final String RATIO_16_9 = "16-9";

    /**
     * Ratio as it appears in the sites HTML
     */
    public static final String RATIO_4_3 = "4-3";

    /* ------------------------------------------------------------------------------------------
     * Member Data
     * ------------------------------------------------------------------------------------------ */

    /**
     * URL of category
     */
    private String mLink;

    /**
     * Category name
     */
    private String mCategoryName;

    /**
     * Category image
     */
    private String mCategoryImage;

    /**
     * Aspect ratio of image
     */
    private AspectRatio mAspectRatio = AspectRatio.Unknown;

    /**
     * Convert escaped ascii chars
     */
    private HelperHTMLSanitizer mHtmlSanitizer;

    /* ------------------------------------------------------------------------------------------
     * Construction + Getters & Setters
     * ------------------------------------------------------------------------------------------ */

    @Inject
    public TOCategoryItem(HelperHTMLSanitizer sanitizer) {
        mHtmlSanitizer = sanitizer;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String mLink) {
        this.mLink = mLink;
    }

    public String getCategoryName() {
        return mCategoryName;
    }

    public void setCategoryName(String categoryName) {
        mCategoryName = mHtmlSanitizer.sanitizeHTML(categoryName);
    }

    public String getCategoryImage() {
        return mCategoryImage;
    }

    public void setCategoryImage(String mCategoryImage) {
        this.mCategoryImage = mCategoryImage;
    }

    public void setAspectRatio(AspectRatio ratio) {
        mAspectRatio = ratio;
    }

    public void setAspectRatio(String ratio) {
        if(ratio != null) {
            if (ratio.equals(RATIO_16_9)) {
                mAspectRatio = AspectRatio.Ratio_16_9;

            } else if (ratio.equals(RATIO_4_3)) {
                mAspectRatio = AspectRatio.Ratio_4_3;

            } else {
                mAspectRatio = AspectRatio.Unknown;
            }
        } else {
            mAspectRatio = AspectRatio.Unknown;
        }
    }

    public AspectRatio getAspectRatio() {
        return mAspectRatio;
    }
}