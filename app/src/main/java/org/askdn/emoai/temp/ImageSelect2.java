package org.askdn.emoai.temp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.askdn.emoai.PermissionUtils;
import org.askdn.emoai.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageSelect2 extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    String mCurrentPhotoPath;
    public final static int PERMISSION_CODE = 0111;

    AlertDialog mDialog;
    ImageView mResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.old_activity_image_select);
        showDialog();

        mResult = (ImageView) findViewById(R.id.result_image);
        ImageView retakepicture = (ImageView) findViewById(R.id.btn_retake);
        retakepicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file= new File("file://"+mCurrentPhotoPath);
                file.delete();
                showDialog();
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode,permissions,grantResults);

        switch (requestCode) {
            case PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    dispatchTakePictureIntent();
                } else {
                    //code to be filled
                }
                break;
        }
    }


    // Displaying the picture after Capturing
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mDialog.cancel();
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Picasso.with(this)
                    .load("file://"+mCurrentPhotoPath)
                    .error(R.drawable.error_placeholder)
                    .into(mResult);
        }
    }

    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.e("Error Creating File:",photoFile.getAbsolutePath());
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "org.askdn.emoai.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }



    private void showDialog() {
        LayoutInflater inflater = ImageSelect2.this.getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(ImageSelect2.this);
        builder.setTitle(getString(R.string.str_choose_picture_from))
                .setView(inflater.inflate(R.layout.old_choose_picture_dialog,null))
                .setCancelable(false);

        mDialog = builder.create();
        mDialog.show();

        ImageView pickCamera = (ImageView) mDialog.findViewById(R.id.btn_take_a_picture);
        pickCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PermissionUtils.checkPermissions(ImageSelect2.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    dispatchTakePictureIntent();
                } else {
                    PermissionUtils.grantPermissions(ImageSelect2.this,
                            PERMISSION_CODE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }
            }
        });

        
    }


}
