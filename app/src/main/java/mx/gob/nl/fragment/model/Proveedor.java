package mx.gob.nl.fragment.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Ramses on 26/10/14.
 */
public class Proveedor implements ISQLControlador  {
    private DBhelper dbhelper;
    private Context ourcontext;
    private SQLiteDatabase database;

    @Override
    public Object abrirBaseDeDatos() throws SQLException {
        dbhelper = new DBhelper(ourcontext);
        database = dbhelper.getWritableDatabase();
        return this;
    }

    @Override
    public void cerrar() {
        dbhelper.close();
    }

    @Override
    public void insertar(Object[] params) {
        ContentValues cv = new ContentValues();
        cv.put(DBhelper.PROVEEDOR_ID_PROVEEDOR, params[0].toString());
        cv.put(DBhelper.PROVEEDOR_NOMBRE,  params[1].toString());
        cv.put(DBhelper.PROVEEDOR_PRESENTACION,  params[2].toString());
        cv.put(DBhelper.PROVEEDOR_PRESENTACIONCORTA,  params[3].toString());
        cv.put(DBhelper.PROVEEDOR_SERVICIO1,  params[4].toString());
        cv.put(DBhelper.PROVEEDOR_SERVICIO2,  params[5].toString());
        cv.put(DBhelper.PROVEEDOR_SERVICIO3,  params[6].toString());
        cv.put(DBhelper.PROVEEDOR_TELEFONO1,  params[7].toString());
        cv.put(DBhelper.PROVEEDOR_TELEFONO2,  params[8].toString());
        cv.put(DBhelper.PROVEEDOR_TWITER,  params[9].toString());
        cv.put(DBhelper.PROVEEDOR_FACEBOOK,  params[10].toString());
        cv.put(DBhelper.PROVEEDOR_SITIOWEB,  params[11].toString());
        cv.put(DBhelper.PROVEEDOR_MAIL,  params[12].toString());
        cv.put(DBhelper.PROVEEDOR_CALLE,  params[13].toString());
        cv.put(DBhelper.PROVEEDOR_NUMEROEXTERIOR,  params[14].toString());
        cv.put(DBhelper.PROVEEDOR_NUMEROINTERIOR,  params[15].toString());
        cv.put(DBhelper.PROVEEDOR_COLONIA,  params[16].toString());
        cv.put(DBhelper.PROVEEDOR_MUNICIPIO,  params[17].toString());
        cv.put(DBhelper.PROVEEDOR_ESTADO,  params[18].toString());
        cv.put(DBhelper.PROVEEDOR_PAIS,  params[19].toString());
        cv.put(DBhelper.PROVEEDOR_FOTO,  params[20].toString());
        cv.put(DBhelper.PROVEEDOR_CLAVEBUSQUEDA,  params[21].toString());
        cv.put(DBhelper.PROVEEDOR_VIGENCIA,  params[22].toString());
        cv.put(DBhelper.PROVEEDOR_ACTIVO,  params[22].toString());
        cv.put(DBhelper.PROVEEDOR_ORDEN,  params[23].toString());
        database.insert(DBhelper.TABLE_PROVEEDORES, null, cv);
    }

