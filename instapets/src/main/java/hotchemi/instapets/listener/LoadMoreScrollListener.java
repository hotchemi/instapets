package hotchemi.instapets.listener;

import android.widget.AbsListView;

abstract class LoadMoreScrollListener implements AbsListView.OnScrollListener {

    private AbsListView mAbsListView;

    public LoadMoreScrollListener(final AbsListView absListView) {
        mAbsListView = absListView;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (isEnd()) {
            onLoadMore();
        }
    }

    private boolean isEnd() {
        if (mAbsListView.getAdapter() == null || mAbsListView.getChildCount() == 0) {
            return false;
        }
        int totalItemCount = mAbsListView.getAdapter().getCount() - 1;
        int lastItemBottomPosition = mAbsListView.getChildAt(mAbsListView.getChildCount() - 1).getBottom();
        return mAbsListView.getLastVisiblePosition() == totalItemCount && lastItemBottomPosition <= mAbsListView.getHeight();
    }

    protected abstract void onLoadMore();

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

}