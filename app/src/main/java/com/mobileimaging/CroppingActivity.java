package com.mobileimaging;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.camera.CropImageIntentBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class CroppingActivity extends Activity implements View.OnClickListener {
    private static int REQUEST_PICTURE = 1;
    private static int REQUEST_CROP_PICTURE = 2;
    private static int PIC_CROP = 1;

    private Button button;
    private ImageView imageView;
    Bitmap finalImage = null;
    File croppedImageFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_crop);
        String filename = getIntent().getStringExtra("panoramaImage");
        try {
            FileInputStream is = this.openFileInput(filename);
            finalImage = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        imageView = (ImageView) findViewById(R.id.stitched_image);
        imageView.setImageBitmap(finalImage);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.equals(button)) {

            performCrop(getImageUri(getBaseContext(), finalImage));
//            startActivityForResult(MediaStoreUtils.getPickImageIntent(this), REQUEST_PICTURE);
//            croppedImageFile = new File(getFilesDir(), "test.jpg");
//            Uri croppedImage = Uri.fromFile(croppedImageFile);
//            CropImageIntentBuilder cropImage = new CropImageIntentBuilder(200, 200, croppedImage);
////            cropImage.setOutlineColor(0xFF03A9F4);
//            cropImage.setSourceImage(getImageUri(getBaseContext(), finalImage));
//
//            startActivityForResult(cropImage.getIntent(this), REQUEST_CROP_PICTURE);

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

        if (requestCode == PIC_CROP) {
            if (data != null) {
                // get the returned data
                Bundle extras = data.getExtras();
                // get the cropped bitmap
                Bitmap selectedBitmap = extras.getParcelable("data");
                saveToInternalSorage(selectedBitmap);

                imageView.setImageBitmap(selectedBitmap);
            }
        }

//        imageView.setImageBitmap(BitmapFactory.decodeFile(croppedImageFile.getAbsolutePath()));

//        if ((requestCode == REQUEST_PICTURE) && (resultCode == RESULT_OK)) {
//            // When the user is done picking a picture, let's start the CropImage Activity,
//            // setting the output image file and size to 200x200 pixels square.
//            Uri croppedImage = Uri.fromFile(croppedImageFile);
//
//            CropImageIntentBuilder cropImage = new CropImageIntentBuilder(200, 200, croppedImage);
////            cropImage.setOutlineColor(0xFF03A9F4);
//            cropImage.setSourceImage(data.getData());
//
//            startActivityForResult(cropImage.getIntent(this), REQUEST_CROP_PICTURE);
//        } else if ((requestCode == REQUEST_CROP_PICTURE) && (resultCode == RESULT_OK)) {
//            // When we are done cropping, display it in the ImageView.
//            imageView.setImageBitmap(BitmapFactory.decodeFile(croppedImageFile.getAbsolutePath()));
//        }
    }

    private String saveToInternalSorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File path = new File(directory,"profile.jpg");

        FileOutputStream fos = null;
        try {

            fos = new FileOutputStream(path);

            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return directory.getAbsolutePath();
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 10, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private void performCrop(Uri picUri) {
        try {

            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
//            cropIntent.putExtra("aspectX", 1);
//            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 200);
            cropIntent.putExtra("outputY", 200);
            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, PIC_CROP);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            // display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
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
