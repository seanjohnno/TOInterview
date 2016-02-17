package timeout.slang.com.model.scraper;


import javax.inject.Inject;

import timeout.slang.com.model.dataobjects.TODataObjectProvider;

public class ScraperBuilder {

    private TODataObjectProvider mProvider;

    @Inject
    public ScraperBuilder(TODataObjectProvider provider) {
        mProvider = provider;
    }

    public IScraperMain buildScraper() {
        return new ScraperMainImpl(mProvider);
    }
}
