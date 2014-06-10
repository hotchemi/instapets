package hotchemi.instapets.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import hotchemi.instapets.R;
import hotchemi.instapets.model.Favorite;
import hotchemi.instapets.utils.PicassoUtils;
import hotchemi.instapets.utils.ToastUtils;
import hotchemi.instapets.view.DimensionedImageView;

public class ShowFavoriteAdapter extends BindableAdapter<Favorite> {

    public ShowFavoriteAdapter(Context context, List<Favorite> list) {
        super(context, list);
    }

    @Override
    public View newView(LayoutInflater inflater, int position, ViewGroup parent) {
        View view = inflater.inflate(R.layout.show_favorite_grid_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(final Favorite data, final int position, View view) {
        final ViewHolder holder = (ViewHolder) view.getTag();
        final Context context = getContext();

        PicassoUtils.setImage(context, data.imageUrl, holder.imageView);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(data.link));
                context.startActivity(intent);
            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Delete");
                alertDialogBuilder.setMessage("Delete item from favotire?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Favorite.deleteFromItem(data);
                                ToastUtils.showToast(context, "Item deleted.");
                                remove(data);
                            }
                        }
                );
                alertDialogBuilder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }
                );
                alertDialogBuilder.setCancelable(true);
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                return false;
            }
        });
    }

    static class ViewHolder {
        @InjectView(R.id.favorite_image_view)
        DimensionedImageView imageView;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}