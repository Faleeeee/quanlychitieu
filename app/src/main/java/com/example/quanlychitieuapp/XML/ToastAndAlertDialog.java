package com.example.quanlychitieuapp.XML;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quanlychitieuapp.R;

public class ToastAndAlertDialog extends AppCompatActivity implements View.OnClickListener {
    EditText txtName;
    Button btnOK, btnHuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_toast_and_alert_dialog);
        txtName = (EditText) findViewById(R.id.txtNameTA);

        btnOK = (Button) findViewById(R.id.btnOk);
        btnOK.setOnClickListener(this);

        btnHuy = (Button) findViewById(R.id.btnHuy);
        btnHuy.setOnClickListener(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnOk) {
            String ten = txtName.getText() + " ";
            ten = ten.trim();

            if (ten.length() == 0) {
                Toast.makeText(ToastAndAlertDialog.this, "Tên không được rỗng", Toast.LENGTH_SHORT).show();
                txtName.selectAll();
                txtName.requestFocus();
            }

            Toast toast = Toast.makeText(ToastAndAlertDialog.this, "Xin chào: \n" + ten, Toast.LENGTH_LONG);
            toast.show();
        }

        if (v.getId() == R.id.btnHuy) {
            //Tạo AlertDialog
            AlertDialog.Builder builder = new AlertDialog.Builder(ToastAndAlertDialog.this);
            builder.setTitle("Xác nhận");
            builder.setMessage("Bạn có muốn thoát");

            //Tạo PositiveButton(TRái)
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            //Tạo NegativeButton(Phái)
            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.create().show();
        }

    }
}