package com.example.quanlychitieuapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class contentProvider extends Activity {
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private ContentResolver resolver;

    @SuppressLint("Range")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);

        ListView listView = findViewById(R.id.listView);
        resolver = getContentResolver();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    PERMISSIONS_REQUEST_READ_CONTACTS);
        } else {
            loadContacts(listView);
        }
    }

    @SuppressLint("Range")
    private void loadContacts(ListView listView) {
        List<String> list = new ArrayList<>();
        Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI,
                new String[]{ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.Contacts._ID},
                null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                long id = cursor.getLong(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                Cursor c = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[]{ContactsContract.
                                CommonDataKinds.Phone.NUMBER}, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? ",
                        new String[]{String.valueOf(id)}, null);
                String phone = "";
                while (c.moveToNext()) {
                    phone += c.getString(c.getColumnIndex(ContactsContract.
                            CommonDataKinds.Phone.NUMBER));
                }

                list.add(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)) + " (" + phone + ")");

            }
            cursor.close();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ListView listView = findViewById(R.id.listView);
                loadContacts(listView);
            } else {
                Toast.makeText(this, "Permission denied to read your contacts", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
