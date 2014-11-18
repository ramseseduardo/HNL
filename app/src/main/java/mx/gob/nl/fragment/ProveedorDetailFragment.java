package mx.gob.nl.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import mx.gob.nl.fragment.model.DBhelper;
import mx.gob.nl.fragment.model.FactoryTable;
import mx.gob.nl.fragment.model.ISQLControlador;
import mx.gob.nl.fragment.model.ModelList;

/**
 * A fragment representing a single Proveedor detail screen.
 * This fragment is either contained in a {@link ProveedorListActivity}
 * in two-pane mode (on tablets) or a {@link ProveedorDetailActivity}
 * on handsets.
 */
public class ProveedorDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private ModelList mItem = new ModelList(-1,"","","");

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ProveedorDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem.setId(Integer.valueOf(getArguments().getString(ARG_ITEM_ID)));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_proveedor_detail, container, false);


        loadData(rootView);
        rootView.findViewById(R.id.btnproducto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent(getActivity(), ArticuloListActivity.class);
                detailIntent.putExtra(ProveedorDetailFragment.ARG_ITEM_ID,  String.valueOf(mItem.getId()));
                startActivity(detailIntent);
            }
        });

        return rootView;
    }

    private void loadData(View rootView) {
        ISQLControlador objTable;

        // Show the dummy content as text in a TextView.
        if (mItem.getId() != -1) {
            objTable = FactoryTable.getSQLController(FactoryTable.TABLA.PROVEEDORES);

            objTable.abrirBaseDeDatos(getActivity());

            Cursor objCursor = objTable.leer(DBhelper.PROVEEDOR_ID_PROVEEDOR + " = ?" , new String[] {String.valueOf(mItem.getId())});

            while (!objCursor.isAfterLast()) {
                ((TextView)rootView.findViewById(R.id.txtNombre)).setText(String.valueOf(objCursor.getString(1)));
                ((TextView)rootView.findViewById(R.id.txtDetalle)).setText(String.valueOf(objCursor.getString(3)));
                ImageView objView = (ImageView)rootView.findViewById(R.id.imageView);
                Picasso.with(getActivity()).load(objCursor.getString(19)).into(objView);
                objCursor.moveToNext();
            }

            objCursor.close();

            objTable.cerrar();

        }
    }
}


