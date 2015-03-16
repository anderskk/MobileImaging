package com.mobileimaging;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.camera.CropImageIntentBuilder;

import java.io.File;

public class CroppingActivity extends Activity implements View.OnClickListener {
    private static int REQUEST_PICTURE = 1;
    private static int REQUEST_CROP_PICTURE = 2;

    private Button button;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_crop);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);

        imageView = (ImageView) findViewById(R.id.stitched_image);
    }

    @Override
    public void onClick(View view) {
        if (view.equals(button)) {
            startActivityForResult(MediaStoreUtils.getPickImageIntent(this), REQUEST_PICTURE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_crop, menu);

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        File croppedImageFile = new File(getFilesDir(), "test.jpg");

        if ((requestCode == REQUEST_PICTURE) && (resultCode == RESULT_OK)) {
            // When the user is done picking a picture, let's start the CropImage Activity,
            // setting the output image file and size to 200x200 pixels square.
            Uri croppedImage = Uri.fromFile(croppedImageFile);

            CropImageIntentBuilder cropImage = new CropImageIntentBuilder(200, 200, croppedImage);
//            cropImage.setOutlineColor(0xFF03A9F4);
            cropImage.setSourceImage(data.getData());

            startActivityForResult(cropImage.getIntent(this), REQUEST_CROP_PICTURE);
        } else if ((requestCode == REQUEST_CROP_PICTURE) && (resultCode == RESULT_OK)) {
            // When we are done cropping, display it in the ImageView.
            imageView.setImageBitmap(BitmapFactory.decodeFile(croppedImageFile.getAbsolutePath()));
        }
    }
}













//import android.app.Activity;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.net.Uri;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import com.soundcloud.android.crop.Crop;
//import java.io.File;
//
//public class CroppingActivity extends Activity {
//
//
//    private ImageView resultView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_crop);
//
//        resultView = (ImageView) findViewById(R.id.stitched_image);
////        Intent intent = getIntent();
////        Bitmap panorama = intent.getBundleExtra("panoramaImage").getParcelable("panoramaImage");
//
////        resultView.setImageBitmap(panorama);
//
//
//    }
//
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_crop, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.action_select) {
//            resultView.setImageDrawable(null);
//            Crop.pickImage(this);
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
//        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
//            beginCrop(result.getData());
//        } else if (requestCode == Crop.REQUEST_CROP) {
//            handleCrop(resultCode, result);
//        }
//    }
//
//    private void beginCrop(Uri source) {
//        Uri outputUri = Uri.fromFile(new File(getCacheDir(), "cropped"));
//        new Crop(source).output(outputUri).asSquare().start(this);
//    }
//
//    private void handleCrop(int resultCode, Intent result) {
//        if (resultCode == RESULT_OK) {
//            resultView.setImageURI(Crop.getOutput(result));
//        } else if (resultCode == Crop.RESULT_ERROR) {
//            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
//        }
//    }
//}
