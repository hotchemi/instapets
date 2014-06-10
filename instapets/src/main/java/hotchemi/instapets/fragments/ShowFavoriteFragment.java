package hotchemi.instapets.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.devspark.progressfragment.ProgressFragment;

import java.util.List;

import hotchemi.instapets.R;
import hotchemi.instapets.adapter.ShowFavoriteAdapter;
import hotchemi.instapets.model.Favorite;

public class ShowFavoriteFragment extends ProgressFragment {

    private GridView mGridView;

    private View mContentView;

    public ShowFavoriteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_show_favorite, container, false);
        mGridView = (GridView) mContentView.findViewById(R.id.grid_view_show_photo);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setContentView(mContentView);
        List<Favorite> list = Favorite.getAllItem();
        if (list.isEmpty()) {
            setContentEmpty(true);
            setEmptyText(R.string.labels_favorite_empty);
        } else {
            ShowFavoriteAdapter mAdapter = new ShowFavoriteAdapter(getActivity(), list);
            mGridView.setAdapter(mAdapter);
        }
        setContentShown(true);
    }

}