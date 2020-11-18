package com.example.vintest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    ArrayList<String> subjectname = new ArrayList<>();
    ArrayList<String> subjectdesc = new ArrayList<>();
    ArrayList<Integer> subjectimage = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setsubjects();
    }

    private void setsubjects() {

        subjectname.add("Java");
        subjectdesc.add("Take the Java Quiz to test your skills in basic Java programming.");
        subjectimage.add(R.drawable.javalogo);

        subjectname.add("Android");
        subjectdesc.add("Show off your Android Development knowledge here!");
        subjectimage.add(R.drawable.androidlogo);

        subjectname.add("Python");
        subjectdesc.add("Check your Python learning progress and to test your skills!");
        subjectimage.add(R.drawable.pythonlogo);

        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        SubjectsRecyclerViewAdapter adapter = new SubjectsRecyclerViewAdapter(this, subjectname, subjectdesc, subjectimage);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.signout) {
            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(Home.this, MainActivity.class);
            startActivity(i);
        }

        return true;
    }

}