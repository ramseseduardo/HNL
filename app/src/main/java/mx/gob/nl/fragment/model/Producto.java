package mx.gob.nl.fragment.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Ramses on 26/10/14.
 */
public class Producto implements ISQLControlador  {
    private DBhelper dbhelper;
    private SQLiteDatabase database;

    @Override
    public Object abrirBaseDeDatos() throws SQLException {
        dbhelper = new DBhelper(ApplicationContext.getInstance());
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
        cv.put(DBhelper.PRODUCTO_ID_PRODUCTO, params[0].toString());
        cv.put(DBhelper.PRODUCTO_ID_PROVEEDOR,  params[1].toString());
        cv.put(DBhelper.PRODUCTO_NOMBRE,  params[2].toString());
        cv.put(DBhelper.PRODUCTO_DESCRIPCIONCORTA,  params[3].toString());
        cv.put(DBhelper.PRODUCTO_DESCRIPCION,  params[4].toString());
        cv.put(DBhelper.PRODUCTO_PRECIOMENUDEO,  params[5].toString());
        cv.put(DBhelper.PRODUCTO_PRECIOMAYOREO,  params[6].toString());
        cv.put(DBhelper.PRODUCTO_FOTO1,  params[7].toString());
        cv.put(DBhelper.PRODUCTO_FOTO2,  params[8].toString());
        cv.put(DBhelper.PRODUCTO_FOTO3,  params[9].toString());
        cv.put(DBhelper.PRODUCTO_VIGENCIA,  params[10].toString());
        cv.put(DBhelper.PRODUCTO_ACTIVO,  params[11].toString());
        cv.put(DBhelper.PRODUCTO_ORDEN,  params[12].toString());
        database.insert(DBhelper.TABLE_PRODUCTOS, null, cv);
    }

    @Override
    public Cursor leer(String[] params) {
        String[] todasLasColumnas = new String[] {
                DBhelper.PRODUCTO_ID_PRODUCTO,
                DBhelper.PRODUCTO_ID_PROVEEDOR,
                DBhelper.PRODUCTO_NOMBRE,
                DBhelper.PRODUCTO_DESCRIPCIONCORTA,
                DBhelper.PRODUCTO_DESCRIPCION,
                DBhelper.PRODUCTO_PRECIOMENUDEO,
                DBhelper.PRODUCTO_PRECIOMAYOREO,
                DBhelper.PRODUCTO_FOTO1,
                DBhelper.PRODUCTO_FOTO2,
                DBhelper.PRODUCTO_FOTO3,
                DBhelper.PRODUCTO_VIGENCIA,
                DBhelper.PRODUCTO_ACTIVO,
                DBhelper.PRODUCTO_ORDEN
        };
        Cursor c = database.query(DBhelper.TABLE_PRODUCTOS, todasLasColumnas,null,
                params, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    @Override
    public int actualizar(Object[] params) {
        ContentValues cv = new ContentValues();
        cv.put(DBhelper.PRODUCTO_ID_PROVEEDOR, params[1].toString());
        cv.put(DBhelper.PRODUCTO_NOMBRE,  params[2].toString());
        cv.put(DBhelper.PRODUCTO_DESCRIPCIONCORTA,  params[3].toString());
        cv.put(DBhelper.PRODUCTO_DESCRIPCION,  params[4].toString());
        cv.put(DBhelper.PRODUCTO_PRECIOMENUDEO,  params[5].toString());
        cv.put(DBhelper.PRODUCTO_PRECIOMAYOREO,  params[6].toString());
        cv.put(DBhelper.PRODUCTO_FOTO1,  params[7].toString());
        cv.put(DBhelper.PRODUCTO_FOTO2,  params[8].toString());
        cv.put(DBhelper.PRODUCTO_FOTO3,  params[6].toString());
        cv.put(DBhelper.PRODUCTO_VIGENCIA,  params[7].toString());
        cv.put(DBhelper.PRODUCTO_ACTIVO,  params[8].toString());
        cv.put(DBhelper.PRODUCTO_ORDEN,  params[9].toString());

        int i = database.update(DBhelper.TABLE_PRODUCTOS, cv,
                DBhelper.PRODUCTO_ID_PRODUCTO + " = " + params[0].toString(), null);
        return i;
    }

    @Override
    public void deleteData(Object[] params) {
        database.delete(DBhelper.TABLE_PRODUCTOS, DBhelper.PRODUCTO_ID_PRODUCTO + "="
                + params[0].toString(), null);
    }
}
