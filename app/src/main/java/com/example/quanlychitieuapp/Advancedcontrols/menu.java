package com.example.quanlychitieuapp.Advancedcontrols;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quanlychitieuapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class menu extends AppCompatActivity {
    private ListView lstLanguage;
    private ArrayAdapter<String> adapter;
    String[] languages;
    List<String> languages_list;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);
        lstLanguage = (ListView) findViewById(R.id.lstLanguage);
        languages = getResources().getStringArray(R.array.languages);
        languages_list = new ArrayList<String>(Arrays.asList(languages));
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, languages_list);
        lstLanguage.setAdapter(adapter);
        registerForContextMenu(lstLanguage);

        btnBack = (Button) findViewById(R.id.btnback);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        // TODO Auto-generated method stub
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        int pos = info.position;
        String i = adapter.getItem(pos);

        int item1 = item.getItemId();

        if (item1 == R.id.delId) {
            languages_list.remove(pos);
            adapter.notifyDataSetChanged();
            return true;
        } else if (item1 == R.id.upId) {
            String upln = i.toUpperCase();
            languages_list.set(pos, upln);
            adapter.notifyDataSetChanged();
            return true;
        } else if (item1 == R.id.lowId) {
            String lpln = i.toLowerCase();
            languages_list.set(pos, lpln);
            adapter.notifyDataSetChanged();
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
    }


}