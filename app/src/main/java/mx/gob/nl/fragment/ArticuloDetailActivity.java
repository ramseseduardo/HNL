package mx.gob.nl.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Activity;

import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.squareup.picasso.Picasso;

import mx.gob.nl.fragment.model.DBhelper;
import mx.gob.nl.fragment.model.FactoryTable;
import mx.gob.nl.fragment.model.ISQLControlador;
import mx.gob.nl.fragment.model.ModelList;


/**
 * An activity representing a single Articulo detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ArticuloListActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link ArticuloDetailFragment}.
 */
public class ArticuloDetailActivity extends Activity {

    private ViewFlipper viewFlipper;
    private float lastX;
    public static final String ARG_ITEM_ID = "item_id";
    public static final String ARG_ARTICULO_ID = "articulo_id";
    private ModelList mItem = new ModelList(-1,"","","");
    private int ArticuloId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articulo_detail);

        // Show the Up button in the action bar.
        getActionBar().setDisplayHomeAsUpEnabled(true);

        if(savedInstanceState != null && savedInstanceState.containsKey(ArticuloDetailActivity.ARG_ITEM_ID)) {
            mItem.setId(Integer.valueOf((String)savedInstanceState.getCharSequence(ArticuloDetailActivity.ARG_ITEM_ID)));
            ArticuloId = Integer.valueOf((String)savedInstanceState.getCharSequence(ArticuloDetailActivity.ARG_ARTICULO_ID));
        }


        if (getIntent().getStringExtra(ArticuloDetailActivity.ARG_ITEM_ID) != null) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem.setId(Integer.valueOf(getIntent().getStringExtra(ArticuloDetailActivity.ARG_ITEM_ID)));
            ArticuloId = Integer.valueOf(getIntent().getStringExtra(ArticuloDetailActivity.ARG_ARTICULO_ID));
        }

        loadData();



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
        //    // Create the detail fragment and add it to the activity
        //    // using a fragment transaction.
        //    Bundle arguments = new Bundle();
        //    arguments.putString(ArticuloDetailFragment.ARG_ITEM_ID,
        //            getIntent().getStringExtra(ArticuloDetailFragment.ARG_ITEM_ID));
        //    ArticuloDetailFragment fragment = new ArticuloDetailFragment();
        //    fragment.setArguments(arguments);
        //    getFragmentManager().beginTransaction()
        //            .add(R.id.articulo_detail_container, fragment)
        //            .commit();
        //}
    }

    private void loadData() {
        ISQLControlador objTable;

        // Show the dummy content as text in a TextView.
        if (mItem.getId() != -1) {
            objTable = FactoryTable.getSQLController(FactoryTable.TABLA.PRODUCTOS);

            objTable.abrirBaseDeDatos(this);

            Cursor objCursor = objTable.leer(DBhelper.PRODUCTO_ID_PROVEEDOR + " = ? AND " + DBhelper.PRODUCTO_ID_PRODUCTO + " = ?" , new String[] {String.valueOf(mItem.getId()),String.valueOf(ArticuloId)});

            while (!objCursor.isAfterLast()) {
                ImageView objView1 = (ImageView)findViewById(R.id.imageView1);
                Picasso.with(this).load(String.valueOf(objCursor.getString(7))).into(objView1);
                ImageView objView2 = (ImageView)findViewById(R.id.imageView2);
                Picasso.with(this).load(String.valueOf(objCursor.getString(8))).into(objView2);
                ImageView objView3 = (ImageView)findViewById(R.id.imageView3);
                Picasso.with(this).load(String.valueOf(objCursor.getString(9))).into(objView3);
                objCursor.moveToNext();
            }

            objCursor.close();

            objTable.cerrar();

        }

        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getIntent().putExtra(ArticuloDetailActivity.ARG_ITEM_ID,  String.valueOf(mItem.getId()));
        outState.putCharSequence(ArticuloDetailActivity.ARG_ITEM_ID, String.valueOf(mItem.getId()));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
    // Method to handle touch event like left to right swap and right to left swap
    public boolean onTouchEvent(MotionEvent touchevent)
    {
        switch (touchevent.getAction())
        {
            // when user first touches the screen to swap
            case MotionEvent.ACTION_DOWN:
            {
                lastX = touchevent.getX();
                break;
            }
            case MotionEvent.ACTION_UP:
            {
                float currentX = touchevent.getX();

                // if left to right swipe on screen
                if (lastX < currentX)
                {
                    // If no more View/Child to flip
                    if (viewFlipper.getDisplayedChild() == 0)
                        break;

                    // set the required Animation type to ViewFlipper
                    // The Next screen will come in form Left and current Screen will go OUT from Right
                    viewFlipper.setInAnimation(this, R.anim.in_from_left);
                    viewFlipper.setOutAnimation(this, R.anim.out_to_right);
                    // Show the next Screen
                    viewFlipper.showNext();
                }

                // if right to left swipe on screen
                if (lastX > currentX)
                {
                    if (viewFlipper.getDisplayedChild() == 1)
                        break;
                    // set the required Animation type to ViewFlipper
                    // The Next screen will come in form Right and current Screen will go OUT from Left
                    viewFlipper.setInAnimation(this, R.anim.in_from_right);
                    viewFlipper.setOutAnimation(this, R.anim.out_to_left);
                    // Show The Previous Screen
                    viewFlipper.showPrevious();
                }
                break;
            }
        }
        return false;
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

            Intent detailIntent = new Intent(this, ArticuloListActivity.class);
            detailIntent.putExtra(ArticuloListFragment.ARG_ITEM_ID,  String.valueOf(mItem.getId()));

            navigateUpTo(detailIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
