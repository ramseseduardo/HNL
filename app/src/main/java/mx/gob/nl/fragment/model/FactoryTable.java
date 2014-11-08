package mx.gob.nl.fragment.model;

/**
 * Created by Ramses on 26/10/14.
 */
public class FactoryTable {
    public enum TABLA
    {
        ACTUALIZACION,
        CONFIGURACION,
        CATEGORIA,
        PROVEEDORES,
        SUBCATEGORIA,
        PRODUCTOS,
        PROVEEDORSUBCATEGORIA
    }


    public ISQLControlador getSQLController(TABLA pTabla)
    {
        ISQLControlador objSqlController = null;

        switch(pTabla)
        {
            case ACTUALIZACION:
                objSqlController = new Actualizacion();
                break;
            case CATEGORIA:
                objSqlController = new Categoria();
                break;
            case CONFIGURACION:
                objSqlController = new Configuracion();
                break;
            case PRODUCTOS:
                objSqlController = new Producto();
                break;
            case PROVEEDORES:
                objSqlController = new Proveedor();
                break;
            case PROVEEDORSUBCATEGORIA:
                objSqlController = new ProveedorSubCategoria();
                break;
            case SUBCATEGORIA:
                objSqlController = new SubCategoria();
                break;
            default:
                break;

        }

        return objSqlController;
    }
}
