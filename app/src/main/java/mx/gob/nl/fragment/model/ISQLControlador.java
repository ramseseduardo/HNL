package mx.gob.nl.fragment.model;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

/**
 * Created by Ramses on 26/10/14.
 */
public interface ISQLControlador{
    public Object abrirBaseDeDatos(Context context) throws SQLException;
    public void cerrar();
    public void insertar(Object[] params);
    public Cursor leer(String selection, String[] selectionArgs);
    public int actualizar(Object[] params);
    public void deleteData(Object[] params);
    public int count();
}
