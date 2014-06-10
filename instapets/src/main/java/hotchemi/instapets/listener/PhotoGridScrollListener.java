package hotchemi.instapets.listener;

import android.widget.AbsListView;

import hotchemi.instapets.events.BusProvider;
import hotchemi.instapets.events.EndLoadMoreEvent;

public final class PhotoGridScrollListener extends LoadMoreScrollListener {

    private boolean mIsLoading;

    public PhotoGridScrollListener(AbsListView view) {
        super(view);
    }

    @Override
    public void onLoadMore() {
        if (mIsLoading) {
            return;
        }
        BusProvider.getInstance().post(new EndLoadMoreEvent());
        mIsLoading = true;
    }

    public void isEndLoading(boolean isLoading) {
        mIsLoading = isLoading;
    }

}
