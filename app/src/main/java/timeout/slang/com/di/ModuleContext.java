package timeout.slang.com.di;

import android.support.v4.app.FragmentActivity;

import dagger.Module;
import dagger.ObjectGraph;
import dagger.Provides;
import timeout.slang.com.model.ITimeoutModel;
import timeout.slang.com.model.impl.TimeoutModelImpl;
import timeout.slang.com.ui.FragmentBase;
import timeout.slang.com.ui.categories.LoaderMainBuilder;

/**
 * Dagger module for scoped graph (when we need to inject Context)
 */
@Module(library = true, injects = {LoaderMainBuilder.class, FragmentBase.class})
public class ModuleContext {

    private FragmentActivity mContext;

    private ObjectGraph mGraph;

    public ModuleContext(ObjectGraph graph, FragmentActivity context) {
        mContext = context;
        mGraph = graph;
    }

    @Provides FragmentActivity providesContent() {
        return mContext;
    }

    @Provides ITimeoutModel providesModel() {
        return mGraph.get(TimeoutModelImpl.class);
    }

    @Provides LoaderMainBuilder providesLoaderMainBuilder(ITimeoutModel model) {
        return new LoaderMainBuilder(mContext, model);
    }
}