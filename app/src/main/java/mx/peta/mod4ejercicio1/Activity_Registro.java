package mx.peta.mod4ejercicio1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import mx.peta.mod4ejercicio1.SQL.DataSource;
import mx.peta.mod4ejercicio1.service.ManejoTiempoDeVida;
import mx.peta.mod4ejercicio1.service.ServiceTimer;
import mx.peta.mod4ejercicio1.utileria.Preferences;
import mx.peta.mod4ejercicio1.utileria.SystemMsg;

/**
 * Created by rayo on 6/21/16.
 */
public class Activity_Registro extends AppCompatActivity {
    DataSource ds;
    long segundosActivo;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            segundosActivo++;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(ServiceTimer.SERVICIO_TIMER, "activity Registro onCreate");
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
                if (mUserName.isEmpty()) {
                    SystemMsg.msg(getApplicationContext(), getString(R.string.passwordInvalido));
                } else {
                    if (password.isEmpty()){
                        SystemMsg.msg(getApplicationContext(), getString(R.string.passwordInvalido));
                    } else {
                        if (ds.getPassword(mUserName) == null)
                            ds.writeUser(mUserName, password);
                        else
                            SystemMsg.msg(getApplicationContext(),getString(R.string.usuarioYaExiste));
                    }
                }
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

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ServiceTimer.ACTION_SEND_TIMER);
        registerReceiver(broadcastReceiver, filter);
        segundosActivo = 0;
        Log.d(ServiceTimer.SERVICIO_TIMER, " activity registro onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
        Preferences p = new Preferences(getApplicationContext());
        long tdv = p.getLong(ManejoTiempoDeVida.TIEMPO_DE_VIDA);
        if (tdv == -1)
            p.saveLongItem(ManejoTiempoDeVida.TIEMPO_DE_VIDA, segundosActivo);
        else {
            tdv += segundosActivo;
            p.saveLongItem(ManejoTiempoDeVida.TIEMPO_DE_VIDA, tdv);
        }
        Log.d(ServiceTimer.SERVICIO_TIMER, " activity registro onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(getApplicationContext(), ServiceTimer.class));
        Log.d(ServiceTimer.SERVICIO_TIMER, " activity registro onDestroy");
    }
}

