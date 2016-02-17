package timeout.slang.com.model.scraper;

import java.util.List;

import timeout.slang.com.model.dataobjects.TOSection;

/**
 * Interface of the scraper used to get the main page
 */
public interface IScraperMain {

    /**
     * @param str       Pages HTML
     * @return          List of TOSections containing TOCategories
     */
    public List<TOSection> parseHTML(String str);
}
