package mx.gob.nl.fragment.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Ramses on 26/10/14.
 */
public class Configuracion implements ISQLControlador  {
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
        cv.put(DBhelper.CONFIGURACION_ID_CONFIGURACION, params[0].toString());
        cv.put(DBhelper.CONFIGURACION_TIPO,  params[1].toString());
        cv.put(DBhelper.CONFIGURACION_VALOR,  params[2].toString());
        database.insert(DBhelper.TABLE_CONFIGURACION, null, cv);
    }

    @Override
    public Cursor leer(String[] params) {
        String[] todasLasColumnas = new String[] {
                DBhelper.CONFIGURACION_ID_CONFIGURACION,
                DBhelper.CONFIGURACION_TIPO,
                DBhelper.CONFIGURACION_VALOR
        };
        Cursor c = database.query(DBhelper.TABLE_CONFIGURACION, todasLasColumnas,null,
                params, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    @Override
    public int actualizar(Object[] params) {
        ContentValues cv = new ContentValues();
        cv.put(DBhelper.CONFIGURACION_TIPO, params[1].toString());
        cv.put(DBhelper.CONFIGURACION_VALOR,  params[2].toString());
        int i = database.update(DBhelper.TABLE_CONFIGURACION, cv,
                DBhelper.CONFIGURACION_ID_CONFIGURACION + " = " + params[0].toString(), null);
        return i;
    }

    @Override
    public void deleteData(Object[] params) {
        database.delete(DBhelper.TABLE_CONFIGURACION, DBhelper.CONFIGURACION_ID_CONFIGURACION + "="
                + params[0].toString(), null);
    }
}
