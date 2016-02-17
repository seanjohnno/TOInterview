package timeout.slang.com.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import timeout.slang.com.view.helper.HelperTOImage;
import timeout.slang.com.view.views.ViewTOImage;

/**
 * Dagger module for UI classes
 */
@Module(library = true, injects = {ViewTOImage.class})
public class ModuleUI {
    @Provides @Singleton public HelperTOImage providesHelperTOImage() {
        return new HelperTOImage();
    }
}
