package hotchemi.instapets.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

public final class ApplicationUtils {

    /**
     * No instances.
     */
    private ApplicationUtils() {
    }

    /**
     * Check whether device api level is under HONEYCOMB or not.
     *
     * @return boolean
     */
    public static boolean isOverHoneyComb() {
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB;
    }

    /**
     * Get version name related with context.
     *
     * @param context context
     * @return versionName
     * @throws android.content.pm.PackageManager.NameNotFoundException
     */
    public static String getVersionName(final Context context) throws PackageManager.NameNotFoundException {
        final PackageManager packageManager = context.getPackageManager();
        return packageManager.getPackageInfo(context.getPackageName(), 1).versionName;
    }

}
