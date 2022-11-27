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
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

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
                String[] options = {"Aparat" , "Galeria"};
                alert.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(options[i] == "Aparat"){
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
        File pic = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File dir = new File(pic, "swierczynski");
        String[] opcje = new String[dir.listFiles().length];
        for(int i=0; i< dir.listFiles().length; i++){
            opcje[i] = dir.listFiles()[i].getName();
        }
        if(requestCode == 200){
            if(resultCode == RESULT_OK){
                Bundle extras = data.getExtras();
                Bitmap b = (Bitmap) extras.get("data");
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Wybierz folder do zapisu?");
                alert.setItems(opcje, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        b.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        byte[] byteArray = stream.toByteArray();
                        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
                        String d = df.format(new Date());
                        FileOutputStream fs = null;
                        try {
                            fs = new FileOutputStream(dir.listFiles()[i].getPath() + "/" + d + ".jpg");
                            fs.write(byteArray);
                            fs.close();

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                alert.show();
            }
        }else if(requestCode == 100){
            if(resultCode == RESULT_OK){
                Uri imgData = data.getData();
                try {
                    InputStream stream = getContentResolver().openInputStream(imgData);
                    Bitmap b = BitmapFactory.decodeStream(stream);
                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setTitle("Wybierz folder do zapisu?");
                    alert.setItems(opcje, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            b.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                            byte[] byteArray = stream.toByteArray();
                            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
                            String d = df.format(new Date());
                            FileOutputStream fs = null;
                            try {
                                fs = new FileOutputStream(dir.listFiles()[i].getPath() + "/" + d + ".jpg");
                                fs.write(byteArray);
                                fs.close();

                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    alert.show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


            }
        }

    }
}