package mx.gob.nl.fragment;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mx.gob.nl.fragment.adapter.CustomAdapter;
import mx.gob.nl.fragment.model.ModelList;


/**
 * An activity representing a list of Proveedores. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ProveedorDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link ProveedorListFragment} and the item details
 * (if present) is a {@link ProveedorDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link ProveedorListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class ProveedorListActivity extends Activity implements SearchView.OnQueryTextListener
        , ProveedorListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private static final String TAG = "SearchViewFilterMode";

    private SearchView mSearchView;
    private ListView mListView;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proveedor_list);

        if (findViewById(R.id.proveedor_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((ProveedorListFragment) getFragmentManager()
                    .findFragmentById(R.id.proveedor_list))
                    .setActivateOnItemClick(true);
        }


        // TODO: If exposing deep links into your app, handle intents here.
    }
    /**
     * Callback method from {@link ProveedorListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
   public void onItemSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(ProveedorDetailFragment.ARG_ITEM_ID, id);
            ProveedorDetailFragment fragment = new ProveedorDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .replace(R.id.proveedor_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, ProveedorDetailActivity.class);
            detailIntent.putExtra(ProveedorDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //Create the search view
        /*SearchView searchView = new SearchView(getSupportActionBar().getThemedContext());

        mListView = getListView();
        mListView.setTextFilterEnabled(true);

        setupSearchView(searchView);

        menu.add(0, 1, 1, null)
                .setIcon(R.drawable.ic_search)
                .setActionView(searchView)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);*/

        return super.onCreateOptionsMenu(menu);
    }

    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(false);
        //mSearchView.setQueryHint(getString(R.string.cheese_hunt_hint));
    }

    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            mListView.clearTextFilter();
        } else {
            mListView.setFilterText(newText.toString());
        }
        return true;
    }

    public boolean onQueryTextSubmit(String query) {
        return false;
    }
}
