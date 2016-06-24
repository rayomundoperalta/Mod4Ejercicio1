package mx.peta.mod4ejercicio1;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

import mx.peta.mod4ejercicio1.SQL.DataSource;
import mx.peta.mod4ejercicio1.service.ManejoTiempoDeVida;
import mx.peta.mod4ejercicio1.service.ServiceTimer;
import mx.peta.mod4ejercicio1.utileria.Preferences;
import mx.peta.mod4ejercicio1.utileria.SystemMsg;

/* Esta basado en el programa que se hizó en clase, ya que el ejercicio es muy
   parecido

   El segundo ejercicio se va a hacer sobre el codigo del primero.

   Para implementar que el usuario sea recordado la ultima vez que se hizó un logón correcto
   se puede insertar el codigo antes de llamar a la actividad que controla los fragmentos
 */
public class Activity_Login extends AppCompatActivity implements View.OnClickListener {
    private static final String USER_ID    = "userID";
    private static final String FECHA_HORA = "fechaHora";
    private EditText wUser;
    private EditText wPassword;
    private View     wLoading;
    private CheckBox wRecordarLogin;
    private TextView wUltimoLogin;
    private TextView wTextUltimoLogin;
    private TextView tdv;
    private String[] usuarios = new String[4];
    private String[] claves   = new String[4];

    DataSource ds;
    /* la inicialización la provee el IDE */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(ServiceTimer.SERVICIO_TIMER, "activity login onCreate ---------------------------------------------------");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ds = new DataSource(getApplicationContext());
                /* inicializamos los widgets para poder usarlos en la programación */
        wUser     = (EditText) findViewById(R.id.login_user);
        wPassword = (EditText) findViewById(R.id.login_password);
        wRecordarLogin = (CheckBox) findViewById(R.id.recordarLogin);
        /* inicializamos el manejador de eventos del boton para enterarnos cuando sea oprimido */
        /* es necesario recordar que esta clase implementa el manejador de eventos */
        findViewById(R.id.login_btnlogin).setOnClickListener(this);
        findViewById(R.id.btnRegisterLogin).setOnClickListener(this);
        findViewById(R.id.login_btnEnd).setOnClickListener(this);
        wLoading = findViewById(R.id.login_progressbar);
        wUltimoLogin = (TextView) findViewById(R.id.fechaUltimoLogin);
        wTextUltimoLogin = (TextView) findViewById(R.id.campoUltimmoLogin);
        tdv = (TextView) findViewById(R.id.tdv);
        wUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                wTextUltimoLogin.setVisibility(View.INVISIBLE);
                wUltimoLogin.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Preferences p = new Preferences(getApplicationContext());
        /* mostramos el último login válido */
        String user      = p.getStringItem(USER_ID);
        String fechaHora = p.getStringItem(FECHA_HORA);
        wUser.setText(user != null ? user : "");
        wUltimoLogin.setText(user != null ? fechaHora : "");
        if (user == null) {
            wTextUltimoLogin.setVisibility(View.INVISIBLE);
            wUltimoLogin.setVisibility(View.INVISIBLE);
        } else {
            wTextUltimoLogin.setVisibility(View.VISIBLE);
            wUltimoLogin.setVisibility(View.VISIBLE);
        }
        // el password siempre va en blanco
        wPassword.setText("");
        long lTdv = p.getLong(ManejoTiempoDeVida.TIEMPO_DE_VIDA);
        lTdv = lTdv == -1 ? 0 : lTdv;
        tdv.setText(String.valueOf(lTdv) + " " + getString(R.string.segundos));
        Log.d(ServiceTimer.SERVICIO_TIMER, " activity login on resume");
    }

    /* Este es el manejador de los eventos onClick, sera llamado por todos los widgets que
       produzcan onClick, por lo tanto dentro del manejador es necesario identificar quien
       lo está llamando
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btnlogin:
                processLogin(Activity_Fragmentos.class);
                break;
            case R.id.btnRegisterLogin:
                processLogin(Activity_Registro.class);
                break;
            case R.id.login_btnEnd:
                //finish();
                int p = android.os.Process.myPid();
                android.os.Process.killProcess(p);
                break;
        }
    }

    /* Esta rutina procesa el evento onClick del widget login_btnlogin */
    private void processLogin(Class<?> cls) {
        final String user = wUser.getText().toString();
        final String pass = wPassword.getText().toString();

        String dbPassword = ds.getPassword(user);
        if (dbPassword != null) {           // vemos si el usuario esta registrado
            if (dbPassword.equals(pass)) {  // si afirmativo el password es correcto
                /*
                    Por la forma como se van a hacer las verificaciones en este punto
                    el usuario y el password no son nulos y tienen valores validos
                    por lo tanto los podemos guardar en las preferencias como el último
                    login valido
                    Primero traemos la fecha y la hora del login
                    despuesto recordamos la información
                */
                SimpleDateFormat ff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String fechaHora = ff.format(new Date());
                Preferences p = new Preferences(getApplicationContext());
                if (wRecordarLogin.isChecked()) {
                    p.saveStringItem(USER_ID, user); // por seguridad solo se guarda el usuario
                    p.saveStringItem(FECHA_HORA, fechaHora); // fecha y hora del ultimo login
                } else
                    p.saveStringItem(USER_ID, ""); // esto es una desición de diseño
                Intent intent = new Intent(getApplicationContext(), cls);
                intent.putExtra("usuario", user);
                startActivity(intent);
                startService(new Intent(getApplicationContext(), ServiceTimer.class));
            } else {
                SystemMsg.msg(getApplicationContext(), getString(R.string.passwordInvalido));
            }
        } else {
            SystemMsg.msg(getApplicationContext(), getString(R.string.usuarioNoRegistrado));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(ServiceTimer.SERVICIO_TIMER, " activity login on pause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(ServiceTimer.SERVICIO_TIMER, " activity login on destroy");
    }
}
