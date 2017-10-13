package edu.ckcc.schoolguide.Activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import edu.ckcc.schoolguide.R;

/**
 * Created by Computer on 10/14/2017.
 */

public class HomeAdapter extends AppCompatActivity{
    GridView gridView;
    String [] data = {"data1", "data2,data3"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        gridView =(GridView) findViewById(R.id.gridview);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
        gridView.setAdapter(adapter);
    }
}
