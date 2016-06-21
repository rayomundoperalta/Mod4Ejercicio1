package mx.peta.mod4ejercicio1;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import mx.peta.mod4ejercicio1.utileria.Preferences;
import mx.peta.mod4ejercicio1.utileria.SystemMsg;

/* Esta basado en el programa que se hizó en clase, ya que el ejercicio es muy
   parecido

   El segundo ejercicio se va a hacer sobre el codigo del primero.

   Para implementar que el usuario sea recordado la ultima vez que se hizó un logón correcto
   se puede insertar el codigo antes de llamar a la actividad que controla los fragmentos
 */
public class Activity_Login extends AppCompatActivity implements View.OnClickListener {
    private static final String USER_ID = "userID";
    private EditText wUser;
    private EditText wPassword;
    private View     wLoading;
    private CheckBox wRecordarLogin;
    private String[] usuarios = new String[4];
    private String[] claves   = new String[4];

    /* la inicialización la provee el IDE */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
                /* inicializamos los widgets para poder usarlos en la programación */
        wUser     = (EditText) findViewById(R.id.login_user);
        wPassword = (EditText) findViewById(R.id.login_password);
        wRecordarLogin = (CheckBox) findViewById(R.id.recordarLogin);
        /* inicializamos el manejador de eventos del boton para enterarnos cuando sea oprimido */
        /* es necesario recordar que esta clase implementa el manejador de eventos */
        findViewById(R.id.login_btnlogin).setOnClickListener(this);
        wLoading = findViewById(R.id.login_progressbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Preferences p = new Preferences(getApplicationContext());
        /* mostramos el último login válido */
        String user = p.getStringItem(USER_ID);
        wUser.setText(user != null ? user : "");
        wPassword.setText("");
    }

    /* Este es el manejador de los eventos onClick, sera llamado por todos los widgets que
       produzcan onClick, por lo tanto dentro del manejador es necesario identificar quien
       lo está llamando
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btnlogin:
                processData();
                break;
        }
    }

    /* Esta rutina procesa el evento onClick del widget login_btnlogin */
    private void processData() {
        final String user = wUser.getText().toString();
        final String pass = wPassword.getText().toString();

        /* Esta es la base de datos de usuarios */
        usuarios[0] = "aura"; claves[0] = "pass1";
        usuarios[1] = "rayo"; claves[1] = "pass2";
        usuarios[2] = "AURA"; claves[2] = "pass3";
        usuarios[3] = "RAYO"; claves[3] = "pass4";

        wLoading.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean loginOk = false;
                wLoading.setVisibility(View.GONE);
                for (int i = 0; i < 4; i++) {
                    if (user.equals(usuarios[i]) && pass.equals(claves[i])) {
                        /*
                            Por la forma como se van a hacer las verificaciones en este punto
                            el usuario y el password no son nulos y tienen valores validos
                            por lo tanto los podemos guardar en las preferencias como el último
                            login valido
                         */
                        Preferences p = new Preferences(getApplicationContext());
                        if (wRecordarLogin.isChecked())
                            p.saveStringItem(USER_ID, user); // por seguridad solo se guarda el usuario
                        else
                            p.saveStringItem(USER_ID, ""); // esto es una desición de diseño
                        SystemMsg.msg(getApplicationContext(), "Login Ok ");
                        loginOk = true;
                        Intent intent = new Intent(getApplicationContext(), Activity_Fragmentos.class);
                        intent.putExtra("usuario", user);
                        startActivity(intent);
                        break;
                    }
                }
                if (!loginOk)
                    SystemMsg.msg(getApplicationContext(), "Unkown user");
            }
        }, 1000);

    }
}
