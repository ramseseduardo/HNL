package mx.gob.nl.fragment.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper {

    // Información de la tabla
    public static final String TABLE_ACTUALIZACION = "Actualizacion";
    public static final String TABLE_CONFIGURACION = "Configuracion";
    public static final String TABLE_CATEGORIA = "Categoria";
    public static final String TABLE_PROVEEDORES = "Proveedores";
    public static final String TABLE_SUBCATEGORIA = "SubCategoria";
    public static final String TABLE_PRODUCTOS = "Productos";
    public static final String TABLE_PROVEEDORSUBCATEGORIA = "ProveedorCategoria";

    public static final String ACTUALIZACION_ID_ACTUALIZACION = "Id_Actualizacion";
    public static final String ACTUALIZACION_FECHA_ACTUALIZACION = "Fecha_Actualizacion";
    public static final String ACTUALIZACION_CONTROLPROVEEDOR = "ControlProveedor";
    public static final String ACTUALIZACION_CONTROLPRODUCTOS = "ControlProductos";
    public static final String ACTUALIZACION_CONTROLCONFIGURACION = "ControlConfiguracion";
    public static final String ACTUALIZACION_TOTALPROVEEDOR = "TotalProveedor";
    public static final String ACTUALIZACION_TOTALPRODUCTOS = "TotalProductos";
    public static final String ACTUALIZACION_TOTALCONFIGURACION = "TotalConfiguracion";
    public static final String ACTUALIZACION_IDIOMA = "Idioma";

    public static final String CONFIGURACION_ID_CONFIGURACION = "Id_Configuracion";
    public static final String CONFIGURACION_TIPO = "Tipo";
    public static final String CONFIGURACION_VALOR = "Valor";

    public static final String CATEGORIA_ID_CATEGORIA = "Id_Categoria";
    public static final String CATEGORIA_DESCRIPCION = "Descripcion";

    public static final String PROVEEDOR_ID_PROVEEDOR = "Id_Proveedor";
    public static final String PROVEEDOR_NOMBRE = "Nombre";
    public static final String PROVEEDOR_PRESENTACION = "Presentacion";
    public static final String PROVEEDOR_PRESENTACIONCORTA = "PresentacionCorta";
    public static final String PROVEEDOR_SERVICIO1 = "Servicio1";
    public static final String PROVEEDOR_SERVICIO2 = "Servicio2";
    public static final String PROVEEDOR_SERVICIO3 = "Servicio3";
    public static final String PROVEEDOR_TELEFONO1 = "Telefono1";
    public static final String PROVEEDOR_TELEFONO2 = "Telefono2";
    public static final String PROVEEDOR_TWITTER = "Twitter";
    public static final String PROVEEDOR_FACEBOOK = "Facebook";
    public static final String PROVEEDOR_SITIOWEB = "SitioWeb";
    public static final String PROVEEDOR_MAIL = "Mail";
    public static final String PROVEEDOR_CALLE = "Calle";
    public static final String PROVEEDOR_NUMEROEXTERIOR = "NumeroExterior";
    public static final String PROVEEDOR_NUMEROINTERIOR = "NumeroInterior";
    public static final String PROVEEDOR_COLONIA = "Colonia";
    public static final String PROVEEDOR_MUNICIPIO = "Municipio";
    public static final String PROVEEDOR_ESTADO = "Estado";
    public static final String PROVEEDOR_PAIS = "Pais";
    public static final String PROVEEDOR_FOTO = "Foto";
    public static final String PROVEEDOR_CLAVEBUSQUEDA = "ClaveBusqueda";
    public static final String PROVEEDOR_VIGENCIA = "Vigencia";
    public static final String PROVEEDOR_ACTIVO = "Activo";
    public static final String PROVEEDOR_ORDEN = "Orden";

    public static final String PRODUCTO_ID_PRODUCTO = "Id_Producto";
    public static final String PRODUCTO_ID_PROVEEDOR = "Id_Proveedor";
    public static final String PRODUCTO_NOMBRE = "Nombre";
    public static final String PRODUCTO_DESCRIPCIONCORTA = "DescripcionCorta";
    public static final String PRODUCTO_DESCRIPCION = "Descripcion";
    public static final String PRODUCTO_PRECIOMENUDEO = "PrecioMenudeo";
    public static final String PRODUCTO_PRECIOMAYOREO = "PrecioMayoreo";
    public static final String PRODUCTO_FOTO1 = "Foto1";
    public static final String PRODUCTO_FOTO2 = "Foto2";
    public static final String PRODUCTO_FOTO3 = "Foto3";
    public static final String PRODUCTO_VIGENCIA = "Vigencia";
    public static final String PRODUCTO_ACTIVO = "Activo";
    public static final String PRODUCTO_ORDEN = "Orden";

    public static final String SUBCATEGORIA_ID_SUBCATEGORIA = "Id_SubCategoria";
    public static final String SUBCATEGORIA_DESCRIPCION = "Descripcion";
    public static final String SUBCATEGORIA_ID_CATEGORIA = "Id_Categoria";

    public static final String PROVEEDORSUBCATEGORIA_ID_PROVEEDOR = "Id_Proveedor";
    public static final String PROVEEDORSUBCATEGORIA_ID_SUBCATEGORIA = "Id_SubCategoria";

    // información del a base de datos
    static final String DB_NAME = "HNL";
    static final int DB_VERSION = 2;

    // Información de la base de datos
    private static final String CREATE_ACTUALIZACION = "CREATE TABLE [Actualizacion] ([Id_Actualizacion] INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, [Fecha_Actualizacion] DATETIME, [ControlProveedor] INTEGER, [ControlProductos] INTEGER, [ControlConfiguracion] INTEGER, [TotalProveedor] INTEGER, [TotalProductos] INTEGER, [TotalConfiguracion] INTEGER, [Idioma] VARCHAR (30))";
    private static final String CREATE_CONFIGURACION = "CREATE TABLE [Configuracion] ([Id_Configuracion] INTEGER PRIMARY KEY NOT NULL UNIQUE, [Tipo] INTEGER, [Valor] VARCHAR (255))";
    private static final String CREATE_CATEGORIA = "CREATE TABLE [Categoria] ([Id_Categoria] INTEGER PRIMARY KEY NOT NULL, [Descripcion] VARCHAR (255) NOT NULL)";
    private static final String CREATE_PROVEEDORES = "CREATE TABLE [Proveedores] ([Id_Proveedor] INTEGER PRIMARY KEY NOT NULL UNIQUE, [Nombre] VARCHAR (20) NOT NULL, [Presentacion] VARCHAR (255), [PresentacionCorta] VARCHAR (50), [Servicio1] VARCHAR (50), [Servicio2] VARCHAR (50), [Servicio3] VARCHAR (50), [Telefono1] VARCHAR (14), [Telefono2] VARCHAR (14), [Twitter] VARCHAR (255), [Facebook] VARCHAR (255), [SitioWeb] VARCHAR (255), [Mail] VARCHAR (255), [Calle] VARCHAR (255), [NumeroExterior] VARCHAR (10), [NumeroInterior] VARCHAR (10), [Colonia] VARCHAR (255), [Municipio] VARCHAR (100), [Estado] VARCHAR (50), [Pais] VARCHAR (50), [Foto] VARCHAR (255), [ClaveBusqueda] VARCHAR (255), [Vigencia] DATETIME, [Activo] BOOLEAN DEFAULT(1), [Orden] INTEGER)";
    private static final String CREATE_SUBCATEGORIA = "CREATE TABLE [SubCategoria] ([Id_SubCategoria] INTEGER PRIMARY KEY NOT NULL UNIQUE, [Descripcion] VARCHAR (255) NOT NULL, [Id_Categoria] INTEGER REFERENCES [Categoria] ([Id_Categoria]))";
    private static final String CREATE_PRODUCTOS = "CREATE TABLE [Productos] ([Id_Producto] INTEGER  NOT NULL , [Id_Proveedor] INTEGER NOT NULL REFERENCES [Proveedores] ([Id_Proveedor]), [Nombre] VARCHAR (50), [DescripcionCorta] VARCHAR (50), [Descripcion] VARCHAR (255), [PrecioMenudeo] INTEGER, [PrecioMayoreo] INTEGER, [Foto1] VARCHAR (255), [Foto2] VARCHAR (255), [Foto3] VARCHAR (255), [Vigencia] DATETIME, [Activo] BOOLEAN, [Orden] INTEGER, PRIMARY KEY ([Id_Producto], [Id_Proveedor]))";
    private static final String CREATE_PROVCATEGORIA = "CREATE TABLE [ProveedorCategoria] ([Id_Proveedor] INTEGER NOT NULL UNIQUE REFERENCES [Proveedores] ([Id_Proveedor]),[Id_SubCategoria] INTEGER NOT NULL UNIQUE REFERENCES [SubCategoria] ([Id_SubCategoria]), PRIMARY KEY ([Id_Proveedor],[Id_SubCategoria]))";

    public DBhelper(Context context) {
        super(context, DB_NAME, null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ACTUALIZACION);
        db.execSQL(CREATE_CONFIGURACION);
        db.execSQL(CREATE_CATEGORIA);
        db.execSQL(CREATE_PROVEEDORES);
        db.execSQL(CREATE_SUBCATEGORIA);
        db.execSQL(CREATE_PRODUCTOS);
        db.execSQL(CREATE_PROVCATEGORIA);
        WShelper.InsertConfiguracion(db);
     }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROVEEDORSUBCATEGORIA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBCATEGORIA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROVEEDORES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONFIGURACION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTUALIZACION);
        onCreate(db);
    }
}