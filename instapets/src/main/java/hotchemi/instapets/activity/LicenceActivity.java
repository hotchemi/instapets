package hotchemi.instapets.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import butterknife.ButterKnife;
import hotchemi.instapets.R;
import hotchemi.instapets.delegate.MenuDelegate;

public class LicenceActivity extends ActionBarActivity {

    private static final String FILE_PATH = "licence.txt";

    private static final String LF = "\n";

    private MenuDelegate mMenuDelegate;

    public static Intent createIntent(Context context) {
        return new Intent(context, LicenceActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_licence);
        setUpActionBar();
        setUpLicenceTextView();
        mMenuDelegate = new MenuDelegate(this);
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
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.title_licence_activity);
    }

    private void setUpLicenceTextView() {
        TextView licenceTextView = ButterKnife.findById(this, R.id.licence_text_view);
        licenceTextView.setText(getLicence());
    }

    private String getLicence() {
        final AssetManager assets = getResources().getAssets();
        final StringBuilder text = new StringBuilder();
        BufferedReader br;
        try {
            final InputStream in = assets.open(FILE_PATH);
            br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line).append(LF);
            }
            br.close();
        } catch (final IOException e) {
            Log.w(LicenceActivity.class.getCanonicalName(), e);
        }
        return text.toString();
    }

}