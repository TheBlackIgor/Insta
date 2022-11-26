package com.example.insta;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.io.File;

public class AlbumActivity extends AppCompatActivity {

    private ListView listView;
    private LinearLayout addFolderBtn;

    public void refreshDirectory(File dir){

        String[] array = new String[dir.listFiles().length];
        for(int i=0; i< dir.listFiles().length; i++){
            array[i] = dir.listFiles()[i].getName();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                AlbumActivity.this,
                R.layout.list_view_elem,
                R.id.tv1,
                array
        );
        listView.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        File pic = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File dir = new File(pic, "swierczynski");
        dir.mkdir();
        File dir1 = new File(pic, "swierczynski/ludzie");
        dir1.mkdir();
        dir1 = new File(pic, "swierczynski/rzeczy");
        dir1.mkdir();
        dir1 = new File(pic, "swierczynski/miejsca");
        dir1.mkdir();

        File[] files = dir.listFiles();

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        listView = findViewById(R.id.listViewID);
        refreshDirectory(dir);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("longClikc", adapterView.getItemAtPosition(i).toString());
                AlertDialog.Builder alert = new AlertDialog.Builder(AlbumActivity.this);
                alert.setTitle("USUWANIE FOLDERU");
                alert.setMessage("Czy na pewno usunać?");
                alert.setPositiveButton("USUŃ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        File toRemove = new File(pic, "swierczynski/" + adapterView.getItemAtPosition(i).toString());
                        toRemove.delete();
                        refreshDirectory(dir);
                    }
                });
                alert.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                    }
                });
                alert.show();
                return true;
            }
        });

        addFolderBtn = findViewById(R.id.addButton);
        addFolderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(AlbumActivity.this);
                alert.setTitle("Nowy folder");
                alert.setMessage("Podaj nazwe nowego folderu");
                EditText input = new EditText(AlbumActivity.this);
                input.setText("Nowy folder");
                alert.setView(input);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        File pic = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                        File dir1 = new File(pic, "swierczynski/" +  input.getText());
                        dir1.mkdir();
                        refreshDirectory(dir);
                    }
                });
                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                    }
                });

                alert.show();
            }

        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(AlbumActivity.this, Images.class);
                intent.putExtra("folder", adapterView.getItemAtPosition(i).toString());
                startActivity(intent);
                AlertDialog.Builder alert = new AlertDialog.Builder(AlbumActivity.this);
                alert.setTitle(dir.listFiles()[i].getName());

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                    }
                });

                alert.show();
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId() == android.R.id.home){
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}