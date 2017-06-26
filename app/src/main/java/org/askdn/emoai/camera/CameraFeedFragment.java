package org.askdn.emoai.camera;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.askdn.emoai.R;

import timber.log.Timber;

public class CameraFeedFragment extends Fragment {

    public static String CAMERA_FEED = CameraFeedFragment.class.getName();

    private View cameraFeed;

    public static CameraFeedFragment getInstance() {
        return new CameraFeedFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        availableCameraDevices(); // TODO: 25/6/17 Add a check if the camera is not present
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        cameraFeed = layoutInflater.inflate(R.layout.fragment_camera_feed, null, false);

        return cameraFeed;
    }

    private void availableCameraDevices() {
        CameraManager cameraManager = (CameraManager) getActivity().getSystemService(Context.CAMERA_SERVICE);
        try {
            String[] cameraIdList = cameraManager.getCameraIdList();

            if (cameraIdList.length == 0) {
                Timber.e("No camera hardware detected");
            }

        } catch (CameraAccessException error) {
            Timber.e("Device cameras are being used", error);
        }
    }

}
