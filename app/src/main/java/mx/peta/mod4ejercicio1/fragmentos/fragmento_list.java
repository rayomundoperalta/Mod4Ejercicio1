package mx.peta.mod4ejercicio1.fragmentos;

import android.app.Fragment;
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /* aqui decimos que es lo que va en el fragmento */
        View view = inflater.inflate(R.layout.fragmento_lista, container, false);
        listView = (ListView) view.findViewById(R.id.fragmento_list_listItems);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AdapterItemList adapter = (AdapterItemList) parent.getAdapter();
                ModelItem modelItem  = adapter.getItem(position);
                ModelItem modelItem2 = array.get(position);
                SystemMsg.msg(getActivity(), modelItem2.item);
            }
        });
        final EditText mItemsText = (EditText) view.findViewById(R.id.fragmento_list_itemText);
        view.findViewById(R.id.fragmento_list_btnAddItem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemData = mItemsText.getText().toString();
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
