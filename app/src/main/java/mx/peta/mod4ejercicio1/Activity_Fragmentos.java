package mx.peta.mod4ejercicio1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import mx.peta.mod4ejercicio1.fragmentos.Fragmento_List;
import mx.peta.mod4ejercicio1.fragmentos.Fragmento_Perfil;
import mx.peta.mod4ejercicio1.service.ManejoTiempoDeVida;
import mx.peta.mod4ejercicio1.service.ServiceTimer;
import mx.peta.mod4ejercicio1.utileria.Preferences;

/* Esta actividad contiene los dos fragmentos que hay que implementar */
public class Activity_Fragmentos extends AppCompatActivity implements View.OnClickListener {
    String user;
    private TextView txtTimer;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int counter = intent.getExtras().getInt(ServiceTimer.SERVICIO_TIMER);
            txtTimer.setText(String.format(getString(R.string.duracionSesion), counter));
            segundosActivo++;
        }
    };

    long segundosActivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(ServiceTimer.SERVICIO_TIMER, "activity fragmentos onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragmentos);
        user = getIntent().getExtras().getString("usuario");
        findViewById(R.id.fragmentos_btnfragmentos1).setOnClickListener(this);
        findViewById(R.id.fragmentos_btnfragmentos2).setOnClickListener(this);
        findViewById(R.id.btnLogOut).setOnClickListener(this);
        txtTimer = (TextView) findViewById(R.id.txtTimer);
    }

    /* cada vez que se da un tap en algun boton se llama esta rutina */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragmentos_btnfragmentos1:
                processFragmento1();
                break;
            case R.id.fragmentos_btnfragmentos2:
                processFragmento2();
                break;
            case R.id.btnLogOut:
                finish();
                break;
        }
    }

    private void processFragmento1() {
        /* Aqui estamos diciendo en donde se va a mostrar el frame */
        getFragmentManager().beginTransaction().replace(R.id.fragmentos_frmlayout1, Fragmento_Perfil.newInstance(user)).commit();
    }

    private void processFragmento2() {
        /* Aqui decimos que se va a mostrar en el frame */
        Fragmento_List f = new Fragmento_List();
        getFragmentManager().beginTransaction().replace(R.id.fragmentos_frmlayout2, f).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ServiceTimer.ACTION_SEND_TIMER);
        registerReceiver(broadcastReceiver, filter);
        segundosActivo = 0;   // inicializamos el contador de segundos activo
        Log.d(ServiceTimer.SERVICIO_TIMER, " activity fragment on resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
        /*
            debemos sumar al registro de preferencias los segundos que estuvimos activos
         */

        Preferences p = new Preferences(getApplicationContext());
        long tdv = p.getLong(ManejoTiempoDeVida.TIEMPO_DE_VIDA);
        if (tdv == -1)
            p.saveLongItem(ManejoTiempoDeVida.TIEMPO_DE_VIDA, segundosActivo);
        else {
            tdv += segundosActivo;
            p.saveLongItem(ManejoTiempoDeVida.TIEMPO_DE_VIDA, tdv);
        }
        Log.d(ServiceTimer.SERVICIO_TIMER, " activity fragment on pause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(getApplicationContext(), ServiceTimer.class));
        Log.d(ServiceTimer.SERVICIO_TIMER, " activity fragment on destroy");
    }
}
