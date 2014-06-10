package hotchemi.instapets.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import butterknife.ButterKnife;
import butterknife.InjectView;
import hotchemi.instapets.R;
import hotchemi.instapets.entity.PhotoEntity;
import hotchemi.instapets.model.Favorite;
import hotchemi.instapets.utils.PicassoUtils;
import hotchemi.instapets.utils.ToastUtils;
import hotchemi.instapets.view.DimensionedImageView;

public class ShowPhotoAdapter extends BindableAdapter<PhotoEntity> {

    public ShowPhotoAdapter(Context context) {
        super(context);
    }

    @Override
    public View newView(LayoutInflater inflater, int position, ViewGroup parent) {
        View view = inflater.inflate(R.layout.show_photo_grid_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(final PhotoEntity data, int position, View view) {
        final ViewHolder holder = (ViewHolder) view.getTag();
        final Context context = getContext();

        PicassoUtils.setImage(context, data.imageUrl, holder.imageView);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View item) {
                Favorite favorite = new Favorite(data);
                favorite.save();
                ToastUtils.showToast(context, "Add to favorite.");
                holder.button.setVisibility(View.GONE);
            }
        });

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
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, data.link);
                context.startActivity(intent);
                return false;
            }
        });
    }

    static class ViewHolder {
        @InjectView(R.id.photo_image_view)
        DimensionedImageView imageView;

        @InjectView(R.id.favorite_button)
        ImageButton button;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}