package com.example.quanlychitieuapp.Advancedcontrols;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SlidingDrawer;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quanlychitieuapp.R;

public class Slidingdrawer extends AppCompatActivity {

    String[] nameArray = {"Aestro", "Blender", "Cupcake", "Donut", "Eclair", "Froyo", "GingerBread", "HoneyComb", "IceCream Sandwich", "JelliBean", "KitKat", "Lollipop", "MarshMallow"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_slidingdrawer);

        SlidingDrawer simpleSlidingDrawer = (SlidingDrawer) findViewById(R.id.simpleSlidingDrawer);
        final Button handleButton = (Button) findViewById(R.id.handle);
        ListView simpleListView = (ListView) findViewById(R.id.simpleListView);

        //adapter for list view
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_item_sliding, R.id.name_item, nameArray);
        // set an adapter to fill the data in the ListView
        simpleListView.setAdapter(arrayAdapter);


        //implement setOnDrawerOpenListener event
        simpleSlidingDrawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
            @Override
            public void onDrawerOpened() {
                handleButton.setText("Close");
            }
        });
        // implement setOnDrawerCloseListener event
        simpleSlidingDrawer.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
            @Override
            public void onDrawerClosed() {
                // change the handle button text
                handleButton.setText("Open");
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}