package hotchemi.instapets.activity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import hotchemi.instapets.R;
import hotchemi.instapets.delegate.MenuDelegate;
import hotchemi.instapets.utils.ApplicationUtils;

public class SettingsActivity extends PreferenceActivity implements Preference.OnPreferenceClickListener {

    private static final String TAG = SettingsActivity.class.getSimpleName();

    private MenuDelegate mMenuDelegate;

    private String mRate;

    private String mRecentChanges;

    private String mLicence;

    private String mVersion;

    public static Intent createIntent(Context context) {
        return new Intent(context, SettingsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        if (ApplicationUtils.isOverHoneyComb()) {
            setUpActionBar();
        }
        setUpLabel();
        setUpPreferenceScreen();
        mMenuDelegate = new MenuDelegate(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        final String key = preference.getKey();
        if (mRate.equals(key)) {
            return true;
        } else if (mVersion.equals(key)) {
            return true;
        } else if (mRecentChanges.equals(key)) {
            return true;
        } else if (mLicence.equals(key)) {
            startActivity(LicenceActivity.createIntent(this));
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mMenuDelegate.pressHome();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setUpActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setTitle(R.string.title_activity_settings);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    private void setUpLabel() {
        mRate = getString(R.string.label_setting_rate);
        mRecentChanges = getString(R.string.label_setting_rate);
        mLicence = getString(R.string.label_setting_licence);
        mVersion = getString(R.string.label_setting_version);
    }

    private void setUpPreferenceScreen() {
        PreferenceScreen rateScreen = (PreferenceScreen) findPreference(mRate);
        PreferenceScreen recentChangesScreen = (PreferenceScreen) findPreference(mRecentChanges);
        PreferenceScreen licenceScreen = (PreferenceScreen) findPreference(mLicence);
        PreferenceScreen versionScreen = (PreferenceScreen) findPreference(mVersion);
        rateScreen.setOnPreferenceClickListener(this);
        recentChangesScreen.setOnPreferenceClickListener(this);
        licenceScreen.setOnPreferenceClickListener(this);
        versionScreen.setOnPreferenceClickListener(this);
        try {
            versionScreen.setSummary(ApplicationUtils.getVersionName(this));
        } catch (PackageManager.NameNotFoundException e) {
            Log.d(TAG, e.toString());
        }
    }

}