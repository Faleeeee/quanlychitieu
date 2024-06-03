package com.example.quanlychitieuapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class IntentACTION_SEND extends AppCompatActivity {

    private static final String LOG_TAG = "AndroidExample";

    private EditText editTextPhoneNumber;
    private EditText editTextMessage;

    private Button buttonSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_action_send);

        this.editTextPhoneNumber = (EditText) this.findViewById(R.id.editText_phoneNumber);
        this.editTextMessage = (EditText) this.findViewById(R.id.editText_message);

        this.buttonSend = (Button) this.findViewById(R.id.button_send);

        this.buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSMS_by_Intent_ACTION_SEND();
            }
        });
    }

    private void sendSMS_by_Intent_ACTION_SEND() {
        String phoneNumber = this.editTextPhoneNumber.getText().toString();
        String message = this.editTextMessage.getText().toString();

        Uri uri = Uri.parse("smsto:" + phoneNumber);
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, uri);
        smsIntent.putExtra("sms_body", message);
        try {
            startActivity(smsIntent);
        } catch (Exception ex) {
            Log.e(LOG_TAG, "Your sms has failed... " + ex.getMessage(), ex);
            Toast.makeText(IntentACTION_SEND.this, "Your sms has failed... " + ex.getMessage(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }
}