package hotchemi.instapets.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import hotchemi.instapets.R;
import hotchemi.instapets.delegate.MenuDelegate;
import hotchemi.instapets.fragments.ShowPhotoFragment;

public class ShowPhotoActivity extends ActionBarActivity
        implements ActionBar.OnNavigationListener {

    private static final String SELECTED_NAVIGATION_ITEM = "selected.navigation.item";

    private ActionBar mActionBar;

    private MenuDelegate mMenuDelegate;

    private Menu mOptionsMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_container);
        setUpActionBar();
        mMenuDelegate = new MenuDelegate(this);
    }

    private void setUpActionBar() {
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        mActionBar.setListNavigationCallbacks(getActionBarAdapter(), this);
    }

    private ArrayAdapter<String> getActionBarAdapter() {
        return new ArrayAdapter<String>(
                mActionBar.getThemedContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                new String[]{
                        getString(R.string.labels_actionbar_section_dog),
                        getString(R.string.labels_actionbar_section_cat)}
        );
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        mActionBar.setSelectedNavigationItem(savedInstanceState.getInt(SELECTED_NAVIGATION_ITEM));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_NAVIGATION_ITEM, mActionBar.getSelectedNavigationIndex());
    }

    @Override
    public boolean onNavigationItemSelected(int position, long id) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, ShowPhotoFragment.newInstance(position))
                .commit();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.show_photo, menu);
        mOptionsMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                mMenuDelegate.pressRefresh();
                return true;
            case R.id.action_favorites:
                mMenuDelegate.pressFavorites();
                return true;
            case R.id.action_settings:
                mMenuDelegate.pressSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setRefreshActionButtonState(boolean isRefreshing) {
        if (mOptionsMenu != null) {
            MenuItem refresh = mOptionsMenu.findItem(R.id.action_refresh);
            if (refresh != null) {
                if (isRefreshing) {
                    refresh.setActionView(R.layout.actionbar_progress);
                } else {
                    refresh.setActionView(null);
                }
            }
        }
    }

}