package com.example.insta;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class Image extends AppCompatActivity {

    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        img = findViewById(R.id.image);

        Bundle bundle = getIntent().getExtras();
        String img1 = bundle.getString("img").toString();
        Bitmap bmp = betterImageDecode(img1);

        img.setImageBitmap(bmp);
        img.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    private Bitmap betterImageDecode(String filePath){
        Bitmap myBitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        myBitmap = BitmapFactory.decodeFile(filePath, options);
        return myBitmap;
    }
}