package com.surbhi100.noted;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.HashSet;

public class Texteditor extends AppCompatActivity {
    private static final int PREMISSION_REQUEST = 0;
    private static final int RESULT_LOAD_IMAGE = 1;
    ImageView imageView;
    int noteId;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_texteditor);
        if(Build.VERSION.SDK_INT> Build.VERSION_CODES.M&&checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE )
               != PackageManager.PERMISSION_GRANTED)
        {
              requestPermissions(new  String[] {Manifest.permission.READ_EXTERNAL_STORAGE},PREMISSION_REQUEST);
        }
editText = findViewById(R.id.editText);
        FloatingActionButton fab2 = findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Texteditor.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to save this note?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MainActivity.notes.add(noteId, String.valueOf(editText.getText().toString()));
                                MainActivity.arrayAdapter.notifyDataSetChanged();
                                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.surbhi100.noted", Context.MODE_PRIVATE);
                                HashSet<String> set = new HashSet<>(MainActivity.notes);
                                sharedPreferences.edit().putStringSet("notes", set).apply();
                                Toast.makeText(Texteditor.this,"Saved",Toast.LENGTH_SHORT).show();
                                Texteditor.super.onBackPressed();

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                Texteditor.super.onBackPressed();
                            }
                        })
                        .show();
            }
        });


    }


}


