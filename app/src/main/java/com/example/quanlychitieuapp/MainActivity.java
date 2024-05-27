package com.example.quanlychitieuapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void buttonInternetUsage(View view) {
        String stringURL = "https://th.bing.com/th/id/OIP.nxCTB5Bu4bmfCG9TTf0wHaHa?rs=1&pid=ImgDetMain";
        try {
            InputStream inputStream = (InputStream) new URL(stringURL).getContent();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            imageView.setImageBitmap(bitmap);
        } catch (Exception e) {
            textView.setText(e.toString());
        }
    }
}