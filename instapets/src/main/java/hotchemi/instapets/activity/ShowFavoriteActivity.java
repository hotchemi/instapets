package hotchemi.instapets.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import hotchemi.instapets.R;
import hotchemi.instapets.delegate.MenuDelegate;
import hotchemi.instapets.fragments.ShowFavoriteFragment;

public class ShowFavoriteActivity extends ActionBarActivity {

    private MenuDelegate mMenuDelegate;

    public static Intent createIntent(Context context) {
        return new Intent(context, ShowFavoriteActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_container);
        setUpActionBar();
        mMenuDelegate = new MenuDelegate(this);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new ShowFavoriteFragment()).commit();
    }

    private void setUpActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.title_activity_show_favorites);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.show_favorite, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                mMenuDelegate.pressSettings();
                return true;
            case android.R.id.home:
                mMenuDelegate.pressHome();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
