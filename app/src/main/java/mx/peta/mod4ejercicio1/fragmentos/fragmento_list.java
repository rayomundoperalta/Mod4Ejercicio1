package mx.peta.mod4ejercicio1.fragmentos;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mx.peta.mod4ejercicio1.Activity_Lista;
import mx.peta.mod4ejercicio1.R;
import mx.peta.mod4ejercicio1.adapters.AdapterItemList;
import mx.peta.mod4ejercicio1.model.ModelItem;
import mx.peta.mod4ejercicio1.utileria.SystemMsg;

/**
 * Created by rayo on 6/15/16.
 */
public class Fragmento_List extends Fragment {
    private ListView listView;
    private List<ModelItem> array = new ArrayList<>();
    private int counter;
    int min = 1;
    int max = 4;
    Random r = new Random();

    /*  Como generar numeros al azar
        int min = 65;
        int max = 80;

        Random r = new Random();
        int i1 = r.nextInt(max - min + 1) + min;
     */

    /* Esta función se ejecuta cuando el fragmento se crea */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /* aqui decimos que es lo que va en el fragmento */
        View view = inflater.inflate(R.layout.fragmento_lista, container, false);
        listView = (ListView) view.findViewById(R.id.fragmento_list_listItems);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /* esta rutina manejadora de eventos se llama cuando se da un tap en la lista de
               descripciones, es aqui en donde se tiene que llamar a la actividad que va a mostrar
               el detalle.
             */
            //@TargetApi(23)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AdapterItemList adapter = (AdapterItemList) parent.getAdapter();
                ModelItem modelItem  = adapter.getItem(position);
                ModelItem modelItem2 = array.get(position);
                /* llamamos a la tercer activity y le pasamos como parametrp un ModelItem */
                Intent intent = new Intent(getActivity().getApplicationContext(), Activity_Lista.class);
                intent.putExtra("ModelItem_id", modelItem2.id);
                intent.putExtra("ModelItem_item", modelItem2.item);
                intent.putExtra("ModelItem_resourceId", modelItem2.resourceId);
                startActivity(intent);
            }
        });

        /* Esta sección maneja el campo de texto y el boton usados para llenar la lista, se coloca
           una imagen aleatoria en la lista
         */
        final EditText mItemsText = (EditText) view.findViewById(R.id.fragmento_list_itemText);

        view.findViewById(R.id.fragmento_list_btnAddItem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemData = mItemsText.getText().toString(); // el texto capturado

                if (!TextUtils.isEmpty(itemData)) {
                    ModelItem item = new ModelItem();
                    item.item = itemData;
                    item.id = "Description " + counter;
                    /* aqui asignamos aleatoriamente la imagen que se muestra */
                    switch(r.nextInt(max - min + 1) + min ) {
                        case 1:
                            item.resourceId = R.drawable.cake;
                            break;
                        case 2:
                            item.resourceId = R.drawable.man;
                            break;
                        case 3:
                            item.resourceId = R.drawable.radio;
                            break;
                        case 4:
                            item.resourceId = R.drawable.whatshot;
                            break;
                    }
                    array.add(item);
                    listView.setAdapter(new AdapterItemList(getActivity(), array));
                    counter++;
                    mItemsText.setText("");
                }
            }
        });
        return view;
    }
}
