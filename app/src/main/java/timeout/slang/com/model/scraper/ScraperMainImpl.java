package timeout.slang.com.model.scraper;


import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;
import timeout.slang.com.TOApp;
import timeout.slang.com.model.dataobjects.TOCategoryItem;
import timeout.slang.com.model.dataobjects.TODataObjectProvider;
import timeout.slang.com.model.dataobjects.TOSection;
import timeout.slang.com.model.scraper.common.CharStream;
import timeout.slang.com.model.scraper.common.handlers.HandlerBase;
import timeout.slang.com.model.scraper.common.handlers.HandlerBroadcast;
import timeout.slang.com.model.scraper.common.handlers.HandlerCaptureBetween;
import timeout.slang.com.model.scraper.common.handlers.HandlerMatchStart;

/**
 * Used to scrape website pages - Not very efficient as each char is matched against each handler.
 * Most robust impl so far though, certain things throwing state machine & regex out
 * TODO - state machine or trie to do the matching.
 */
public class ScraperMainImpl implements IScraperMain {

    private String mLastHref;

    private String mLastImg;

    private String mLastAspectRatio;

    private boolean mIsSlideShow;

    private TODataObjectProvider mProvider;

    public ScraperMainImpl(TODataObjectProvider provider) {
        mProvider = provider;
    }

    public List<TOSection> parseHTML(String html)  {

        final ArrayList<TOSection> sections = new ArrayList<>();
        final HandlerBroadcast orState = new HandlerBroadcast();

        /* Create our individual states */
        final HandlerBase captureMainTitle = new HandlerCaptureBetween("<h1>", "</h1>", new Action1<String>() { public void call(String sectionTitle) {
            sections.add(mProvider.createTOSection(sectionTitle));
        }});

        final HandlerBase captureSectionTitle = new HandlerCaptureBetween("<h2 class=\"zone_title\">", "</h2>", new Action1<String>() { public void call(String sectionTitle) {
            sections.add(mProvider.createTOSection(sectionTitle));
        }});

        HandlerBase captureHref = new HandlerCaptureBetween("href=\"", "\"", new Action1<String>() { public void call(String categoryLink) {
            mLastHref = categoryLink;
        }});

        HandlerBase captureImg = new HandlerCaptureBetween("imagesrc=\"", "\"", new Action1<String>() { public void call(String categoryImg) {
            mLastImg = categoryImg;
        }});

        HandlerBase captureRatio = new HandlerCaptureBetween("aspect-ratio-", "\"", new Action1<String>() { public void call(String aspectRatio) {
            mLastAspectRatio = aspectRatio;
        }});

        HandlerBase checkSlideshow = new HandlerMatchStart("zone slideshow", new Action1<HandlerMatchStart>() { public void call(HandlerMatchStart handler) {
            orState.removeSubHandler(captureMainTitle).removeSubHandler(captureSectionTitle).removeSubHandler(handler);
            mIsSlideShow = true;
        } } );

        HandlerBase captureTitle = new HandlerCaptureBetween("-title=\"", "\"", new Action1<String>() { public void call(String categoryTitle) {
            TOCategoryItem ci = mProvider.createTOCategory();
            ci.setCategoryName(categoryTitle);
            ci.setCategoryImage(mLastImg);
            ci.setLink(mLastHref);
            ci.setAspectRatio(mLastAspectRatio);

            // No sections, this can happen in the slideshow type page
            if(mIsSlideShow) {
                sections.add(mProvider.createTOSection(""));
            }

            TOSection lastSection = sections.get(sections.size() - 1);
            lastSection.addItem(ci);
        }});

        /* Create a broadcaster - Definitely not the fastest way but seems the most robust so far */
        orState
                .addSubHandler(captureMainTitle)
                .addSubHandler(captureSectionTitle)
                .addSubHandler(checkSlideshow)
                .addSubHandler(captureHref)
                .addSubHandler(captureImg)
                .addSubHandler(captureRatio)
                .addSubHandler(captureTitle);

        CharStream cs = new CharStream(html, orState);
        cs.stream();

        return sections;
    }
}