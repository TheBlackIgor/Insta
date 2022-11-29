package com.example.insta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;

public class ImagesActivity extends AppCompatActivity {

    private LinearLayout imagesContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        this.imagesContainer = (LinearLayout) findViewById(R.id.imagesContainer);

        String folder = getIntent().getStringExtra("folder");
        setTitle(folder);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        File pic = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File dir = new File(pic, "swierczynski/" + folder);
        dir.mkdir();
        Log.d("images", "Amount of photos: "+dir.listFiles().length);

        for (int i = 0; i<dir.listFiles().length; i++){
            String imagePath = dir.listFiles()[i].getPath();
            String imageName = dir.listFiles()[i].getName();
            Bitmap bmp = betterImageDecode(imagePath);
            ImageView img = new ImageView(ImagesActivity.this);
            img.setImageBitmap(bmp);
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            img.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Intent intent = new Intent(ImagesActivity.this, ImageActivity.class);
                    intent.putExtra("img" , imagePath);
                    intent.putExtra("imgName", imageName);
                    startActivity(intent);
                }
            });
            imagesContainer.addView(img);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    400
            );

            params.setMargins(10,20,20,10);

            img.setLayoutParams(params);
        }
    }

    private Bitmap betterImageDecode(String filePath) {
        Bitmap myBitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();    //opcje przekształcania bitmapy
        options.inSampleSize = 2; // zmniejszenie jakości bitmapy 4x
        //
        myBitmap = BitmapFactory.decodeFile(filePath, options);
        return myBitmap;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}