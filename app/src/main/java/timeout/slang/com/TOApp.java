package timeout.slang.com;

import android.app.Application;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;
import timeout.slang.com.di.ModuleModel;
import timeout.slang.com.di.ModuleUI;

/**
 * Android application class
 */
public class TOApp extends Application {

    /* ------------------------------------------------------------------------------------------
     * Static
     * ------------------------------------------------------------------------------------------ */

    /**
     * There is only a single instance of Application so it's safe to set this in onCreate
     */
    private static TOApp _singleton;

    /**
     * @return      Application instance
     */
    public static TOApp getInstance() {
        return _singleton;
    }

    /* ------------------------------------------------------------------------------------------
     * Private Members
     * ------------------------------------------------------------------------------------------ */

    /**
     * Dagger ObjectGraph
     */
    private ObjectGraph mGraph;

    /* ------------------------------------------------------------------------------------------
     * From Application
     * ------------------------------------------------------------------------------------------ */

    @Override public void onCreate() {
        super.onCreate();
        _singleton = this;
        mGraph = ObjectGraph.create(getModules().toArray());
    }

    /* ------------------------------------------------------------------------------------------
     * Public Methods
     * ------------------------------------------------------------------------------------------ */

    /**
     * @return      Dagger ObjectGraph
     */
    public ObjectGraph getGraph() {
        return mGraph;
    }

    /**
     * @param object    Use Dagger to inject Fields
     */
    public void inject(Object object) {
        mGraph.inject(object);
    }

    /**
     * @param modules   Pass modules in to tag onto existing object graph
     * @return          New scoped ObjectGraph
     */
    public ObjectGraph createScopedGraph(Object... modules) {
        return mGraph.plus(modules);
    }

    /* ------------------------------------------------------------------------------------------
     * Protected Methods
     * ------------------------------------------------------------------------------------------ */

    /**
     * @return      ObjectGraph created with Dagger Modules
     */
    protected List<Object> getModules() {
        return Arrays.asList(
                (Object) new ModuleModel(this),
                (Object) new ModuleUI()
        );
    }


}
