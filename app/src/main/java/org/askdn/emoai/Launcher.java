package org.askdn.emoai;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Launcher extends AppCompatActivity {

    public final static int PERMISSION_CODE = 0010;
    private View mActivityView;

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Using ChristenJinx's Calligraphy, the application wide default font is set
        //overrideDefaultFont();
        setContentView(R.layout.activity_launcher);
        mActivityView = findViewById(R.id.activity_launcher);

        if (PermissionUtils.checkPermissions(this, Manifest.permission.CAMERA)) {
            call_imageSelectActivity();
        } else {
            PermissionUtils.grantPermissions(this, PERMISSION_CODE, Manifest.permission.CAMERA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    call_imageSelectActivity();
                } else {
                    makeSnackBar(mActivityView, "Permission was Denied", Snackbar.LENGTH_INDEFINITE);
                }
                break;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void overrideDefaultFont() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/NovaMono.ttf")
                .setFontAttrId(R.attr.fontPath).build());

    }



    // Invoke the call to the Image Capture Activity
    private void call_imageSelectActivity() {
        if (checkCameraHardware(this)) {
            Intent invokeImageSelect = new Intent(this, ImageSelect.class);
            startActivity(invokeImageSelect);
            finish();
        } else {
            Snackbar.make(mActivityView,getString(R.string.msg_no_front_camera),Snackbar.LENGTH_INDEFINITE)
            .setAction("Exit", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            }).show();
        }
    }

    private void makeSnackBar(View view, String message, int length) {
        Snackbar.make(view, message, length)
                .setAction("ENABLE", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PermissionUtils.grantPermissions(Launcher.this, PERMISSION_CODE, Manifest.permission.CAMERA);
                    }
                })
                .show();
    }


    // Check for existing Front Camera (Mandatory for the app)
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA_FRONT))
            return true;
        else return false;
    }

}