package mx.peta.mod4ejercicio1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import mx.peta.mod4ejercicio1.fragmentos.Fragmento_List;
import mx.peta.mod4ejercicio1.fragmentos.Fragmento_Perfil;

/* Esta actividad contiene los dos fragmentos que hay que implementar */
public class Activity_Fragmentos extends AppCompatActivity implements View.OnClickListener {
    String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragmentos);
        user = getIntent().getExtras().getString("usuario");
        findViewById(R.id.fragmentos_btnfragmentos1).setOnClickListener(this);
        findViewById(R.id.fragmentos_btnfragmentos2).setOnClickListener(this);
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
}
