package com.mobileimaging;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShootPanoramaActivity extends Activity {

    private static String logtag = "mImaging";
    private static int TAKE_PICTURE = 1;
    private static final int CAMERA_REQUEST = 1888;
    private List<Bitmap> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_take_picture);

        Button takePictureButton = (Button) findViewById(R.id.take_picture);
        takePictureButton.setOnClickListener(cameraListener);

        Button createPanorama = (Button) findViewById(R.id.create_panorama_from_camera);
        createPanorama.setOnClickListener(panoramaListener);

    }

    private View.OnClickListener cameraListener = new View.OnClickListener() {
        public void onClick(View v) {
            takePhoto(v);
        }
    };

    private void takePhoto(View view) {
        Date date = new Date();
        String picture_date = date.toString();
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        File photo = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "stitch_" + picture_date);
//        Uri image_uri = Uri.fromFile(photo);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    private View.OnClickListener panoramaListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (images.size() > 1) {
                sendToCropping(v, getBaseContext());
            } else {
                Toast.makeText(getApplicationContext(), "Need at least 2 images", Toast.LENGTH_SHORT).show();
            }
        }
    };


    private void sendToCropping(View v, Context ctx) {
        FileOutputStream stream;
        Bitmap panoramaImage = stitchedImage(images);
        try {
            String filename = "bitmap.png";
            stream = ctx.openFileOutput(filename, Context.MODE_PRIVATE);

            panoramaImage.compress(Bitmap.CompressFormat.PNG, 10, stream);

            stream.close();
            panoramaImage.recycle();

            Intent panoramaIntent = new Intent(ShootPanoramaActivity.this, CroppingActivity.class);
            panoramaIntent.putExtra("panoramaImage", filename);
            ShootPanoramaActivity.this.startActivity(panoramaIntent);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (resultCode == RESULT_OK) {
            Uri selectedImage = intent.getData();
            getContentResolver().notifyChange(selectedImage, null);

//            ImageView imageView = (ImageView) findViewById(R.id.captured_image);
//            GridView gridView = (GridView) findViewById(R.id.grid_view);

            Bitmap photo;

            try {
                photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                images.add(photo);
                GridView gridView = (GridView)findViewById(R.id.grid_view_picture);
                gridView.setAdapter(new GridViewAdapter(this.getBaseContext(), images));
                Toast.makeText(ShootPanoramaActivity.this, "Number of images: " + images.size(), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.e(logtag, e.toString());
                System.out.println("Entered Exception");
            }
        } else if (resultCode == RESULT_CANCELED) {
            Log.e(logtag, "Something went wrong");
        }
    }

    public Bitmap stitchedImage(List<Bitmap> images) {
        // call to image stitching process here
        return images.get(0);
    }

}
