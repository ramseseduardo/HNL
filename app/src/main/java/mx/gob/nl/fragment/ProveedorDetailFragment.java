package mx.gob.nl.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    private ModelList mItem = new ModelList(-1,"","");

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

        // Show the dummy content as text in a TextView.
        if (mItem.getId() != -1) {
            ((TextView) rootView.findViewById(R.id.txtNombre)).setText(String.valueOf(mItem.getId()));
        }

        return rootView;
    }
}
