package mx.peta.mod4ejercicio1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import mx.peta.mod4ejercicio1.SQL.DataSource;
import mx.peta.mod4ejercicio1.utileria.SystemMsg;

/**
 * Created by rayo on 6/21/16.
 */
public class Activity_Registro extends AppCompatActivity {
    DataSource ds;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        ds = new DataSource(getApplicationContext());
        final EditText mUser = (EditText) findViewById(R.id.mUserRegister);
        final EditText mPassword = (EditText) findViewById(R.id.mPasswordRegister);
        findViewById(R.id.btnRegisterUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mUserName = mUser.getText().toString();
                String password= mPassword.getText().toString();
                /*
                    Vamos a escribir los usuarios que se den de alta a la base de datos de
                    usuario para poder hacer la validación de usuarios al momento de login
                    Al insertar un nuevo usuario hay que verificar que no haya sido dado de
                    alta con anterioridad, para lo cual crecaremos primero si el usuario
                    tiene un password, si no hay password insertaremos al usuario
                 */

                if (ds.getPassword(mUserName) == null)
                    ds.writeUser(mUserName, password);
                else
                    SystemMsg.msg(getApplicationContext(),getString(R.string.usuarioYaExiste));
                // En cualquier caso borramos los campos de captura
                mUser.setText("");
                mPassword.setText("");
            }
        });
        findViewById(R.id.btnFinish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

