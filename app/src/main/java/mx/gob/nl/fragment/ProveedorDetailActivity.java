package mx.gob.nl.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
                ProveedorDetailActivity.this.startActivity(new Intent(ProveedorDetailActivity.this, ArticuloListActivity.class));
            }
        });
    }

    private void loadData() {

        // Show the dummy content as text in a TextView.
        if (mItem.getId() != -1) {
            ((TextView)findViewById(R.id.txtNombre)).setText(String.valueOf(mItem.getId()));
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
