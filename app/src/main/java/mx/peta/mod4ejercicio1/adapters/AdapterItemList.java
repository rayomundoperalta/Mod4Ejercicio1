package mx.peta.mod4ejercicio1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import mx.peta.mod4ejercicio1.R;
import mx.peta.mod4ejercicio1.model.ModelItem;

/**
 * Created by rayo on 6/15/16.
 */
public class AdapterItemList extends ArrayAdapter<ModelItem> {
    public AdapterItemList(Context context, List<ModelItem> objects) {
        super(context, 0, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list, parent, false);
        }
        TextView txtItemDescription = (TextView) convertView.findViewById(R.id.txtItemDescription);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.txtItemTitle);
        ImageView img = (ImageView) convertView.findViewById(R.id.row_image_view);

        ModelItem modelItem = getItem(position);
        txtTitle.setText(modelItem.item);
        txtItemDescription.setText(modelItem.id);
        img.setImageResource(modelItem.resourceId);
        return convertView;
    }
}
