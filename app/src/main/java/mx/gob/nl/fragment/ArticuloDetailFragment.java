package mx.gob.nl.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.squareup.picasso.Picasso;

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
    private ViewFlipper viewFlipper;
    private float lastX;


    Button btnProducto;
    public static final String ARG_ITEM_ID = "item_id";
    /**
     * The dummy content this fragment is presenting.
     */
    private ModelList mItem = new ModelList(-1,"","","");

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ArticuloDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        lastX=0;
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem.setId(Integer.valueOf(getArguments().getString(ARG_ITEM_ID)));
        }


    }

    // Method to handle touch event like left to right swap and right to left swap
    public boolean onlTouchEvent(MotionEvent touchevent)
    {
        lastX = lastX + 1;

        switch (touchevent.getAction())
        {
            // when user first touches the screen to swap
            //case MotionEvent.ACTION_DOWN:
            //{
            //    lastX = touchevent.getX();
            //    break;
            //}



            case MotionEvent.ACTION_DOWN:
            {
                float currentX = touchevent.getX();

                // if left to right swipe on screen
                //if (lastX < 4)
                //{
                    // If no more View/Child to flip
                    if (viewFlipper.getDisplayedChild() == 0)
                        break;

                    // set the required Animation type to ViewFlipper
                    // The Next screen will come in form Left and current Screen will go OUT from Right
                    viewFlipper.setInAnimation(getActivity(), R.anim.in_from_left);
                    viewFlipper.setOutAnimation(getActivity(), R.anim.out_to_right);
                    // Show the next Screen
                    viewFlipper.showNext();
                //}

                // if right to left swipe on screen
                //if (lastX > currentX)
                //{
                //    if (viewFlipper.getDisplayedChild() == 1)
                //        break;
                    // set the required Animation type to ViewFlipper
                    // The Next screen will come in form Right and current Screen will go OUT from Left
                //    viewFlipper.setInAnimation(getActivity(), R.anim.in_from_right);
                //    viewFlipper.setOutAnimation(getActivity(), R.anim.out_to_left);
                    // Show The Previous Screen
                //    viewFlipper.showPrevious();
                //}
                break;
            }
        }
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_articulo_detail, container, false);

        String[] objsURL = new String[3];

        objsURL[0] = "http://3.bp.blogspot.com/-EWmD65wo81U/T7QquWn-AtI/AAAAAAAAAds/7PUlz-GyaSw/s1600/android-logo.png";
        objsURL[1] = "http://guiaosc.org/wp-content/uploads/2013/08/3570-android.jpg";
        objsURL[2] = "http://www.mejoresaplicacionesandroid.org/wp-content/uploads/2012/09/android-skin-pack-01-535x535.png";
        ImageView objView1 = (ImageView)rootView.findViewById(R.id.imageView1);
        Picasso.with(getActivity()).load(objsURL[0]).into(objView1);
        ImageView objView2 = (ImageView)rootView.findViewById(R.id.imageView2);
        Picasso.with(getActivity()).load(objsURL[1]).into(objView2);
        ImageView objView3 = (ImageView)rootView.findViewById(R.id.imageView3);
        Picasso.with(getActivity()).load(objsURL[2]).into(objView3);
        viewFlipper = (ViewFlipper) rootView.findViewById(R.id.viewFlipper);

        rootView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return onlTouchEvent(event);
            }
        });

        //here the rest of your code
        // Show the dummy content as text in a TextView.
        //if (mItem != null) {
        //    ((TextView) rootView.findViewById(R.id.articulo_detail)).setText(mItem.content);
        //}

        return rootView;
    }
}
