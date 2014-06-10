package hotchemi.instapets.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

public abstract class BindableAdapter<T> extends ArrayAdapter<T> {

    public BindableAdapter(Context context) {
        super(context, 0);
    }

    public BindableAdapter(Context context, List<T> list) {
        super(context, 0, list);
    }

    @Override
    public final View getView(int position, View view, ViewGroup container) {
        if (view == null) {
            LayoutInflater mInflater = LayoutInflater.from(getContext());
            view = newView(mInflater, position, container);
            if (view == null) {
                throw new IllegalStateException("newView result must not be null.");
            }
        }
        bindView(getItem(position), position, view);
        return view;
    }

    public abstract View newView(LayoutInflater inflater, int position, ViewGroup container);

    public abstract void bindView(T item, int position, View view);

}
