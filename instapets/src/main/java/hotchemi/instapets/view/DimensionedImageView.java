package hotchemi.instapets.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public final class DimensionedImageView extends ImageView {

    public DimensionedImageView(Context context) {
        super(context);
    }

    public DimensionedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }

}