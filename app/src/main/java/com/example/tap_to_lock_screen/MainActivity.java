package com.example.tap_to_lock_screen;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_ENABLE_ADMIN = 1;

    private DevicePolicyManager devicePolicyManager;
    private ComponentName adminComponent;
    private TextView messageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        adminComponent = new ComponentName(this, LockAdminReceiver.class);
        setContentView(createContentView());

        if (devicePolicyManager.isAdminActive(adminComponent)) {
            lockScreen();
        } else {
            requestDeviceAdmin();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != REQUEST_ENABLE_ADMIN) {
            return;
        }

        if (devicePolicyManager.isAdminActive(adminComponent)) {
            lockScreen();
        } else {
            messageView.setText(R.string.admin_not_enabled);
        }
    }

    private LinearLayout createContentView() {
        int padding = (int) (32 * getResources().getDisplayMetrics().density);
        LinearLayout content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setGravity(Gravity.CENTER);
        content.setPadding(padding, padding, padding, padding);

        messageView = new TextView(this);
        messageView.setGravity(Gravity.CENTER);
        messageView.setText(R.string.screen_instructions);
        messageView.setTextSize(18);
        content.addView(messageView, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        Button enableButton = new Button(this);
        enableButton.setText(R.string.enable_admin);
        enableButton.setOnClickListener(view -> requestDeviceAdmin());
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        buttonParams.topMargin = padding / 2;
        content.addView(enableButton, buttonParams);

        return content;
    }

    private void requestDeviceAdmin() {
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, adminComponent);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, getString(R.string.admin_explanation));
        startActivityForResult(intent, REQUEST_ENABLE_ADMIN);
    }

    private void lockScreen() {
        try {
            devicePolicyManager.lockNow();
            finish();
        } catch (SecurityException exception) {
            messageView.setText(R.string.lock_failed);
        }
    }
}
