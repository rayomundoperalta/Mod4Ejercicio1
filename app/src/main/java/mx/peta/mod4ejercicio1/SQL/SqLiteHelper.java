package mx.peta.mod4ejercicio1.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by rayo on 6/21/16.
 */
public class SqLiteHelper  extends SQLiteOpenHelper {
    private final static String USERS_DATABASE_NAME  = "ejercicio2_users_db";
    private final static int USERS_DATABASE_VERSION  = 1;
    public static final String USERS_TABLE_NAME      = "users_table";
    public static final String USERS_COLUMN_ID       = BaseColumns._ID;
    public static final String USERS_COLUMN_USER     = "user";
    public static final String USERS_COLUMN_PASSWORD = "password";

    private static final String CREATE_USERS_TABLE   = "create table " + USERS_TABLE_NAME + "(" +
            USERS_COLUMN_ID + " integer primary key autoincrement, " +
            USERS_COLUMN_USER + " text not null, " +
            USERS_COLUMN_PASSWORD + " text not null)";

    public SqLiteHelper(Context context) {
        super(context, USERS_DATABASE_NAME, null, USERS_DATABASE_VERSION);
    }

    /*
        Cuando se crea la BD se inserta el registro del administrador para que haya un primer
        usuario en el sistema para que se pueda insertar a los otros usuarios el usuario del
        dispositivo tiene que conocer el user/password del sistema para poder entrar
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERS_TABLE);
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(USERS_COLUMN_USER, "Admin");
            values.put(USERS_COLUMN_PASSWORD, "password");
            db.insert(USERS_TABLE_NAME, null, values);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
