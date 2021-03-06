package com.example.mindy.simplymatch3;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    Button btn ;
    ImageView imageview ;
    Uri imageUri ;
    private static final int PICK_IMAGE = 100 ;
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

    public static MyDBHandler myDBHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //imageview = (ImageView) findViewById(R.id.imageview);
        //btn = (Button) findViewById(R.id.btn);

        myDBHandler = new MyDBHandler(this, "ImageDB.sqlite", null, 1);
        myDBHandler.queryData("CREATE TABLE IF NOT EXISTS IMAGES(Id INTEGER PRIMARY KEY AUTOINCREMENT, image BLOG)");

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fram, new FragmentOne()).commit();


        btn = (Button) findViewById(R.id.buttontouse);
        imageview = (ImageView) findViewById(R.id.iv123);

        if(!hasCamera()) {
            btn.setEnabled(false);
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
                openGallery();
                //ActivityCompat.requestPermissions(MainActivity.this, new String[](Manifest.permission.READ_EXTERNAL_STORAGE), GALLERY);
                try {
                    myDBHandler.insertData(imageViewtoByte(imageview));
                    Toast.makeText(getApplicationContext(), "Added Sucessfully!", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void openGallery() {
        Intent gallery = new Intent (Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI) ;
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    public void onRequestPermissionsResult( int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == GALLERY) {
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED ) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("Image/*");
                startActivityForResult(intent, GALLERY);
            }

            else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!",Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private byte[] imageViewtoByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        if (requestCode == GALLERY) {
            //String[] manifest = new String[](READ_EXTERNAL_STORAGE);
            //ActivityCompat.requestPermissions(MainActivity.this, manifest, GALLERY);
        }
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
            onActivityResult(GALLERY, RESULT_OK, galleryIntent);
        }

        private void takePhotoFromCamera() {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA);
            onActivityResult(CAMERA, RESULT_OK, intent);
        }

        public void btnClick(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI) ;
        startActivityForResult(intent, GALLERY);
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {

            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == this.RESULT_CANCELED) {
                return;
            }

            if (resultCode == RESULT_OK && requestCode == GALLERY) {
                if (data != null) {
                    imageUri = data.getData();
                    imageview.setImageURI(imageUri);
                    String[] projection = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(imageUri, projection, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    String filepath = cursor.getString(columnIndex);
                    cursor.close();
                    Bitmap bitmap = BitmapFactory.decodeFile(filepath);
                    Drawable drawable = new BitmapDrawable(bitmap);
                    imageview.setBackground(drawable);



                    try {
                        InputStream inputStream = getContentResolver().openInputStream(imageUri);
                        bitmap = BitmapFactory.decodeStream(inputStream);
                        imageview.setImageBitmap(bitmap);
                        //Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                        //String path = saveImage(bitmap);
                        //Toast.makeText(MainActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                        //imageview.setImageBitmap(bitmap);
                    } catch( FileNotFoundException e) {
                    //} catch (IOException e) {
                        e.printStackTrace();
                        //Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();

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


