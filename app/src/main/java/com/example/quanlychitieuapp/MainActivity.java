package com.example.quanlychitieuapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import org.mariuszgromada.math.mxparser.Expression;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private EditText display, note_content;
    private Button btnSave, btnBack, btnDate;
    private String dateSelect;
    private TextView nhomGiaoDich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        initViews();
        setupListeners();
        giaoDich();
        checkFieldsForEmptyValues();
    }

    private void giaoDich() {
        Intent intent = getIntent();
        String data = intent.getStringExtra("Giaodich"); // Nhận chuỗi dữ liệu từ Intent
        nhomGiaoDich.setText(data);
    }

    private void initViews() {
        display = findViewById(R.id.display);
        note_content = findViewById(R.id.noteEdit);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);
        btnDate = findViewById(R.id.btnDate);
        nhomGiaoDich = findViewById(R.id.textGroup);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
//ngăn chặn bàn phím khi người dùng ấn vào editText
        display.setShowSoftInputOnFocus(false);
    }

    private void setupListeners() {
        display.setOnClickListener(this::clearDisplayOnFirstClick);
        display.addTextChangedListener(textWatcher);

        btnBack.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, thongke.class)));
        nhomGiaoDich.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, nhom.class)));
        btnSave.setOnClickListener(v -> saveTransaction());
        btnDate.setOnClickListener(v -> showDatePickerDialog());
    }

    private void clearDisplayOnFirstClick(View v) {
        if (getString(R.string.calculator).equals(display.getText().toString())) {
            display.setText("");
        }
    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            checkFieldsForEmptyValues();
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
            dateSelect = String.format("%d/%d/%d", dayOfMonth, month1 + 1, year1);
            btnDate.setText(dateSelect);
            checkFieldsForEmptyValues();
        }, year, month, day);
        datePickerDialog.show();
    }

    private void saveTransaction() {
        Double money = 0.0;
        try {
            money = Double.parseDouble(display.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Định dạng số không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        int id_wal = 8;
        String group_name = "Ăn uống";
        String date = dateSelect;
        String note = note_content.getText().toString();
        String giaoDich = nhomGiaoDich.getText().toString();
        Bundle myBundle = new Bundle();
        myBundle.putDouble("money", money);
        myBundle.putInt("id_wal", id_wal);
        myBundle.putString("giaoDich", giaoDich);
        myBundle.putString("group_name", group_name);
        myBundle.putString("day", date);
        myBundle.putString("note", note);

        Intent myIntent = new Intent(MainActivity.this, thongke.class);
        myIntent.putExtra("myPackage", myBundle);

        startActivity(myIntent);
    }

    private void checkFieldsForEmptyValues() {
        String money = display.getText().toString();
        if (money.isEmpty() || dateSelect == null || dateSelect.isEmpty()) {
            btnSave.setEnabled(false);
            btnSave.setBackgroundResource(R.drawable.disabled_button_background);
        } else {
            btnSave.setEnabled(true);
            btnSave.setBackgroundResource(R.drawable.enabled_button_background);
        }
    }

    // Phương thức cập nhật văn bản
    private void updateText(String strToAdd) {
        String oldStr = display.getText().toString();
        int cursorPos = display.getSelectionStart();
        String leftStr = oldStr.substring(0, cursorPos);
        String rightStr = oldStr.substring(cursorPos);

        display.setText(String.format("%s%s%s", leftStr, strToAdd, rightStr));
        display.setSelection(cursorPos + 1);
    }

    private boolean isThreeZeroBtnPressed(View view) {
        return view.getId() == R.id.btn000; // Kiểm tra xem id của view có phải là btnThreeZero không
    }

    // Các phương thức xử lý các nút số và toán học
    public void zeroBtn(View view) {
        updateText("0");
    }

    public void threeZeroBtn(View view) {
        updateText("000");
    }

    public void dotBtn(View view) {
        updateText(".");
    }

    public void oneBtn(View view) {
        updateText("1");
    }

    public void twoBtn(View view) {
        updateText("2");
    }

    public void threeBtn(View view) {
        updateText("3");
    }

    public void fourBtn(View view) {
        updateText("4");
    }

    public void fiveBtn(View view) {
        updateText("5");
    }

    public void sixBtn(View view) {
        updateText("6");
    }

    public void sevenBtn(View view) {
        updateText("7");
    }

    public void eightBtn(View view) {
        updateText("8");
    }

    public void nineBtn(View view) {
        updateText("9");
    }

    public void addBtn(View view) {
        updateText("+");
    }

    public void subtractBtn(View view) {
        updateText("-");
    }

    public void multiplyBtn(View view) {
        updateText("×");
    }

    public void divideBtn(View view) {
        updateText("/");
    }

    public void deleteBtn(View view) {
        int cursorPos = display.getSelectionStart();
        int textLen = display.getText().length();

        if (cursorPos != 0 && textLen != 0) {
            SpannableStringBuilder selection = (SpannableStringBuilder) display.getText();
            selection.replace(cursorPos - 1, cursorPos, "");
            display.setText(selection);
            display.setSelection(cursorPos - 1);
        }
    }

    public void restartBtn(View view) {
        display.setText("");
    }

    public void equalBtn(View view) {
        String userExp = display.getText().toString();
        userExp = userExp.replaceAll("÷", "/");
        userExp = userExp.replaceAll("×", "×");

        Expression exp = new Expression(userExp);
        String result = String.valueOf(exp.calculate());

        display.setText(result);
        display.setSelection(result.length());
    }
}
