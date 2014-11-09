package mx.gob.nl.fragment.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Ramses on 26/10/14.
 */
public class SubCategoria implements ISQLControlador  {
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
        cv.put(DBhelper.SUBCATEGORIA_ID_SUBCATEGORIA, params[0].toString());
        cv.put(DBhelper.SUBCATEGORIA_DESCRIPCION,  params[1].toString());
        cv.put(DBhelper.SUBCATEGORIA_ID_CATEGORIA,  params[2].toString());
        database.insert(DBhelper.TABLE_SUBCATEGORIA, null, cv);
    }

    @Override
    public Cursor leer(String[] params) {
        String[] todasLasColumnas = new String[] {
                DBhelper.SUBCATEGORIA_ID_SUBCATEGORIA,
                DBhelper.SUBCATEGORIA_DESCRIPCION,
                DBhelper.SUBCATEGORIA_ID_CATEGORIA
        };
        Cursor c = database.query(DBhelper.TABLE_SUBCATEGORIA, todasLasColumnas,null,
                params, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    @Override
    public int actualizar(Object[] params) {
        ContentValues cv = new ContentValues();
        cv.put(DBhelper.SUBCATEGORIA_DESCRIPCION, params[1].toString());
        cv.put(DBhelper.SUBCATEGORIA_ID_CATEGORIA,  params[2].toString());
        int i = database.update(DBhelper.TABLE_SUBCATEGORIA, cv,
                DBhelper.SUBCATEGORIA_ID_SUBCATEGORIA + " = " + params[0].toString(), null);
        return i;
    }

    @Override
    public void deleteData(Object[] params) {
        database.delete(DBhelper.TABLE_SUBCATEGORIA, DBhelper.SUBCATEGORIA_ID_SUBCATEGORIA + "="
                + params[0].toString(), null);
    }

    @Override
    public int count()
    {
        String query = "Select count(*) FROM " + DBhelper.TABLE_SUBCATEGORIA;
        int iReturn = 0;

        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            iReturn = cursor.getInt(0);
        }

        return iReturn;
    }
}
