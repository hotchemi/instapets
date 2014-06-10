package hotchemi.instapets.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.devspark.progressfragment.ProgressFragment;
import com.loopj.android.http.AsyncHttpClient;
import com.squareup.otto.Subscribe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import hotchemi.instapets.R;
import hotchemi.instapets.activity.ShowPhotoActivity;
import hotchemi.instapets.adapter.ShowPhotoAdapter;
import hotchemi.instapets.entity.PhotoEntity;
import hotchemi.instapets.events.BusProvider;
import hotchemi.instapets.events.EndLoadMoreEvent;
import hotchemi.instapets.events.PressRefreshEvent;
import hotchemi.instapets.events.SearchFailureEvent;
import hotchemi.instapets.events.SearchSuccessEvent;
import hotchemi.instapets.handler.AsyncSearchResponseHandler;
import hotchemi.instapets.listener.PhotoGridScrollListener;
import hotchemi.instapets.utils.UrlUtils;

public class ShowPhotoFragment extends ProgressFragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = ShowPhotoFragment.class.getCanonicalName();

    private static final String PET_KEY = "pet.key";

    private static final int TIMEOUT = 5 * 1000;

    private static final SparseArray<String> PET_LIST = new SparseArray<String>();

    static {
        PET_LIST.append(0, "犬");
        PET_LIST.append(1, "猫");
    }

    private String mNextUrl;

    private GridView mGridView;

    private ShowPhotoAdapter mAdapter;

    private Activity mActivity;

    private View mContentView;

    private SwipeRefreshLayout mSwipeRefresh;

    private PhotoGridScrollListener photoGridScrollListener;

    private ShowPhotoFragment() {
    }

    public static ShowPhotoFragment newInstance(int section) {
        ShowPhotoFragment fragment = new ShowPhotoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(PET_KEY, section);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        mActivity = activity;
        super.onAttach(activity);
    }

    @Override
    public void onDestroyView() {
        BusProvider.getInstance().unregister(this);
        super.onDestroyView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        BusProvider.getInstance().register(this);
        mContentView = inflater.inflate(R.layout.fragment_show_photo, container, false);
        setUpSwipeRefresh();
        setUpGridView();
        search(UrlUtils.getSearchUrl(mActivity, PET_LIST.get(getPetKey())));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setContentView(mContentView);
        setContentShown(false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new ShowPhotoAdapter(mActivity);
        mGridView.setAdapter(mAdapter);
    }

    @Override
    public void onRefresh() {
        setContentShown(false);
        search(UrlUtils.getSearchUrl(mActivity, PET_LIST.get(getPetKey())));
        mAdapter.clear();
    }

    @Subscribe
    public void onPressRefreshMenuItem(PressRefreshEvent event) {
        onRefresh();
    }

    @Subscribe
    public void onEndLoadMore(EndLoadMoreEvent event) {
        search(mNextUrl);
    }

    @Subscribe
    public void onSuccessSearch(SearchSuccessEvent event) {
        try {
            JSONObject json = new JSONObject(event.getContent());
            mNextUrl = json.getJSONObject("pagination").getString("next_url");
            JSONArray dataArray = json.getJSONArray("data");
            List<PhotoEntity> list = new ArrayList<PhotoEntity>();
            int resultNum = dataArray.length();
            for (int i = 0; i < resultNum; i++) {
                JSONObject dataObj = dataArray.getJSONObject(i);
                String link = dataObj.getString("link");
                String imgUrl = dataObj
                        .getJSONObject("images")
                        .getJSONObject("thumbnail")
                        .getString("url");
                list.add(new PhotoEntity(imgUrl, link));
            }
            mAdapter.addAll(list);
        } catch (final JSONException e) {
            Log.d(TAG, e.toString());
        }
        setContentShown(true);
        ((ShowPhotoActivity) mActivity).setRefreshActionButtonState(false);
        mSwipeRefresh.setRefreshing(false);
        photoGridScrollListener.isEndLoading(false);
    }

    @Subscribe
    public void onSuccessFailure(SearchFailureEvent event) {
        setContentEmpty(true);
        setEmptyText(R.string.labels_grid_empty);
        ((ShowPhotoActivity) mActivity).setRefreshActionButtonState(false);
        setContentShown(true);
    }

    private int getPetKey() {
        return getArguments().getInt(PET_KEY);
    }

    private void setUpSwipeRefresh() {
        mSwipeRefresh = (SwipeRefreshLayout) mContentView.findViewById(R.id.swipe_refresh);
        mSwipeRefresh.setColorScheme(R.color.color1, R.color.color2, R.color.color3, R.color.color4);
        mSwipeRefresh.setOnRefreshListener(this);
    }

    private void setUpGridView() {
        mGridView = (GridView) mContentView.findViewById(R.id.grid_view_show_photo);
        photoGridScrollListener = new PhotoGridScrollListener(mGridView);
        mGridView.setOnScrollListener(photoGridScrollListener);
    }

    private void search(final String url) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(TIMEOUT);
        client.get(url, new AsyncSearchResponseHandler());
        ((ShowPhotoActivity) mActivity).setRefreshActionButtonState(true);
    }

}