package hotchemi.instapets.utils;

import android.content.Context;

import hotchemi.instapets.R;

public class UrlUtils {

    private UrlUtils() {
    }

    public static String getSearchUrl(final Context context, final String tag) {
        return context.getString(
                R.string.search_url, tag, context.getString(R.string.access_token));
    }

}