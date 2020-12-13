package com.wolfinfinity.runtimepermission;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private static final int ALLOW_MULTIPLE_PERMISSION_REQUEST = 101;
    private static final int ALLOW_SINGLE_PERMISSION_REQUEST = 102;

    private Button mBtAllowMultiplePermission, mBtAllowSinglePermission;

    String[] PERMISSIONS = {
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Init();
    }

    private void Init() {
        mBtAllowMultiplePermission = findViewById(R.id.btAllowMultiplePermission);
        mBtAllowSinglePermission = findViewById(R.id.btAllowSinglePermission);

        ClickListener();
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkSinglePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, ALLOW_SINGLE_PERMISSION_REQUEST);
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    // Check Gallery And Camera Access Permission Is Given Or Not
    private boolean checkMultiplePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        ALLOW_MULTIPLE_PERMISSION_REQUEST);
                return false;

            }else if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        ALLOW_MULTIPLE_PERMISSION_REQUEST);
                return false;

            } else if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        ALLOW_MULTIPLE_PERMISSION_REQUEST);
                return false;

            }else {
                return true;
            }
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case ALLOW_MULTIPLE_PERMISSION_REQUEST:
                if (grantResults.length > 2   && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Allow Permission Succeed.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Please Allow Permission.", Toast.LENGTH_SHORT).show();
                }
                break;

            case ALLOW_SINGLE_PERMISSION_REQUEST:
                if (grantResults.length > 0   && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Allow Permission Succeed.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Please Allow Permission.", Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                break;
        }
    }

    // Click Listener
    private void ClickListener() {
        mBtAllowMultiplePermission.setOnClickListener(this);
        mBtAllowSinglePermission.setOnClickListener(this);
    }

    // Click Event0
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btAllowMultiplePermission:
                if (!hasPermissions(this, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(this, PERMISSIONS, ALLOW_MULTIPLE_PERMISSION_REQUEST);
                } else {
                    Toast.makeText(this, "Allow Permission Succeed.", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btAllowSinglePermission:
                if (checkSinglePermission()) {
                    Toast.makeText(this, "Allow Permission Succeed.", Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                break;
        }
    }
}