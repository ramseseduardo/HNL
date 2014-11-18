package mx.gob.nl.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.Random;

import mx.gob.nl.fragment.model.DBhelper;
import mx.gob.nl.fragment.model.FactoryTable;
import mx.gob.nl.fragment.model.ISQLControlador;


public class CargaInformacion extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga_informacion);

        TextView objText = (TextView)findViewById(R.id.txtActualizando);
        Animation alphaAnimation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.alpha_animation);
        objText.setAnimation(alphaAnimation);
        Runnable runnable = new Runnable() {
            public void run() {
                CargaBaseDatos();
                Message msg = handler.obtainMessage();
                Bundle bundle = new Bundle();
                String dateString = "1";
                bundle.putString("myKey", dateString);
                msg.setData(bundle);


                handler.sendMessage(msg);
            }
        };
        Thread mythread = new Thread(runnable);
        mythread.start();


    }

    private void CargaBaseDatos() {
        ISQLControlador objTable;
        String sValor1, sValor2 ,sValor3;
        String[] mUrls  = getResources().getStringArray(R.array.urls);
        Random mRandom = new Random();


        objTable = FactoryTable.getSQLController(FactoryTable.TABLA.PROVEEDORES);

        objTable.abrirBaseDeDatos(this);

        if(objTable.count() > 0)
        {
            objTable.cerrar();
            long endTime = System.currentTimeMillis() + 3*1000;

                while (System.currentTimeMillis() < endTime) {
                    synchronized (this) {
                        try {
                            wait(endTime -
                                    System.currentTimeMillis());
                        } catch (Exception e) {}
                    }
             }
            return;
        }

        //Inserta Proveedor
        for(int i=0;i<300;i++) {
            sValor1 = "Proveedor Nombre " + String.valueOf(i);
            sValor2 = "Proveedor de Servicios Generales" + String.valueOf(i);
            sValor3 = "Servicios Generales " + String.valueOf(i);

            objTable.insertar(new Object[]{
                    i,sValor1,sValor2,sValor3,"Servicios1","Servicios2"
                    ,"Servicios3","Tel1","Tel2","https://twitter.com/androidmx",
                    "https://es-la.facebook.com/AplicacionesAndroid",
                    "http://www.android.com","ramses_eduardo@icloud.com"
                    ,1,1,"Colonia","Municipio","Estado","Pais"
                    ,mUrls[mRandom.nextInt(mUrls.length - 1)]
                    ,"","2014-10-10","1","1"
            });
        }
        objTable.cerrar();

        objTable = FactoryTable.getSQLController(FactoryTable.TABLA.PRODUCTOS);
        objTable.abrirBaseDeDatos(this);

        for(int i=0;i<300;i++) {
            for(int x=0;x<10;x++) {
                sValor1 = "Producto Nombre " + String.valueOf(i);
                sValor2 = "Producto de Servicios Generales" + String.valueOf(i);
                sValor3 = "Producto Generales " + String.valueOf(i);

                objTable.insertar(new Object[]{x,i,sValor1,sValor2,sValor3,0,0,
                mUrls[mRandom.nextInt(mUrls.length - 1)],
                mUrls[mRandom.nextInt(mUrls.length - 1)],
                mUrls[mRandom.nextInt(mUrls.length - 1)],
                "2014-10-10",1,1});
            }
        }
        objTable.cerrar();
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            String string = bundle.getString("myKey");
            Intent objIntent = new Intent(CargaInformacion.this,ProveedorListActivity.class);
            startActivity(objIntent);
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.carga_informacion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }
}
