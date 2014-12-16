package mx.gob.nl.fragment;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.app.ListFragment;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mx.gob.nl.fragment.adapter.CustomAdapter;
import mx.gob.nl.fragment.model.FactoryTable;
import mx.gob.nl.fragment.model.ISQLControlador;
import mx.gob.nl.fragment.model.ModelList;

/**
 * A list fragment representing a list of Proveedores. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link ProveedorDetailFragment}.
 * <p>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class ProveedorListFragment extends ListFragment {  //implements LoaderManager.LoaderCallbacks<Cursor> {

    private CustomAdapter mAdapter;
    private List<ModelList> mListCategories = new ArrayList<ModelList>();
    private String[] mUrls = {};
    private Random mRandom = new Random();
    private String grid_currentQuery = null; // holds the current query...

    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks = sProveedorCallbacks;

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(String id);
    }

    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
     private static Callbacks sProveedorCallbacks = new Callbacks() {
    @Override
    public void onItemSelected(String id) {
    }
    };


     @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        //setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.searchview_in_menu, menu);
        SearchView searchView = (SearchView)menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(queryListener);
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ProveedorListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: replace with a real list adapter.
        setListAdapter(init());

    }

    private CustomAdapter init() {
        ISQLControlador objTable;

        objTable = FactoryTable.getSQLController(FactoryTable.TABLA.PROVEEDORES);

        objTable.abrirBaseDeDatos(getActivity());

        Cursor objCursor = objTable.leer(null,null);


        while (!objCursor.isAfterLast()) {
            mListCategories.add(new ModelList(objCursor.getInt(0),objCursor.getString(1),objCursor.getString(3),objCursor.getString(19)));
            objCursor.moveToNext();
        }
        // make sure to close the cursor
        objCursor.close();

        objTable.cerrar();

        mAdapter = new CustomAdapter(getActivity(), mListCategories);

        return mAdapter;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = sProveedorCallbacks;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

        // Notify the active callbacks interface (the activity, if the
        // fragment is attached to one) that an item has been selected.
        mCallbacks.onItemSelected(String.valueOf(mListCategories.get(position).getId()));
    }

    /*@Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bargs) {

        String sort = "SortColumn ASC";
        String[] grid_columns = new String[] { "ColumnA", "ColumnB", "Etc..." };
        String grid_whereClause = "ColumnToSearchBy LIKE ?";

        if (!TextUtils.isEmpty(grid_currentQuery)) {
            return null;//new CursorLoader(getActivity(), DataProvider.CONTENT_URI, grid_columns, grid_whereClause, new String[] { grid_currentQuery + "%" }, sort);
        }

        return null;// new CursorLoader(getActivity(), DataProvider.CONTENT_URI, grid_columns, null, null, sort);
    }*/

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }

    final private SearchView.OnQueryTextListener queryListener = new SearchView.OnQueryTextListener() {

        @Override
        public boolean onQueryTextChange(String newText) {
            if (TextUtils.isEmpty(newText)) {
                getActivity().getActionBar().setSubtitle("List");
                grid_currentQuery = null;
            } else {
                getActivity().getActionBar().setSubtitle("List - Searching for: " + newText);
                grid_currentQuery = newText;

            }
            //getLoaderManager().restartLoader(0, null, ProveedorListFragment.this);
            return false;
        }

        @Override
        public boolean onQueryTextSubmit(String query) {
            Toast.makeText(getActivity(), "Searching for: " + query + "...", Toast.LENGTH_SHORT).show();
            return false;
        }
    };
}
