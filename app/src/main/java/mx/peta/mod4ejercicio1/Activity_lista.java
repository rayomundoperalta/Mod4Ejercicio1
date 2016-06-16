package mx.peta.mod4ejercicio1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by rayo on 6/15/16.
 */
public class Activity_Lista extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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
    }
}
