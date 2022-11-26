package com.example.insta;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;

public class Images extends AppCompatActivity {

    private LinearLayout linearLayout;
    private TextView pageTitle = (TextView) findViewById(R.id.pageTitle);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
//        pageTitle.setText("Album\n" + getIntent().getStringExtra("folder")); ;

//        ActionBar actionbar = getSupportActionBar();
//        actionbar.setDisplayHomeAsUpEnabled(true);
//
//        linearLayout = findViewById(R.id.imageLayout);
//
//        Bundle bundle = getIntent().getExtras();
//        String  folder = bundle.getString("folder").toString();
//        Log.d("XXX", folder);
//
//        File pic = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//        File dir = new File(pic, "swierczynski/" + folder);
//        Log.d("bbb" , dir.getPath());
//        dir.mkdir();
//
//        for(int i=0; i< dir.listFiles().length; i++){
//            String imagePath = dir.listFiles()[i].getPath();
//            Bitmap bmp = betterImageDecode(imagePath);
//            ImageView img = new ImageView(Images.this);
//            img.setImageBitmap(bmp);
//            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            img.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(Images.this, Image.class);
//                    intent.putExtra("img" , imagePath);
//                    startActivity(intent);
//                }
//            });
//            linearLayout.addView(img);
//            img.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,400));
//        }
    }

//    private Bitmap betterImageDecode(String filePath){
//        Bitmap myBitmap;
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inSampleSize = 4;
//        myBitmap = BitmapFactory.decodeFile(filePath, options);
//        return myBitmap;
//    }
}