package mx.peta.mod4ejercicio1.utileria;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by rayo on 6/14/16.
 */
/*
   Rutina de utileria para enviar mensajes a los usuarios
 */
public class SystemMsg {
    public static void msg(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
