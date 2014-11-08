package mx.gob.nl.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mx.gob.nl.fragment.adapter.CustomAdapter;
import mx.gob.nl.fragment.model.ModelList;

/**
 * A fragment representing a single Articulo detail screen.
 * This fragment is either contained in a {@link ArticuloListActivity}
 * in two-pane mode (on tablets) or a {@link ArticuloDetailActivity}
 * on handsets.
 */
public class ArticuloDetailFragment extends Fragment {

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ArticuloDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            //mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_articulo_detail, container, false);

        // Show the dummy content as text in a TextView.
        //if (mItem != null) {
        //    ((TextView) rootView.findViewById(R.id.articulo_detail)).setText(mItem.content);
        //}

        return rootView;
    }
}
