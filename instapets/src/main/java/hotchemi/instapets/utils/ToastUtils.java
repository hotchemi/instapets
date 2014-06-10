package hotchemi.instapets.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {

    private ToastUtils() {
    }

    public static void showToast(final Context context, final String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}