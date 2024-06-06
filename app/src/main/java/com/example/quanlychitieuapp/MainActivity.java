package com.example.quanlychitieuapp;

import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import org.mariuszgromada.math.mxparser.Expression;

public class MainActivity extends AppCompatActivity {
    private EditText display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        display = findViewById(R.id.display);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        display.setShowSoftInputOnFocus(false);
        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getString(R.string.calculator).equals(display.getText().toString())) {
                    display.setText("");
                }
            }
        });
    }

    private void updateText(String strtoAdd) {
        String oldStr = display.getText().toString();
        int cursorPos = display.getSelectionStart();
        String leftStr = oldStr.substring(0, cursorPos);
        String rightStr = oldStr.substring(cursorPos);

        if (getString(R.string.calculator).equals(display.getText().toString())) {
            display.setText(strtoAdd);
            display.setSelection(cursorPos + 1);
        } else {
            display.setText(String.format("%s%s%s", leftStr, strtoAdd, rightStr));
            display.setSelection(cursorPos + 1);
        }
    }

    public void zeroBtn(View view) {
        updateText("0");
    }

    public void threeZeroBtn(View view) {
        updateText("000");
    }

    public void dotBtn(View view) {
        updateText(".");

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
}