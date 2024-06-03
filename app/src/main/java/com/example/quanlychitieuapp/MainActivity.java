package com.example.quanlychitieuapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.BoolRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 222;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check for and request necessary permissions at runtime
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_PHONE_NUMBERS,
                    Manifest.permission.READ_SMS
            }, PERMISSION_REQUEST_CODE);
        }
        doTelephonyService();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permissions granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permissions denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void smsManager(View view) {
        startActivity(new Intent(MainActivity.this, sms_manager.class));
    }

    public void IntentACTION_SEND(View view) {
        startActivity(new Intent(MainActivity.this, IntentACTION_SEND.class));
    }

    public void doRequestingCallState(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED) {

            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            int callStatus = telephonyManager.getCallState();
            String callState = null;

            switch (callStatus) {
                case TelephonyManager.CALL_STATE_IDLE:
                    callState = "Phone đang chờ";
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    callState = "Phone đang sử dụng";
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    callState = "Phone đang reo!\n";
                    callState += telephonyManager.getLine1Number();
                    break;
            }
            Toast.makeText(this, callState, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Permissions not granted", Toast.LENGTH_SHORT).show();
            // Optionally, you can request permissions again here
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_PHONE_NUMBERS,
                    Manifest.permission.READ_SMS
            }, PERMISSION_REQUEST_CODE);
        }
    }

    public void doTelephonyService() {
        TelephonyManager telMng = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        telMng.listen(new PhoneStateListener() {
            public void onServiceStateChanged(ServiceState serviceState) {
                String phoneState;
                switch (serviceState.getState()) {
                    case ServiceState.STATE_EMERGENCY_ONLY:
                        phoneState = "STATE_EMERGENCY_ONLY";
                        break;
                    case ServiceState.STATE_IN_SERVICE:
                        phoneState = "STATE_IN_SERVICE";
                        Log.e("in service", "STATE_IN_SERVICE");
                        break;
                    case ServiceState.STATE_OUT_OF_SERVICE:
                        phoneState = "STATE_OUT_OF_SERVICE";
                        Log.e("OUT_OF_SERVICE", "STATE_OUT_OF_SERVICE");
                        break;
                    case ServiceState.STATE_POWER_OFF:
                        phoneState = "STATE_POWER_OFF";
                        Log.e("POWER_OFF", "STATE_POWER_OFF");
                        break;
                    default:
                        phoneState = "Unknown";
                }
                Toast.makeText(MainActivity.this, phoneState, Toast.LENGTH_LONG).show();
            }
        }, PhoneStateListener.LISTEN_SERVICE_STATE);
    }
}
