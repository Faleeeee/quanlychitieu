package com.example.quanlychitieuapp.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlychitieuapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link addFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class addFragment extends Fragment {

    private TextView tvHeader;
    private EditText etAmount, etDescription, etDate, etAccount;
    private Button btnAdd, btnSubtract, btnMultiply, btnDivide, btnClear, btnEquals;
    private Spinner spnCategory;
    private Button btnSave;
    private GridLayout customKeyboard;
    private View btnDelete;

    private double firstNumber = 0;
    private String operator = "";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public addFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment addFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static addFragment newInstance(String param1, String param2) {
        addFragment fragment = new addFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        // Ánh xạ các thành phần giao diện
        tvHeader = view.findViewById(R.id.tvHeader);
        etAmount = view.findViewById(R.id.etAmount);
        etDescription = view.findViewById(R.id.etDescription);
        etDate = view.findViewById(R.id.etDate);
        etAccount = view.findViewById(R.id.etAccount);

        spnCategory = view.findViewById(R.id.spnCategory);
        btnSave = view.findViewById(R.id.btnSave);

        btnAdd = view.findViewById(R.id.btnAdd);
        btnSubtract = view.findViewById(R.id.btnSubtract);
        btnMultiply = view.findViewById(R.id.btnMultiply);
        btnDivide = view.findViewById(R.id.btnDivide);
        btnDelete = view.findViewById(R.id.btnDelete);
        btnEquals = view.findViewById(R.id.btnEquals);

        // Thiết lập sự kiện cho các nút phép toán
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOperator("+");
            }
        });

        btnSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOperator("-");
            }
        });

        btnMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOperator("*");
            }
        });

        btnDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOperator("/");
            }
        });

        btnEquals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performOperation();
            }
        });

        // Thiết lập bàn phím tùy chỉnh
        setUpCustomKeyboard(view);

        return view;
    }

    private void setUpCustomKeyboard(View view) {
        int[] buttonIds = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
                R.id.btn000, R.id.btnDot
        };

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                etAmount.append(b.getText().toString());
            }
        };
        for (int id : buttonIds) {
            view.findViewById(id).setOnClickListener(listener);
        }

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentText = etAmount.getText().toString();
                if (!currentText.isEmpty()) {
                    etAmount.setText(currentText.substring(0, currentText.length() - 1));
                }
            }
        });
    }

    private void setOperator(String operator) {
        String currentAmount = etAmount.getText().toString();
        if (!currentAmount.isEmpty()) {
            firstNumber = Double.parseDouble(currentAmount);
            etAmount.setText("");
            this.operator = operator;
        }
    }

    private void performOperation() {
        String currentAmount = etAmount.getText().toString();
        if (!currentAmount.isEmpty()) {
            double secondNumber = Double.parseDouble(currentAmount);
            double result = 0;

            switch (operator) {
                case "+":
                    result = firstNumber + secondNumber;
                    break;
                case "-":
                    result = firstNumber - secondNumber;
                    break;
                case "*":
                    result = firstNumber * secondNumber;
                    break;
                case "/":
                    if (secondNumber != 0) {
                        result = firstNumber / secondNumber;
                    } else {
                        Toast.makeText(getActivity(), "Cannot divide by zero", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    break;
            }

            etAmount.setText(String.valueOf(result));
            firstNumber = 0;
            operator = "";
        }
    }

    private void saveTransaction(String amount, String category, String description, String date, String account) {
        // Hàm lưu dữ liệu giao dịch, có thể là lưu vào cơ sở dữ liệu hoặc xử lý logic khác
        // Ví dụ: Hiển thị thông báo
        Toast.makeText(getActivity(), "Giao dịch đã được lưu", Toast.LENGTH_SHORT).show();
    }
}