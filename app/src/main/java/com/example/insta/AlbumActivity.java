package com.example.insta;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.io.File;
import java.util.Arrays;

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

        File pic = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES );
        File dir = new File(pic, "swierczynski");
        pic = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES + "/swierczynski" );
        dir = new File(pic, "folder1"); dir.mkdir();
        dir = new File(pic, "folder2"); dir.mkdir();
        dir = new File(pic, "folder3"); dir.mkdir();
        File[] files = pic.listFiles();
        Arrays.sort(files); // sortowanie plików wg nazwy
        Log.d("folders", "=====================\n\n" + files.toString() +"\n\n=====================");
        for (File file : pic.listFiles()){
            Log.d("file: ", file.getName());
        }// tablica przechowująca testowe dane

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        listView = findViewById(R.id.listViewID);
        refreshDirectory(dir);

        File finalPic = pic;
        File finalDir = dir;
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
                        File toRemove = new File(finalPic, adapterView.getItemAtPosition(i).toString());
                        toRemove.delete();
                        refreshDirectory(finalDir);
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
    }
}