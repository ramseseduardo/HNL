package mx.gob.nl.fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;

import mx.gob.nl.fragment.model.FactoryTable;
import mx.gob.nl.fragment.model.ISQLControlador;
import mx.gob.nl.fragment.model.Producto;
import mx.gob.nl.fragment.model.ProveedorSubCategoria;
import mx.gob.nl.fragment.model.WebService;


public class CargaInformacion extends Activity {

    Calendar sFechaActualizacion = null;
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
        Calendar tabla = new GregorianCalendar();
        Calendar currentDate = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date dateTime = null;
        DateFormat readFormat = new SimpleDateFormat( "EEE MMM d HH:mm:ss zzz yyyy", Locale.US);

        objTable = FactoryTable.getSQLController(FactoryTable.TABLA.ACTUALIZACION);

        objTable.abrirBaseDeDatos(this);
        if(objTable.count() == 0)
        {
            objTable.cerrar();
            SincronizarBaseDeDatos(true);
            return;
        }

        Cursor objCursor = objTable.leer(null,null);

        while (!objCursor.isAfterLast()) {
            try {
                dateTime = readFormat.parse(objCursor.getString(1));
            } catch (ParseException e) {
                e.printStackTrace();
                return;
            }
            tabla.setTime(dateTime);

            if(sFechaActualizacion == null || tabla.getTime().before(sFechaActualizacion.getTime()))
                sFechaActualizacion = tabla;

            if(sdf.format(tabla.getTime()).equals(sdf.format(currentDate.getTime()))) {
                objTable.cerrar();
                return;
            }
            objCursor.moveToNext();
        }

