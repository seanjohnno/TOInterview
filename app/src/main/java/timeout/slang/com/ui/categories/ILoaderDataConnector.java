package timeout.slang.com.ui.categories;

import timeout.slang.com.model.ITimeoutModel;

public interface ILoaderDataConnector {
    public void passResponse(ITimeoutModel.ITimeoutModelObserver observer);
}
