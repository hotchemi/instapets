package hotchemi.instapets.delegate;

import android.app.Activity;

import hotchemi.instapets.activity.SettingsActivity;
import hotchemi.instapets.activity.ShowFavoriteActivity;
import hotchemi.instapets.events.BusProvider;
import hotchemi.instapets.events.PressRefreshEvent;

public final class MenuDelegate {

    private Activity activity;

    public MenuDelegate(Activity activity) {
        this.activity = activity;
    }

    public void pressHome() {
        activity.finish();
    }

    public void pressRefresh() {
        BusProvider.getInstance().post(new PressRefreshEvent());
    }

    public void pressFavorites() {
        activity.startActivity(ShowFavoriteActivity.createIntent(activity));
    }

    public void pressSettings() {
        activity.startActivity(SettingsActivity.createIntent(activity));
    }

}