        objTable.cerrar();
        SincronizarBaseDeDatos(false);
        //Conectar a Servicios
    }

    private void SincronizarBaseDeDatos(boolean bNueva) {

        ISQLControlador objTable;
        Calendar currentDate = new GregorianCalendar();

        InsertUpdateDataBase(FactoryTable.TABLA.CATEGORIA, WebService.Service.CATEGORIA,bNueva);
        InsertUpdateDataBase(FactoryTable.TABLA.SUBCATEGORIA, WebService.Service.SUBCATEGORIA,bNueva);
        InsertUpdateDataBase(FactoryTable.TABLA.PROVEEDORES, WebService.Service.PROVEEDORES,bNueva);
        InsertUpdateDataBase(FactoryTable.TABLA.PRODUCTOS, WebService.Service.PRODUCTOS,bNueva);
        InsertUpdateProveedorSubCategoriaDataBase(bNueva);
        InsertUpdateFotoDataBase(bNueva);

        objTable = FactoryTable.getSQLController(FactoryTable.TABLA.ACTUALIZACION);

        objTable.abrirBaseDeDatos(this);

        objTable.insertar(new Object[]{
                currentDate.getTime(), 0, 0, 0, 0, 0, 0, 0
        });

        objTable.cerrar();

    }

    private void InsertUpdateProveedorSubCategoriaDataBase(boolean bNueva) {
        ISQLControlador objTable;
        Object[][] objResult;
        Object[] objList = new Object[1];
        String[] oInsert = new String[2];
        objTable = FactoryTable.getSQLController(FactoryTable.TABLA.PROVEEDORSUBCATEGORIA);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        WebService objWService = new WebService();
        if(bNueva)
            objWService.OnLineCallWebService( WebService.Service.PROVEEDORSUBCATEGORIA);
        else
            objWService.OnLineCallWebService(WebService.Service.PROVEEDORSUBCATEGORIA,sdf.format(sFechaActualizacion));


        objResult = objWService.readJSONToObject(WebService.Accion.ALL);

        objTable.abrirBaseDeDatos(this);

        for(int i=0;i<objResult.length;i++) {
            objList[0]= objResult[i][0];
            if(objList[0] != null)
                ((ProveedorSubCategoria)objTable).deleteProveedor(objList);
        }

        for(int i=0;i<objResult.length;i++) {
            oInsert[0] = ((String) objResult[i][0]);
            try{objList = (Object[]) objResult[i][1];}catch(Exception ex){objList = null;}
            if(objList != null && objList.length > 0 && objList[0] != null)
                for(int x=0;x<objList.length;x++) {
                    oInsert[1] = objList[x].toString();
                    objTable.insertar(oInsert);
                }
        }

        objTable.cerrar();
    }

    private void InsertUpdateFotoDataBase(boolean bNueva) {

        ISQLControlador objTable;
        Object[][] objResult;
        Object[] objList = new Object[1];
        objTable = FactoryTable.getSQLController(FactoryTable.TABLA.PRODUCTOS);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int iCount = 0;
        String sProducto = "";
        String sAntProducto = "";

        WebService objWService = new WebService();
        if(bNueva)
            objWService.OnLineCallWebService( WebService.Service.FOTOS);
        else
            objWService.OnLineCallWebService(WebService.Service.FOTOS,sdf.format(sFechaActualizacion));


        objResult = objWService.readJSONToObject(WebService.Accion.ALL);

        objTable.abrirBaseDeDatos(this);

        for(int i=0;i<objResult.length;i++) {
            sProducto = String.valueOf(objResult[i][0]);

            if(!sProducto.equals(sAntProducto)) {
                iCount = 1;
                sAntProducto = sProducto;
            }
            else
                iCount++;

            objList = objResult[i];

            if(objList[0] != null)
                switch (iCount) {
                    case 1:
                        ((Producto) objTable).actualizarFoto1(objList);
                        break;
                    case 2:
                        ((Producto) objTable).actualizarFoto2(objList);
                        break;
                    case 3:
                        ((Producto) objTable).actualizarFoto3(objList);
                        break;
                    default:
                        break;
                }
        }


        objTable.cerrar();
    }

    private void InsertUpdateDataBase(FactoryTable.TABLA oTabla,WebService.Service oService,boolean bNueva) {
        ISQLControlador objTable;
        Object[][] objResult;
        Object[] objList;
        objTable = FactoryTable.getSQLController(oTabla);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        WebService objWService = new WebService();
        if(bNueva)
            objWService.OnLineCallWebService(oService);
        else
            objWService.OnLineCallWebService(oService,sdf.format(sFechaActualizacion));

        if(bNueva)
            objResult = objWService.readJSONToObject(WebService.Accion.ALL);
        else
            objResult = objWService.readJSONToObject(WebService.Accion.INSERT);


        objTable.abrirBaseDeDatos(this);

        for(int i=0;i<objResult.length;i++) {
            objList = objResult[i];
            if(objList[0] != null)
                objTable.insertar(objResult[i]);
        }

        if(!bNueva)
        {
            objResult = objWService.readJSONToObject(WebService.Accion.UPDATE);

            for (int i = 0; i < objResult.length; i++) {
                objList = objResult[i];
                if (objList[0] != null)
                    objTable.actualizar(objResult[i]);
            }
        }

        objTable.cerrar();
    }

    private void CargaBaseDatos3() {
        ISQLControlador objTable;
        String sValor1, sValor2 ,sValor3;
        int idProducto = 0,idProveedor  = 0;

        sValor1 = "";
        sValor2 = "";
        sValor3 = "";

        objTable = FactoryTable.getSQLController(FactoryTable.TABLA.PROVEEDORES);

        objTable.abrirBaseDeDatos(this);

        objTable.insertar(new Object[]{
                25, "PlasticJD", "fabricante Moldes soplado, fabricante moldes pet, fabricante moldes inyección, fabricación de envases, fabricación piezas inyección", "fabricante Moldes soplado, fabricante moldes pet", "", "", "", "448180889855", "", "", "", "www.plasticsjd.com", "juandavid.r09@gmail.com", "", "", "", "", "Monterrey", "Nuevo Leon", "Mexico", "http://hnl.desoftspaces.com/uploads/sin_foto.png", "plastico", "", 1, 1
        });

        objTable.insertar(new Object[]{
                27, "Coqueterias Mexicanas", "Coqueterias Mexicanas", "Coqueterias Mexicanas", "", "", "", "81 1745 9174", "", "", " www.facebook.com/coqueteriasmexicanas", "", "coqueteriasmexicanas@hotmail.com", "", "", "", "", "Monterrey", "Nuevo Leon", "Mexico", "http://hnl.desoftspaces.com/Uploads/27coqueterias%20mexicanas.jpg", "zapato", "", 1, 2
        });

        objTable.insertar(new Object[]{
                28, "ECOkiller", "ECOkiller", "ECOkiller", "", "", "", "19347555", "", "@ecokillerinfo", "www.facebook.com/ecokiller", "www.ecokiller.com.mx", "ventas@ecokiller.com.mx", "", "", "", "", "", "", "", "http://hnl.desoftspaces.com/Uploads/28ecokiller.png", "fumigaciones", "", 1, 3
        });


        objTable.insertar(new Object[]{
                29, "CRISTA MARIANA JIMENEZ TREJO", "ALISSE LISSAGE VIE NUTRITIF", "ALISSE LISSAGE VIE NUTRITIF", "", "", "", "81 12 90 32 61", "83454105", "", "", "", "cristamariana@mobest.mx", "", "", "", "", "Monterrey", "Nuevo Leon", "Mexico", "http://hnl.desoftspaces.com/uploads/sin_foto.png", "shampoo", "", 1, 4
        });

        objTable.insertar(new Object[]{
                30, "Alex & Tony S.A. De C.V.", "Jamaicool, Tamarindcool, Te verdecool, Lemoncool", "Jamaicool, Tamarindcool, Te verdecool, Lemoncool", "", "", "", "(81)82127820", "", "", "www.facebook.com/amigosdeljamaicool", "www.alexytony.com.mx", "Alexytony@terra.com.mx", "", "", "", "", "San Pedro Garza Garcia", "Nuevo Leon", "Mexico", "http://hnl.desoftspaces.com/Uploads/30Jamaicool.jpg", "jamaica", "", 1, 5
        });

        objTable.insertar(new Object[]{
                31, "ALIMENTOS SR NATURAL SAPI DE CV", "SR NATURAL, MANASUR.", "SR NATURAL, MANASUR.", "", "", "", "8183848200", "", "", "wwww.facebook.com/AlimentosSrNatural", "www.srnatural.com", "ricardo.gonzalez@srnatural.com.mx", "", "", "", "", "Escobedo", "Nuevo Leon", "Mexico", "http://hnl.desoftspaces.com/Uploads/31sr%20natural.jpg", "avena", "", 1, 6
        });

        objTable.insertar(new Object[]{
                32, "Cervecería Albur", "Chalupa - Cerveza Golden Ale", "Chalupa - Cerveza Golden Ale, Mano Pachona - Cerveza IPA, La Avenida - Cerveza Brown Ale", "", "", "", "(81)8252.0090", "", "@cerveceriaalbur", "www.facebook.com/cerveceria.albur", "http://www.albur.mx", "pablo.flores@albur.mx", "", "", "", "", "Guadalupe", "Nuevo Leon", "Mexico", "http://hnl.desoftspaces.com/Uploads/32albur.png", "cerveza", "", 1, 7
        });


        objTable.insertar(new Object[]{
                33, "Gloriosas Tentaciones", "GLORIOSAS TENTACIONES", "GLORIOSAS TENTACIONES", "", "", "", "(81)8252.0090", "", "", "www.facebook.com/gloriosastentaciones", "", "gloriosastentaciones@yahoo.com.mx", "", "", "", "", "San Pedro Garza García", "Nuevo Leon", "Mexico", "http://hnl.desoftspaces.com/Uploads/33GLORIOSAS%20TENTACIONES.jpg", "dulces", "", 1, 8
        });

        objTable.insertar(new Object[]{
                34, "VERALMEX S.P.R. DE R.L.", "Veralmex", "Veralmex", "", "", "", "88183352761", "", "", "www.facebook.com/veralmex", "www.veralmex.com", "guillermo@veralmex.com", "", "", "", "", "Montemorelos", "Nuevo Leon", "Mexico", "http://hnl.desoftspaces.com/Uploads/34veralmex.jpg", "tostadas", "", 1, 9
        });

        objTable.insertar(new Object[]{
                35, "La Maizería", "La Maizería", "La Maizería", "", "", "", "(044) 8120007342", "", "", "", "", "jtalamantess@hotmail.com", "", "", "", "", "San Nicolás", "Nuevo Leon", "Mexico", "http://hnl.desoftspaces.com/uploads/sin_foto.png", "pan", "", 1, 10
        });



        objTable.cerrar();

        objTable = FactoryTable.getSQLController(FactoryTable.TABLA.PRODUCTOS);
        objTable.abrirBaseDeDatos(this);

        objTable.insertar(new Object[]{
                18, 25, "Caja Makita", "", "Caja Makita", 0, 0, "http://hnl.desoftspaces.com/Uploads/1-18Caja-Makita-JD_02.JPG","http://hnl.desoftspaces.com/Uploads/1-18Caja-Makita-JD_03.JPG","http://upload.wikimedia.org/wikipedia/commons/c/ca/1x1.png", "", 1, 1
        });

        objTable.insertar(new Object[]{
                19, 25, "Casco Ticsa", "", "Casco Ticsa", 0, 0, "http://hnl.desoftspaces.com/Uploads/1-19Casco-Ticsa_01.JPG","http://hnl.desoftspaces.com/Uploads/1-19Casco-Ticsa_02.JPG","http://hnl.desoftspaces.com/Uploads/1-19Casco-Ticsa_03.JPG", "", 1, 2
        });

        objTable.insertar(new Object[]{
                20, 25, "Envase", "", "Envase", 0, 0, "http://hnl.desoftspaces.com/Uploads/1-20Envase-JD_01.JPG","http://hnl.desoftspaces.com/Uploads/1-20Envase-JD_02.JPG","http://upload.wikimedia.org/wikipedia/commons/c/ca/1x1.png", "", 1, 3
        });

        objTable.insertar(new Object[]{
                21, 25, "Rueda", "", "Rueda", 0, 0, "http://hnl.desoftspaces.com/Uploads/1-21Rueda-JD_01.JPG","http://hnl.desoftspaces.com/Uploads/1-21Rueda-JD_02.JPG","http://hnl.desoftspaces.com/Uploads/1-21Rueda-JD_03.JPG", "", 1, 4
        });

        objTable.insertar(new Object[]{
                22, 27, "Zapatos z1", "", "Zapatos z1", 0, 0, "http://hnl.desoftspaces.com/Uploads/1-22Coqueterias-Mexico_09.JPG","http://hnl.desoftspaces.com/Uploads/2-22Coqueterias-Mexico_10.JPG","http://hnl.desoftspaces.com/Uploads/3-22Coqueterias-Mexico_11.JPG", "", 1, 5
        });

        objTable.insertar(new Object[]{
                23, 27, "Bolso", "", "Bolso", 0, 0, "http://hnl.desoftspaces.com/Uploads/1-23Coqueterias-Mexico_13.JPG","http://hnl.desoftspaces.com/Uploads/2-23Coqueterias-Mexico_14.JPG","http://hnl.desoftspaces.com/Uploads/3-23Coqueterias-Mexico_15.JPG", "", 1, 6
        });

        objTable.insertar(new Object[]{
                24, 29, "Shampoo", "", "Shampoo", 0, 0, "http://hnl.desoftspaces.com/Uploads/1-24Gontre_01.JPG","http://hnl.desoftspaces.com/Uploads/2-24Gontre_02.JPG","http://hnl.desoftspaces.com/Uploads/3-24Gontre_03.JPG", "", 1, 7
        });

        objTable.insertar(new Object[]{
                25, 30, "Jamaicool", "", "Jamaicool", 0, 0, "http://hnl.desoftspaces.com/Uploads/1-25Jamaicool_01.JPG","http://hnl.desoftspaces.com/Uploads/3-25Jamaicool_03.JPG","http://hnl.desoftspaces.com/Uploads/4-25Jamaicool_04.JPG", "", 1, 8
        });

        objTable.insertar(new Object[]{
                26, 31, "Barras Nutritivas", "", "Barras Nutritivas", 0, 0, "http://hnl.desoftspaces.com/Uploads/1-26Sr_Natural_01.JPG","http://hnl.desoftspaces.com/Uploads/2-26Sr_Natural_02.JPG","http://hnl.desoftspaces.com/Uploads/3-26Sr_Natural_03.JPG", "", 1, 9
        });

        objTable.insertar(new Object[]{
                27, 28, "EcoKiller", "", "EcoKiller", 0, 0, "http://hnl.desoftspaces.com/Uploads/1-27Eco-Killer_01.JPG","http://hnl.desoftspaces.com/Uploads/2-27Eco-Killer_02.JPG","http://hnl.desoftspaces.com/Uploads/3-27Eco-Killer_03.JPG", "", 1, 10
        });

        objTable.insertar(new Object[]{
                28, 32, "Cerveza Artesanal", "", "Cerveza Artesanal", 0, 0, "http://hnl.desoftspaces.com/Uploads/1-28Cervezas-Albur_01.JPG","http://hnl.desoftspaces.com/Uploads/2-28Cervezas-Albur_02.JPG","http://hnl.desoftspaces.com/Uploads/3-28Cervezas-Albur_03.JPG", "", 1, 11
        });
        objTable.insertar(new Object[]{
                29, 33, "Mermelada", "", "Mermelada", 0, 0, "http://hnl.desoftspaces.com/Uploads/1-29Gloriosas-Tradiciones_02.JPG","http://hnl.desoftspaces.com/Uploads/2-29Gloriosas-Tradiciones_03.JPG","http://hnl.desoftspaces.com/Uploads/3-29Gloriosas-Tradiciones_04.JPG", "", 1, 12
        });

        objTable.insertar(new Object[]{
                30, 34, "Tostadas de Nopa", "", "Tostadas de Nopa", 0, 0, "http://hnl.desoftspaces.com/Uploads/1-30Veralmex_01.JPG","http://hnl.desoftspaces.com/Uploads/2-30Veralmex_02.JPG","http://hnl.desoftspaces.com/Uploads/3-30Veralmex_03.JPG", "", 1, 13
        });

        objTable.insertar(new Object[]{
                31, 35, "Pan de Elote", "", "Pan de Elote", 0, 0, "http://hnl.desoftspaces.com/Uploads/1-31LA MAIZERIA_01.JPG","http://hnl.desoftspaces.com/Uploads/2-31LA MAIZERIA_02.JPG","http://hnl.desoftspaces.com/Uploads/3-31LA MAIZERIA_03.JPG", "", 1, 14
        });



        objTable.cerrar();
    }


    private void CargaBaseDatos_2() {
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
