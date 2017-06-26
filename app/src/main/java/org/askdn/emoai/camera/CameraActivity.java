package org.askdn.emoai.camera;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import org.askdn.emoai.R;

public class CameraActivity extends AppCompatActivity {

    /**
     * Starts this activity.
     */
    public static void start(Context context) {
        Intent intent = new Intent(context, CameraActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                                                        .beginTransaction()
                                                        .replace(R.id.camera_fragment_container,
                                                                 CameraFeedFragment.getInstance(),
                                                                 CameraFeedFragment.CAMERA_FEED);

        fragmentTransaction.commitAllowingStateLoss();
    }

}
