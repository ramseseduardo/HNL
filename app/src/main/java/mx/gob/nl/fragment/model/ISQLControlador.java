package mx.gob.nl.fragment.model;

import android.database.Cursor;
import android.database.SQLException;

/**
 * Created by Ramses on 26/10/14.
 */
public interface ISQLControlador{
    public Object abrirBaseDeDatos() throws SQLException;
    public void cerrar();
    public void insertar(Object[] params);
    public Cursor leer(String[] params);
    public int actualizar(Object[] params);
    public void deleteData(Object[] params);
}
