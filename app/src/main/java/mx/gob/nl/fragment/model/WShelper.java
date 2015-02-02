package mx.gob.nl.fragment.model;

import android.database.sqlite.SQLiteDatabase;

public  class WShelper{

    // Información de la tabla
    public static final String URL_BASE = "http://hnl.desoftspaces.com/services/";
    public static final String URL_CONFIGURACION = "";
    public static final String URL_CATEGORIA = "getCategorias";
    public static final String URL_PROVEEDORES = "getProveedores";
    public static final String URL_SUBCATEGORIA = "getSubCategorias";
    public static final String URL_PRODUCTOS = "getProductos";
    public static final String URL_FOTOS = "getFotos";
    public static final String URL_PROVEEDORSUBCATEGORIA = "getProveedoresSubCategorias";
    public static final String URL_ACTUALIZACION = "";


    public static void InsertConfiguracion(SQLiteDatabase db) {
        db.execSQL("INSERT INTO "  + DBhelper.TABLE_CONFIGURACION  + "(" + DBhelper.CONFIGURACION_TIPO +"," + DBhelper.CONFIGURACION_VALOR + ")  values (1,'" + URL_BASE+URL_CATEGORIA+"')" );
        db.execSQL("INSERT INTO "  + DBhelper.TABLE_CONFIGURACION  + "(" + DBhelper.CONFIGURACION_TIPO +"," + DBhelper.CONFIGURACION_VALOR + ")  values (1,'" + URL_BASE+URL_SUBCATEGORIA+"')" );
        db.execSQL("INSERT INTO "  + DBhelper.TABLE_CONFIGURACION  + "(" + DBhelper.CONFIGURACION_TIPO +"," + DBhelper.CONFIGURACION_VALOR + ")  values (1,'" + URL_BASE+URL_PROVEEDORES+"')" );
        db.execSQL("INSERT INTO "  + DBhelper.TABLE_CONFIGURACION  + "(" + DBhelper.CONFIGURACION_TIPO +"," + DBhelper.CONFIGURACION_VALOR + ")  values (1,'" + URL_BASE+URL_PRODUCTOS+"')" );
        db.execSQL("INSERT INTO "  + DBhelper.TABLE_CONFIGURACION  + "(" + DBhelper.CONFIGURACION_TIPO +"," + DBhelper.CONFIGURACION_VALOR + ")  values (1,'" + URL_BASE+URL_FOTOS+"')" );
        db.execSQL("INSERT INTO "  + DBhelper.TABLE_CONFIGURACION  + "(" + DBhelper.CONFIGURACION_TIPO +"," + DBhelper.CONFIGURACION_VALOR + ")  values (1,'" + URL_BASE+URL_PROVEEDORSUBCATEGORIA+"')" );
    }
}