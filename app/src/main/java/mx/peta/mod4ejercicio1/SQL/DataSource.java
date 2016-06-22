package mx.peta.mod4ejercicio1.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by rayo on 6/21/16.
 */
public class DataSource {
    private final SQLiteDatabase db;

    public DataSource(Context context) {
        SqLiteHelper helper = new SqLiteHelper(context);
        db = helper.getWritableDatabase();
    }

    public void writeUser(String user, String password) {
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(SqLiteHelper.USERS_COLUMN_USER, user);
            values.put(SqLiteHelper.USERS_COLUMN_PASSWORD, password);
            db.insert(SqLiteHelper.USERS_TABLE_NAME, null, values);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    /*
        Vamos a recuperar el password de un usuario que esta dado de alta en la base de datos
        si no se encuentra al usuario hay que regresar null
        El query debe ser: select password from table where User = user
     */
    public String getPassword(String user) {
        String QUERY_PASSWORD = "select " + SqLiteHelper.USERS_COLUMN_PASSWORD +
                " from " + SqLiteHelper.USERS_TABLE_NAME + " where " + SqLiteHelper.USERS_COLUMN_USER +
                " = ?";
        // rawQuery("SELECT id, name FROM people WHERE name = ? AND id = ?", new String[] {"David", "2"});
        Cursor cursor = db.rawQuery(QUERY_PASSWORD, new String[] {user});
        if (cursor.moveToFirst())
            return cursor.getString(cursor.getColumnIndexOrThrow(SqLiteHelper.USERS_COLUMN_PASSWORD));
        else
            return null;
    }
}
