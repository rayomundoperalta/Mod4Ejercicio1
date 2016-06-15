package mx.peta.mod4ejercicio1;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import mx.peta.mod4ejercicio1.utileria.SystemMsg;

/* Esta basado en el programa que se hizó en clase, ya que el ejercicio es muy
   parecido
 */
public class Activity_Login extends AppCompatActivity implements View.OnClickListener {
    private EditText wUser;
    private EditText wPassword;
    private View     wLoading;
    private String[] usuarios = new String[2];
    private String[] claves   = new String[2];

    /* la inicialización la provee el IDE */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        /* inicializamos los widgets para poder usarlos en la programación */
        wUser     = (EditText) findViewById(R.id.login_user);
        wPassword = (EditText) findViewById(R.id.login_password);
        /* inicializamos el manejador de eventos del boton para enterarnos cuando sea oprimido */
        /* es necesario recordar que esta clase implementa el manejador de eventos */
        findViewById(R.id.login_btnlogin).setOnClickListener(this);
        wLoading = findViewById(R.id.login_progressbar);
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

        usuarios[0] = "aura"; claves[0] = "pass1";
        usuarios[1] = "rayo"; claves[1] = "pass2";

        wLoading.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean loginOk = false;
                wLoading.setVisibility(View.GONE);
                for (int i = 0; i < 2; i++) {
                    if (user.equals(usuarios[i]) && pass.equals(claves[i])) {
                        SystemMsg.msg(getApplicationContext(), "Login Ok " + String.valueOf(user.charAt(0)));
                        loginOk = true;
                        Intent intent = new Intent(getApplicationContext(), Activity_Fragmentos.class);
                        intent.putExtra("usuario", user);
                        startActivity(intent);
                        break;
                    }
                }
                if (!loginOk)
                    SystemMsg.msg(getApplicationContext(), "User not known");
            }
        }, 1000);

    }
}
