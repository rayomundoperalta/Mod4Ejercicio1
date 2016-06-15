package mx.peta.mod4ejercicio1.fragmentos;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mx.peta.mod4ejercicio1.R;

/**
 * Created by rayo on 6/15/16.
 */
public class Fragmento_List extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_lista, container, false);
        return view;
    }
}
