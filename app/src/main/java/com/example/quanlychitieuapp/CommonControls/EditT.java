package com.example.quanlychitieuapp.CommonControls;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quanlychitieuapp.R;

public class EditT extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_t);
        EditText edtName = (EditText) findViewById(R.id.edt_Name);
        EditText edtBirth = (EditText) findViewById(R.id.edtBirth);
        EditText edtEmail = (EditText) findViewById(R.id.edtemail);
        EditText edtPass = (EditText) findViewById(R.id.edtPass);
        TextView txtShowMessage = (TextView) findViewById(R.id.txtShowMessage);

        Button btnReg = (Button) findViewById(R.id.btnReg);
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = "Thông tin tài khoản \n";
                str += "Tài khoản: " + edtName.getText().toString() + "\nMật khẩu: " + edtPass.getText().toString();
                str += "\nNgày sinh " + edtBirth.getText().toString() + "\nEmail: " + edtEmail.getText().toString();
                txtShowMessage.setText(str);


            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}