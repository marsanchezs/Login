package OpenHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLite_OpenHelper extends SQLiteOpenHelper {
    public SQLite_OpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "create table usuarios (_ID integer primary key autoincrement, Nombre text, Correo text, Password text);";
        String query2 = "create table productos (_ID integer primary key autoincrement, Nombre text, Descripcion text, Precio INTEGER, Longitud INTEGER, Latitud INTEGER, Imagen BLOB, created_at DATETIME DEFAULT CURRENT_TIMESTAMP);";
        sqLiteDatabase.execSQL(query);
        sqLiteDatabase.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public void abrirBD(){
        //Permite abrir la BBDD para escribir.
        this.getWritableDatabase();
    }

    public void cerrarBD(){
        //Cierra la BBDD.
        this.close();
    }

    public void insertarRegistrosUsuarios(String name, String email, String pass){
        //Inserta valores en la tabla usuarios.
        ContentValues valores = new ContentValues();
        valores.put("Nombre", name); //Almacena los parametros dentro del ContentValues valores.
        valores.put("Correo", email);
        valores.put("Password", pass);

        this.getWritableDatabase().insert("usuarios", null, valores);

    }

    public Cursor validarUsuario(String user, String pass) throws SQLException{
        //Valida si el usuario existe.
        Cursor micursor = null;
        micursor = this.getReadableDatabase().query("usuarios", new String[]{"_ID",
                "Nombre", "Correo", "Password"}, "Correo like '"+user+"' " +
                "and Password like '"+pass+"' ", null, null, null, null);

        return micursor;
    }

    public void insertarProducto(String nameProducto, String descripcion, int precio, double longitud, double latitud, byte [] blob){
        //Inserta valores en la tabla productos.
        ContentValues valoresProd = new ContentValues();
        valoresProd.put("Nombre", nameProducto); //Almacena los parametros dentro del ContentValues valores.
        valoresProd.put("Descripcion", descripcion);
        valoresProd.put("Precio", precio);
        valoresProd.put("Longitud", longitud);
        valoresProd.put("Latitud", latitud);
        valoresProd.put("Imagen", blob);

        this.getWritableDatabase().insert("productos", null, valoresProd);

    }
}

