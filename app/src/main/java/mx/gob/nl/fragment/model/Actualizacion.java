package mx.gob.nl.fragment.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Ramses on 26/10/14.
 */
public class Actualizacion implements ISQLControlador {

    private DBhelper dbhelper;
    private SQLiteDatabase database;
    
    @Override
    public Object abrirBaseDeDatos(Context context) throws SQLException {
        dbhelper = new DBhelper(context);
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
        cv.put(DBhelper.ACTUALIZACION_FECHA_ACTUALIZACION, params[0].toString());
        cv.put(DBhelper.ACTUALIZACION_CONTROLPROVEEDOR,  params[1].toString());
        cv.put(DBhelper.ACTUALIZACION_CONTROLPRODUCTOS,  params[2].toString());
        cv.put(DBhelper.ACTUALIZACION_CONTROLCONFIGURACION,  params[3].toString());
        cv.put(DBhelper.ACTUALIZACION_TOTALPROVEEDOR,  params[4].toString());
        cv.put(DBhelper.ACTUALIZACION_TOTALPRODUCTOS,  params[5].toString());
        cv.put(DBhelper.ACTUALIZACION_TOTALCONFIGURACION,  params[6].toString());
        cv.put(DBhelper.ACTUALIZACION_IDIOMA,  params[7].toString());
        database.insert(DBhelper.TABLE_ACTUALIZACION, null, cv);
    }

    @Override
    public Cursor leer(String[] params) {
        String[] todasLasColumnas = new String[] {
            DBhelper.ACTUALIZACION_ID_ACTUALIZACION,
            DBhelper.ACTUALIZACION_FECHA_ACTUALIZACION,
            DBhelper.ACTUALIZACION_CONTROLPROVEEDOR,
            DBhelper.ACTUALIZACION_CONTROLPRODUCTOS,
            DBhelper.ACTUALIZACION_CONTROLCONFIGURACION,
            DBhelper.ACTUALIZACION_TOTALPROVEEDOR,
            DBhelper.ACTUALIZACION_TOTALPRODUCTOS,
            DBhelper.ACTUALIZACION_TOTALCONFIGURACION,
            DBhelper.ACTUALIZACION_IDIOMA
        };
        Cursor c = database.query(DBhelper.TABLE_ACTUALIZACION, todasLasColumnas,null,
                params, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    @Override
    public int actualizar(Object[] params) {
        ContentValues cv = new ContentValues();
        cv.put(DBhelper.ACTUALIZACION_FECHA_ACTUALIZACION, params[1].toString());
        cv.put(DBhelper.ACTUALIZACION_CONTROLPROVEEDOR,  params[2].toString());
        cv.put(DBhelper.ACTUALIZACION_CONTROLPRODUCTOS,  params[3].toString());
        cv.put(DBhelper.ACTUALIZACION_CONTROLCONFIGURACION,  params[4].toString());
        cv.put(DBhelper.ACTUALIZACION_TOTALPROVEEDOR,  params[5].toString());
        cv.put(DBhelper.ACTUALIZACION_TOTALPRODUCTOS,  params[6].toString());
        cv.put(DBhelper.ACTUALIZACION_TOTALCONFIGURACION,  params[7].toString());
        cv.put(DBhelper.ACTUALIZACION_IDIOMA,  params[8].toString());
        int i = database.update(DBhelper.TABLE_ACTUALIZACION, cv,
                DBhelper.ACTUALIZACION_ID_ACTUALIZACION + " = " + params[0].toString(), null);
        return i;
    }

    @Override
    public void deleteData(Object[] params) {
        database.delete(DBhelper.TABLE_ACTUALIZACION, DBhelper.ACTUALIZACION_ID_ACTUALIZACION + "="
                + params[0].toString(), null);
    }


    @Override
    public int count()
    {
        String query = "Select count(*) FROM " + DBhelper.TABLE_ACTUALIZACION;
        int iReturn = 0;

        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            iReturn = cursor.getInt(0);
        }

        return iReturn;
    }
}
