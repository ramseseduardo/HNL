package mx.gob.nl.fragment.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Ramses on 26/10/14.
 */
public class Categoria implements ISQLControlador {
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
        cv.put(DBhelper.CATEGORIA_ID_CATEGORIA, params[0].toString());
        cv.put(DBhelper.CATEGORIA_DESCRIPCION,  params[1].toString());
        database.insert(DBhelper.TABLE_CATEGORIA, null, cv);
    }

    @Override
    public Cursor leer(String selection, String[] selectionArgs) {
        String[] todasLasColumnas = new String[] {
                DBhelper.CATEGORIA_ID_CATEGORIA,
                DBhelper.CATEGORIA_DESCRIPCION,
        };
        Cursor c = database.query(DBhelper.TABLE_CATEGORIA, todasLasColumnas,selection,
                selectionArgs, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    @Override
    public int actualizar(Object[] params) {
        ContentValues cv = new ContentValues();
        cv.put(DBhelper.CATEGORIA_DESCRIPCION, params[1].toString());
        int i = database.update(DBhelper.TABLE_CATEGORIA, cv,
                DBhelper.CATEGORIA_ID_CATEGORIA + " = " + params[0].toString(), null);
        return i;
    }

    @Override
    public void deleteData(Object[] params) {
        database.delete(DBhelper.TABLE_CATEGORIA, DBhelper.CATEGORIA_ID_CATEGORIA + "="
                + params[0].toString(), null);
    }

    @Override
    public int count()
    {
        String query = "Select count(*) FROM " + DBhelper.TABLE_CATEGORIA;
        int iReturn = 0;

        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            iReturn = cursor.getInt(0);
        }

        return iReturn;
    }
}
