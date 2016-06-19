package mx.peta.mod4ejercicio1.fragmentos;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import mx.peta.mod4ejercicio1.R;

/**
 * Created by rayo on 6/15/16.
 */
public class Fragmento_Perfil extends Fragment {
    private ImageView imgPerfil;
    // final private char charDeCorte = 'n';

    /* En esta rutina ademas de crear el fragmento se e agregan los parametros que se desea
       pasarle, el fragmento no hereda parametros de parte de la actividad, es necesario
       pasarlos....
     */
    public static Fragmento_Perfil newInstance(String name) {
        Fragmento_Perfil f = new Fragmento_Perfil();
        Bundle b = new Bundle();
        b.putString("usuario", name);
        f.setArguments(b);
        return  f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /* aqui estamos diciendo que es lo que se va a mostrar en el fragment */
        View view = inflater.inflate(R.layout.fragmento_perfil, container, false);
        imgPerfil = (ImageView) view.findViewById(R.id.fragmento_perfil_image);
        TextView texto = (TextView) view.findViewById(R.id.fragmento_perfil_txtview);
        Bundle bundle = getArguments();
        String user;
        if (bundle != null)
            user = bundle.getString("usuario");
        else
            user = "XML Inflate";
        texto.setText(user);
        if ((('a' <= user.charAt(0)) && (user.charAt(0) < 'n')) || (('A' <= user.charAt(0)) && (user.charAt(0) < 'N')))
            imgPerfil.setImageResource(R.drawable.man);
        else
            imgPerfil.setImageResource(R.drawable.radio);
        return view;
    }
}
