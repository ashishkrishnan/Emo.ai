package org.askdn.emoai;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v13.app.FragmentCompat;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ashish on 10/20/16.
 */

public class PermissionUtils {

    public static boolean checkPermissions(Context context, String... permissions) {
        List<String> request = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                request.add(permission);
            }
        }
        if(request.isEmpty()) {
            return true;
        }
        else
            return false;
    }

    public static void grantPermissions(Object o, int code, String...permissions) {
        if(o instanceof Activity)
            ActivityCompat.requestPermissions((AppCompatActivity) o, permissions, code);
    }
}
