package com.example.insta;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private LinearLayout cameraBtn;
    private LinearLayout albumsBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cameraBtn = findViewById(R.id.buttonCamera);
        albumsBtn = findViewById(R.id.buttonAlbum);
        albumsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this , AlbumActivity.class);
                startActivity(intent);
            }
        });
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Wybirz źródło zdjęcia!");
                String[] opcje = {"Aparat" , "Galeria"};
                alert.setItems(opcje, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(opcje[i] == "Aparat"){
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            if(intent.resolveActivity(getPackageManager()) != null){
                                startActivityForResult(intent,200);
                            }
                        }else{
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType("image/*");
                            startActivityForResult(intent, 100);
                        }
                    }
                });
                alert.show();

            }
        });
        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 100);
        checkPermission(Manifest.permission.CAMERA, 100);




    }

    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
        } else {
            Toast.makeText(MainActivity.this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }
    //@Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        //
//        switch (requestCode) {
//            case 100:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    //tak
//                } else {
//                    //nie
//                }
//                break;
//            case 101 :
//
//                break;
//        }
//
//    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 200){
            Log.d("qwerty", "asadfasfasdfasdf");
            if(resultCode == RESULT_OK){
                Bundle extras = data.getExtras();
                Bitmap b = (Bitmap) extras.get("data");

            }
        }else if(requestCode == 100){
            if(resultCode == RESULT_OK){
                Uri imgData = data.getData();
                try {
                    InputStream stream = getContentResolver().openInputStream(imgData);
                    Bitmap b = BitmapFactory.decodeStream(stream);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }

    }
}