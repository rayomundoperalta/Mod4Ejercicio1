package mx.peta.mod4ejercicio1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.concurrent.TimeUnit;

import mx.peta.mod4ejercicio1.utileria.SystemMsg;

/* Esta basado en el programa que se hizó en clase, ya que el ejercicio es muy
   parecido
 */
public class Activity_Login extends AppCompatActivity implements View.OnClickListener {
    private EditText wUser;
    private EditText wPassword;
    private View     wLoading;

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
        wLoading.setVisibility(View.VISIBLE);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            SystemMsg.msg(getApplicationContext(), "The Clock has been interrupted");
        }
        wLoading.setVisibility(View.GONE);
        if (user.equals("user1") && pass.equals("pass1")) {
            SystemMsg.msg(getApplicationContext(), "Login ok");
            Intent intent = new Intent(getApplicationContext(), Activity_Fragmentos.class);
            intent.putExtra("usuario", user);
            startActivity(intent);
        } else
            SystemMsg.msg(getApplicationContext(), "User not known");
    }
}
