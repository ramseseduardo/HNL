package mx.gob.nl.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Activity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import mx.gob.nl.fragment.model.DBhelper;
import mx.gob.nl.fragment.model.FactoryTable;
import mx.gob.nl.fragment.model.ISQLControlador;
import mx.gob.nl.fragment.model.ModelList;

import static android.view.View.OnClickListener;


/**
 * An activity representing a single Proveedor detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ProveedorListActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link ProveedorDetailFragment}.
 */
public class ProveedorDetailActivity extends Activity {

    Button btnProducto;
    public static final String ARG_ITEM_ID = "item_id";
    /**
     * The dummy content this fragment is presenting.
     */
    private ModelList mItem = new ModelList(-1,"","","");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proveedor_detail);
        btnProducto = (Button) findViewById(R.id.btnproducto);

        // Show the Up button in the action bar.
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        //if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            //Bundle arguments = new Bundle();
            //arguments.putString(ProveedorDetailFragment.ARG_ITEM_ID,
            //        getIntent().getStringExtra(ProveedorDetailFragment.ARG_ITEM_ID));
            //ProveedorDetailFragment fragment = new ProveedorDetailFragment();
            //fragment.setArguments(arguments);
            //getFragmentManager().beginTransaction()
            //        .add(R.id.proveedor_detail_container, fragment)
            //        .commit();
        //}

        if(savedInstanceState != null && savedInstanceState.containsKey(ProveedorDetailFragment.ARG_ITEM_ID)) {
            mItem.setId(Integer.valueOf((String)savedInstanceState.getCharSequence(ProveedorDetailFragment.ARG_ITEM_ID)));
        }


        if (getIntent().getStringExtra(ProveedorDetailFragment.ARG_ITEM_ID) != null) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem.setId(Integer.valueOf(getIntent().getStringExtra(ProveedorDetailFragment.ARG_ITEM_ID)));
        }

        loadData();
        findViewById(R.id.btnproducto).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent(ProveedorDetailActivity.this, ArticuloListActivity.class);
                detailIntent.putExtra(ProveedorDetailFragment.ARG_ITEM_ID,  String.valueOf(mItem.getId()));
                startActivity(detailIntent);
            }
        });
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getIntent().putExtra(ProveedorDetailFragment.ARG_ITEM_ID,  String.valueOf(mItem.getId()));
        outState.putCharSequence(ProveedorDetailFragment.ARG_ITEM_ID, String.valueOf(mItem.getId()));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void loadData() {
        ISQLControlador objTable;
        String sValores = "";
        String sDescripcion = "";

        // Show the dummy content as text in a TextView.
        if (mItem.getId() != -1) {
            objTable = FactoryTable.getSQLController(FactoryTable.TABLA.PROVEEDORES);

            objTable.abrirBaseDeDatos(this);

            Cursor objCursor = objTable.leer(DBhelper.PROVEEDOR_ID_PROVEEDOR + " = ?" , new String[] {String.valueOf(mItem.getId())});

            while (!objCursor.isAfterLast()) {
                sDescripcion = String.valueOf(objCursor.getString(1)) + "\n";
                sDescripcion += "\n";
                sDescripcion +=  "\t" + String.valueOf(objCursor.getString(2));

                ((TextView)findViewById(R.id.txtNombre)).setText(sDescripcion);

                if(!String.valueOf(objCursor.getString(7)).isEmpty() && !String.valueOf(objCursor.getString(8)).isEmpty() )
                    sValores = "Teléfono: " + String.valueOf(objCursor.getString(7)) + " - " + String.valueOf(objCursor.getString(8)) + "\n";
                else
                   {
                      if (!String.valueOf(objCursor.getString(7)).isEmpty())
                          sValores = "Teléfono: " + String.valueOf(objCursor.getString(7)) + "\n";
                      if (!String.valueOf(objCursor.getString(8)).isEmpty())
                          sValores = "Teléfono: " + String.valueOf(objCursor.getString(8)) + "\n";
                   }

                if(!String.valueOf(objCursor.getString(9)).isEmpty())
                    sValores += "Twitter: " + String.valueOf(objCursor.getString(9)) + "\n";
                if(!String.valueOf(objCursor.getString(10)).isEmpty())
                    sValores += "Facebook: " + String.valueOf(objCursor.getString(10)) + "\n";
                if(!String.valueOf(objCursor.getString(11)).isEmpty())
                    sValores += "Sitio Web: " + String.valueOf(objCursor.getString(11)) + "\n";
                if(!String.valueOf(objCursor.getString(12)).isEmpty());
                    sValores += "Mail: " + String.valueOf(objCursor.getString(12)) + "\n";
                if(!String.valueOf(objCursor.getString(13)).isEmpty())
                    sValores += "Calle: " + String.valueOf(objCursor.getString(13)) + "\n";
                if(!String.valueOf(objCursor.getString(14)).isEmpty())
                    sValores += "Número Exterior: " + String.valueOf(objCursor.getString(14)) + "\n";
                if(!String.valueOf(objCursor.getString(15)).isEmpty())
                    sValores += "Número Interior: " + String.valueOf(objCursor.getString(15)) + "\n";
                if(!String.valueOf(objCursor.getString(16)).isEmpty())
                    sValores += "Colionia: " + String.valueOf(objCursor.getString(16)) + "\n";
                if(!String.valueOf(objCursor.getString(17)).isEmpty())
                    sValores += "Municipio: " + String.valueOf(objCursor.getString(17));
                ((TextView)findViewById(R.id.txtDetalle)).setText(sValores);
                ImageView objView = (ImageView)findViewById(R.id.imageView);
                if(!objCursor.getString(20).isEmpty())
                    Picasso.with(this).load(objCursor.getString(20)).into(objView);
                else
                    Picasso.with(this).load(R.string.URLSinFoto).into(objView);
                objCursor.moveToNext();
            }

            objCursor.close();

            objTable.cerrar();

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
            navigateUpTo(new Intent(this, ProveedorListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
