package timeout.slang.com.di;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import timeout.slang.com.model.ITimeoutModel;
import timeout.slang.com.model.dataobjects.TODataObjectProvider;
import timeout.slang.com.model.helper.HelperHTMLSanitizer;
import timeout.slang.com.model.impl.TimeoutModelImpl;
import timeout.slang.com.model.scraper.ScraperBuilder;
import timeout.slang.com.ui.FragmentBase;
import timeout.slang.com.ui.categories.FragmentCategories;
import timeout.slang.com.ui.helper.HelperGetMeAJob;

/**
 * Dagger module for model classes
 */
@Module(library = true, injects = { TimeoutModelImpl.class, TODataObjectProvider.class,
        ScraperBuilder.class, FragmentCategories.class, HelperGetMeAJob.class, FragmentBase.class })
public class ModuleModel {

    private final Application mApp;

    public ModuleModel(Application app) {
        mApp = app;
    }

    @Provides @Singleton @ForApplication Context provideApplicationContext() {
        return mApp;
    }

    @Provides @Singleton public RequestQueue providesRequestQueue() {
        return Volley.newRequestQueue(mApp);
    }

    @Provides @Singleton public ScraperBuilder providesScraperBuilder(TODataObjectProvider objectProvider) {
        return new ScraperBuilder(objectProvider);
    }

    @Provides @Singleton public ITimeoutModel providesModel(RequestQueue queue, ScraperBuilder scraper) {
        return new TimeoutModelImpl(queue, scraper);
    }

    @Provides @Singleton public HelperHTMLSanitizer provideHtmlSanitizer() {
        return new HelperHTMLSanitizer();
    }

    @Provides public TODataObjectProvider provideDOProvider(HelperHTMLSanitizer sanitizer) {
        return new TODataObjectProvider(sanitizer);
    }

    @Provides @Singleton public HelperGetMeAJob providesHelper(TODataObjectProvider provider) {
        return new HelperGetMeAJob(provider);
    }
}
