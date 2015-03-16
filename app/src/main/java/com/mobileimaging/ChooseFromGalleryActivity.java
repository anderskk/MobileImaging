package com.mobileimaging;


import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ChooseFromGalleryActivity extends Activity {

    List<Bitmap> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        Button chooseImagesButton = (Button) findViewById(R.id.choose_button);
        chooseImagesButton.setOnClickListener(galleryListener);

        Button createPanorama = (Button) findViewById(R.id.create_panorama_from_gallery);
        createPanorama.setOnClickListener(panoramaListener);

    }

    private View.OnClickListener galleryListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
        }
    };

    private View.OnClickListener panoramaListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (images.size() > 1) {
                Bitmap panoramaImage = stitchedImage(images);
                Intent panoramaIntent = new Intent(ChooseFromGalleryActivity.this, CroppingActivity.class);
                panoramaIntent.putExtra("panoramaImage", panoramaImage);
                ChooseFromGalleryActivity.this.startActivity(panoramaIntent);
            } else {
                Toast.makeText(getApplicationContext(), "Need at least 2 images", Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (intent != null) {
            ClipData clipData = intent.getClipData();
            if (clipData != null) {
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    ClipData.Item item = clipData.getItemAt(i);
                    Uri uri = item.getUri();
                    try {
                        Bitmap photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                        if (!images.contains(photo)) {
                            images.add(photo);
                        }
                        GridView gridView = (GridView)findViewById(R.id.grid_view_gallery);
                        gridView.setAdapter(new GridViewAdapter(this.getBaseContext(), images));

                    } catch (Exception e) {
                        Log.e("", e.toString());
                    }
                }
            }
        }
    }

        public Bitmap stitchedImage(List<Bitmap> images) {
        // call to image stitching process here
        return images.get(0);
    }
}
