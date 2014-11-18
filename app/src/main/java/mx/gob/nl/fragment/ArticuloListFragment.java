package mx.gob.nl.fragment;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mx.gob.nl.fragment.adapter.CustomAdapter;
import mx.gob.nl.fragment.model.DBhelper;
import mx.gob.nl.fragment.model.FactoryTable;
import mx.gob.nl.fragment.model.ISQLControlador;
import mx.gob.nl.fragment.model.ModelList;

/**
 * A list fragment representing a list of Articulos. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link ArticuloDetailFragment}.
 * <p>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class ArticuloListFragment extends ListFragment {

    private CustomAdapter mAdapter;
    private List<ModelList> mListCategories = new ArrayList<ModelList>();
    private String[] mUrls = {};
    private Random mRandom = new Random();
    public static final String ARG_ITEM_ID = "item_id";
    /**
     * The dummy content this fragment is presenting.
     */
    private ModelList mItem = new ModelList(-1,"","","");
    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks = sDummyCallbacks;

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
    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(String id) {
        }
    };

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ArticuloListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: replace with a real list adapter.

        if (getActivity().getIntent().getStringExtra(ArticuloListFragment.ARG_ITEM_ID) != null){
            mItem.setId(Integer.valueOf(getActivity().getIntent().getStringExtra(ArticuloListFragment.ARG_ITEM_ID)));
        }

        setListAdapter(init());
    }

    private CustomAdapter init() {

        ISQLControlador objTable;

        objTable = FactoryTable.getSQLController(FactoryTable.TABLA.PRODUCTOS);

        objTable.abrirBaseDeDatos(getActivity());

        Cursor objCursor = objTable.leer(DBhelper.PRODUCTO_ID_PROVEEDOR + " = ?" , new String[] {String.valueOf(mItem.getId())});


        while (!objCursor.isAfterLast()) {
            mListCategories.add(new ModelList(objCursor.getInt(0),objCursor.getString(2),objCursor.getString(3),objCursor.getString(7)));
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
        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

        // Notify the active callbacks interface (the activity, if the
        // fragment is attached to one) that an item has been selected.
        mCallbacks.onItemSelected(String.valueOf(mListCategories.get(position).getId()));
    }

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
}
