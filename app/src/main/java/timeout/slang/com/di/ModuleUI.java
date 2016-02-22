package timeout.slang.com.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import timeout.slang.com.ui.helper.HelperTOImage;
import timeout.slang.com.ui.views.ViewTOImage;

/**
 * Dagger module for UI classes
 */
@Module(library = true, injects = {ViewTOImage.class})
public class ModuleUI {
    @Provides @Singleton public HelperTOImage providesHelperTOImage() {
        return new HelperTOImage();
    }
}
