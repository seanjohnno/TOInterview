package timeout.slang.com.timeout.model;

import timeout.slang.com.model.dataobjects.TOCategoryItem;
import timeout.slang.com.model.dataobjects.TODataObjectProvider;
import timeout.slang.com.model.helper.HelperHTMLSanitizer;


/**
 * Runs all the tests in TOCatergoryItemTest but uses the object provider instead of building directly
 */
public class TestTODataObjectProviderCategoryItem extends TestTOCategoryItem {


    @Override
    protected TOCategoryItem getCategoryItem() {
        TODataObjectProvider provider = new TODataObjectProvider(new HelperHTMLSanitizer());
        return provider.createTOCategory();
    }
}
