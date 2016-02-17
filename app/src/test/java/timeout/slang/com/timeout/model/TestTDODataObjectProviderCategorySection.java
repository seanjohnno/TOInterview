package timeout.slang.com.timeout.model;

import org.junit.Before;

import timeout.slang.com.model.dataobjects.TOCategoryItem;
import timeout.slang.com.model.dataobjects.TODataObjectProvider;
import timeout.slang.com.model.dataobjects.TOSection;
import timeout.slang.com.model.helper.HelperHTMLSanitizer;

/**
 * Runs through all TOSectionItemTest but uses provider
 */
public class TestTDODataObjectProviderCategorySection extends TOSectionItemTest {


    private TODataObjectProvider mProvider;

    @Before
    public void setup() {
        mProvider = new TODataObjectProvider(new HelperHTMLSanitizer());
    }

    protected TOCategoryItem getCategoryItem() {
        return mProvider.createTOCategory();
    }

    protected TOSection getSectionItem(String title) {
        return mProvider.createTOSection(title);
    }

    // Not used
    protected HelperHTMLSanitizer getSanitizer() {
        return null;
    }
}
