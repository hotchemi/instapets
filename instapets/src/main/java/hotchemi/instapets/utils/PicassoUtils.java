package hotchemi.instapets.utils;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import hotchemi.instapets.R;

public final class PicassoUtils {

    private PicassoUtils() {
    }

    public static void setImage(final Context context, final String url, final ImageView imageView) {
        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .fit()
                .into(imageView);
    }

}
