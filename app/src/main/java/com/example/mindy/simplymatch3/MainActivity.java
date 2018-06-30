package com.example.mindy.simplymatch3;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private Button btn ;
    private ImageView imageview ;
    private static final String IMAGE_DIRECTORY = "/demonuts" ;
    private int GALLERY = 1 , CAMERA = 2 ;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_clothing:
                    setTitle("Collections");
                    FragmentOne fragment = new FragmentOne() ; //set the title of the action bar
                    android.support.v4.app.FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction() ;
                    fragmentTransaction1.replace(R.id.fram,fragment,"FragmentName") ; //fram is id of framelayout in xml file
                    fragmentTransaction1.commit();
                    return true;
                case R.id.navigation_add:
                    setTitle("Add Outfits");
                    FragmentTwo fragment2 = new FragmentTwo() ;
                    android.support.v4.app.FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction() ;
                    fragmentTransaction2.replace(R.id.fram, fragment2,"FragmentName") ;
                    fragmentTransaction2.commit();
                    return true;
                case R.id.navigation_pieces:
                    setTitle("Clothing Pieces");
                    FragmentThree fragment3 = new FragmentThree() ;
                    android.support.v4.app.FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction() ;
                    fragmentTransaction3.replace(R.id.fram,fragment3,"FragmentName") ;
                    fragmentTransaction3.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fram, new FragmentOne()).commit();


        btn = (Button) findViewById(R.id.buttontouse);
        imageview = (ImageView) findViewById(R.id.iv);

        if(!hasCamera()) {
            btn.setEnabled(false);
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });
    }

    private boolean hasCamera() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }


    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {"Select photo from gallery", "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        choosePhotoFromGallery();
                        break;
                    case 1:
                        takePhotoFromCamera();
                        break;
                }
            }
        });

        pictureDialog.show();
    }

        public void choosePhotoFromGallery() {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(galleryIntent, GALLERY);
        }

        private void takePhotoFromCamera() {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA);
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {

            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == this.RESULT_CANCELED) {
                return;
            }

            if (requestCode == GALLERY) {
                if (data != null) {
                    Uri contentURI = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                        String path = saveImage(bitmap);
                        Toast.makeText(MainActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                        imageview.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();

                    }
                }
            } else if (requestCode == CAMERA && resultCode == RESULT_OK) {
                Bundle extras = data.getExtras() ;
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                imageview.setImageBitmap(thumbnail);
                saveImage(thumbnail);
                Toast.makeText(MainActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
            }
        }

        public String saveImage(Bitmap myBitmap) {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
            File wallpaperDirectory = new File(
                    Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
            if (!wallpaperDirectory.exists()) {
                wallpaperDirectory.mkdirs();
            }

            try {
                File f = new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + "jpg");
                f.createNewFile();
                FileOutputStream fo = new FileOutputStream(f);
                fo.write(bytes.toByteArray());
                MediaScannerConnection.scanFile(this,
                        new String[]{f.getPath()},
                        new String[]{"image/jpeg"}, null);
                fo.close();
                Log.d("TAG", "File Saved: : --->" + f.getAbsolutePath());

                return f.getAbsolutePath();
            } catch (IOException el) {
                el.printStackTrace();
            }
            return "";
        }



    private android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    android.support.v4.app.Fragment selectedFragment = null ;

                    switch(item.getItemId()) {
                        case R.id.navigation_clothing:
                            selectedFragment = new FragmentOne() ;
                            break;
                        case R.id.navigation_add:
                            selectedFragment = new FragmentTwo() ;
                            break;
                        case R.id.navigation_pieces:
                            selectedFragment = new FragmentThree() ;
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fram,selectedFragment).commit() ;

                    return true;
                }
            };


}