    @Override
    public Cursor leer(String[] params) {
        String[] todasLasColumnas = new String[] {
                DBhelper.PROVEEDOR_ID_PROVEEDOR,
                DBhelper.PROVEEDOR_NOMBRE,
                DBhelper.PROVEEDOR_PRESENTACION,
                DBhelper.PROVEEDOR_PRESENTACIONCORTA,
                DBhelper.PROVEEDOR_SERVICIO1,
                DBhelper.PROVEEDOR_SERVICIO2,
                DBhelper.PROVEEDOR_SERVICIO3,
                DBhelper.PROVEEDOR_TELEFONO1,
                DBhelper.PROVEEDOR_TELEFONO2,
                DBhelper.PROVEEDOR_TWITER,
                DBhelper.PROVEEDOR_FACEBOOK,
                DBhelper.PROVEEDOR_SITIOWEB,
                DBhelper.PROVEEDOR_MAIL,
                DBhelper.PROVEEDOR_CALLE,
                DBhelper.PROVEEDOR_NUMEROEXTERIOR,
                DBhelper.PROVEEDOR_NUMEROINTERIOR,
                DBhelper.PROVEEDOR_COLONIA,
                DBhelper.PROVEEDOR_MUNICIPIO,
                DBhelper.PROVEEDOR_ESTADO,
                DBhelper.PROVEEDOR_PAIS,
                DBhelper.PROVEEDOR_FOTO,
                DBhelper.PROVEEDOR_CLAVEBUSQUEDA,
                DBhelper.PROVEEDOR_VIGENCIA,
                DBhelper.PROVEEDOR_ACTIVO,
                DBhelper.PROVEEDOR_ORDEN
        };
        Cursor c = database.query(DBhelper.TABLE_PROVEEDORES, todasLasColumnas,null,
                params, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    @Override
    public int actualizar(Object[] params) {
        ContentValues cv = new ContentValues();
        cv.put(DBhelper.PROVEEDOR_NOMBRE,  params[1].toString());
        cv.put(DBhelper.PROVEEDOR_PRESENTACION,  params[2].toString());
        cv.put(DBhelper.PROVEEDOR_PRESENTACIONCORTA,  params[3].toString());
        cv.put(DBhelper.PROVEEDOR_SERVICIO1,  params[4].toString());
        cv.put(DBhelper.PROVEEDOR_SERVICIO2,  params[5].toString());
        cv.put(DBhelper.PROVEEDOR_SERVICIO3,  params[6].toString());
        cv.put(DBhelper.PROVEEDOR_TELEFONO1,  params[7].toString());
        cv.put(DBhelper.PROVEEDOR_TELEFONO2,  params[8].toString());
        cv.put(DBhelper.PROVEEDOR_TWITER,  params[9].toString());
        cv.put(DBhelper.PROVEEDOR_FACEBOOK,  params[10].toString());
        cv.put(DBhelper.PROVEEDOR_SITIOWEB,  params[11].toString());
        cv.put(DBhelper.PROVEEDOR_MAIL,  params[12].toString());
        cv.put(DBhelper.PROVEEDOR_CALLE,  params[13].toString());
        cv.put(DBhelper.PROVEEDOR_NUMEROEXTERIOR,  params[14].toString());
        cv.put(DBhelper.PROVEEDOR_NUMEROINTERIOR,  params[15].toString());
        cv.put(DBhelper.PROVEEDOR_COLONIA,  params[16].toString());
        cv.put(DBhelper.PROVEEDOR_MUNICIPIO,  params[17].toString());
        cv.put(DBhelper.PROVEEDOR_ESTADO,  params[18].toString());
        cv.put(DBhelper.PROVEEDOR_PAIS,  params[19].toString());
        cv.put(DBhelper.PROVEEDOR_FOTO,  params[20].toString());
        cv.put(DBhelper.PROVEEDOR_CLAVEBUSQUEDA,  params[21].toString());
        cv.put(DBhelper.PROVEEDOR_VIGENCIA,  params[22].toString());
        cv.put(DBhelper.PROVEEDOR_ACTIVO,  params[22].toString());
        cv.put(DBhelper.PROVEEDOR_ORDEN,  params[23].toString());
        int i = database.update(DBhelper.TABLE_PROVEEDORES, cv,
                DBhelper.PROVEEDOR_ID_PROVEEDOR + " = " + params[0].toString(), null);
        return i;
    }

    @Override
    public void deleteData(Object[] params) {
        database.delete(DBhelper.TABLE_PROVEEDORES, DBhelper.PROVEEDOR_ID_PROVEEDOR + "="
                + params[0].toString(), null);
    }
}
