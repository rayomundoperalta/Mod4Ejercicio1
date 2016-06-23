package mx.peta.mod4ejercicio1.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by rayo on 6/22/16.
 */
public class ServiceTimer extends Service {
    public static final String ACTION_SEND_TIMER = "mx.peta.SEND_TIMER";
    public static final String SERVICIO_TIMER     = "Timer";
    int counter;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            counter++;
            handler.postDelayed(runnable, 1000);
            Intent i = new Intent(ACTION_SEND_TIMER);
            i.putExtra(SERVICIO_TIMER, counter);
            Log.d(SERVICIO_TIMER, " tick ");
            sendBroadcast(i);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        handler.post(runnable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}
