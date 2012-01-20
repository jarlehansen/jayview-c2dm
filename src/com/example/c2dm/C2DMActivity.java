package com.example.c2dm;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class C2DMActivity extends Activity {
    static final String TAG = "JayView-C2DM";
    private C2DMReceiver messageReceiver = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void registerDevice(View view) {
        messageReceiver = new C2DMReceiver();
        messageReceiver.register(this);
    }

    public void exit(View view) {
        if (messageReceiver != null) {
            messageReceiver.unRegister(this);
        }

        finish();
    }
}
