package mx.peta.mod4ejercicio1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import mx.peta.mod4ejercicio1.service.ManejoTiempoDeVida;
import mx.peta.mod4ejercicio1.service.ServiceTimer;
import mx.peta.mod4ejercicio1.utileria.Preferences;

/**
 * Created by rayo on 6/15/16.
 */
public class Activity_Lista extends AppCompatActivity {

    long segundosActivo;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            segundosActivo++;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(ServiceTimer.SERVICIO_TIMER, "activity lista onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.row_list2);
        String modelItemId      = getIntent().getExtras().getString("ModelItem_id");
        String modelItemItem    = getIntent().getExtras().getString("ModelItem_item");
        int modelItemResourceId = getIntent().getExtras().getInt("ModelItem_resourceId");
        ImageView img = (ImageView) findViewById(R.id.row_image_view);
        TextView txtTitle = (TextView) findViewById(R.id.txtItemTitle);
        TextView txtItemDescription = (TextView) findViewById(R.id.txtItemDescription);
        txtTitle.setText(modelItemItem);
        txtItemDescription.setText(modelItemId);
        img.setImageResource(modelItemResourceId);
        //int p = android.os.Process.myPid();
        //android.os.Process.killProcess(p);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ServiceTimer.ACTION_SEND_TIMER);
        registerReceiver(broadcastReceiver, filter);
        segundosActivo = 0;
        Log.d(ServiceTimer.SERVICIO_TIMER, " activity Lista onResume");
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
        Log.d(ServiceTimer.SERVICIO_TIMER, " activity Lista onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(ServiceTimer.SERVICIO_TIMER, " activity Lista onDestroy");
    }
}
