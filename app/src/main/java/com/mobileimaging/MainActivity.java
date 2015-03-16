package com.mobileimaging;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.Date;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton useGalleryButton = (ImageButton) findViewById(R.id.galleryButton);
        useGalleryButton.setOnClickListener(galleryListener);

        ImageButton useCameraButton = (ImageButton) findViewById(R.id.cameraButton);
        useCameraButton.setOnClickListener(cameraListener);

        Button skip = (Button) findViewById(R.id.skipButton);
        skip.setOnClickListener(skipListener);
    }

    private View.OnClickListener skipListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MainActivity.this.startActivity(new Intent(MainActivity.this, CroppingActivity.class));
        }
    };

    private View.OnClickListener galleryListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, ChooseFromGalleryActivity.class);
            MainActivity.this.startActivity(intent);
        }
    };

    private View.OnClickListener cameraListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, ShootPanoramaActivity.class);
            MainActivity.this.startActivity(intent);
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
