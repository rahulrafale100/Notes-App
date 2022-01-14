package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    ListView myList;
     static ArrayList<String> notes=new ArrayList<String>();
     static ArrayAdapter<String> arrayAdapter;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myList=findViewById(R.id.listView);
        sharedPreferences=getApplicationContext().getSharedPreferences("com.example.notesapp", Context.MODE_PRIVATE);
        HashSet<String> set=(HashSet<String>) sharedPreferences.getStringSet("notes",null);
        if(set==null){
            notes.add("Do home work");
            notes.add("Do home work");
        }
        else{
            notes= new ArrayList(set);
        }

        arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,notes);
        myList.setAdapter(arrayAdapter);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getApplicationContext(),NoteEditorActivity.class);
                intent.putExtra("noteId",i);
                startActivity(intent);
            }
        });
        myList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                int itm=i;
                new AlertDialog.Builder(MainActivity.this).setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Caution!!!")
                        .setMessage("Are your sure want to delete it")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                notes.remove(itm);
                                arrayAdapter.notifyDataSetChanged();

                                HashSet<String> hashSet=new HashSet<>(MainActivity.notes);
                                sharedPreferences.edit().putStringSet("notes",hashSet).apply();
                            }
                        })
                        .setNegativeButton("No",null)
                        .show();
                return true;
            };
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId()==R.id.add){
            Intent intent=new Intent(getApplicationContext(),NoteEditorActivity.class);
            startActivity(intent);
            return true;
        }
return false;
    }
}