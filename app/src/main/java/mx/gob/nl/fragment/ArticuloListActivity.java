package mx.gob.nl.fragment;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Activity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import mx.gob.nl.fragment.model.DBhelper;
import mx.gob.nl.fragment.model.FactoryTable;
import mx.gob.nl.fragment.model.ISQLControlador;
import mx.gob.nl.fragment.model.ModelList;


/**
 * An activity representing a list of Articulos. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ArticuloDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link ArticuloListFragment} and the item details
 * (if present) is a {@link ArticuloDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link ArticuloListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class ArticuloListActivity extends Activity
        implements ArticuloListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */

    public static final String ARG_ARTICULO_ID = "articulo_id";
    private boolean mTwoPane;
    private ModelList mItem = new ModelList(-1,"","","");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articulo_list);

        if(savedInstanceState != null && savedInstanceState.containsKey(ArticuloListFragment.ARG_ITEM_ID)) {
            mItem.setId(Integer.valueOf((String)savedInstanceState.getCharSequence(ArticuloListFragment.ARG_ITEM_ID)));
        }

        if (getIntent().getStringExtra(ArticuloListFragment.ARG_ITEM_ID) != null) {
            mItem.setId(Integer.valueOf(getIntent().getStringExtra(ArticuloListFragment.ARG_ITEM_ID)));
        }

        if (findViewById(R.id.articulo_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            // Create the detail fragment and add it to the activity
            ((ArticuloListFragment) getFragmentManager()
                    .findFragmentById(R.id.articulo_list))
                    .setActivateOnItemClick(true);
        }

        //loadData();

        // TODO: If exposing deep links into your app, handle intents here.
    }

    private void loadData() {
        ISQLControlador objTable;

        // Show the dummy content as text in a TextView.
        if (mItem.getId() != -1) {
            objTable = FactoryTable.getSQLController(FactoryTable.TABLA.PRODUCTOS);

            objTable.abrirBaseDeDatos(this);

            Cursor objCursor = objTable.leer(DBhelper.PRODUCTO_ID_PROVEEDOR + " = ?" , new String[] {String.valueOf(mItem.getId())});

            while (!objCursor.isAfterLast()) {
                ((TextView)findViewById(R.id.txtNombre)).setText(String.valueOf(objCursor.getString(2)));
                //((TextView)findViewById(R.id.txtDetalle)).setText(String.valueOf(objCursor.getString(3)));
                ImageView objView = (ImageView)findViewById(R.id.imageView);
                Picasso.with(this).load(objCursor.getString(19)).into(objView);
                objCursor.moveToNext();
            }

            objCursor.close();

            objTable.cerrar();

        }
    }
    /**
     * Callback method from {@link ArticuloListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(ArticuloListActivity.ARG_ARTICULO_ID, id);
            arguments.putString(ArticuloDetailFragment.ARG_ITEM_ID, String.valueOf(mItem.getId()));
            ArticuloDetailFragment fragment = new ArticuloDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .replace(R.id.articulo_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, ArticuloDetailActivity.class);
            detailIntent.putExtra(ArticuloListActivity.ARG_ARTICULO_ID, id);
            detailIntent.putExtra(ArticuloDetailFragment.ARG_ITEM_ID, String.valueOf(mItem.getId()));
            startActivity(detailIntent);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            Intent detailIntent = new Intent(this, ProveedorDetailActivity.class);
            detailIntent.putExtra(ArticuloListFragment.ARG_ITEM_ID,  String.valueOf(mItem.getId()));

            navigateUpTo(detailIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
