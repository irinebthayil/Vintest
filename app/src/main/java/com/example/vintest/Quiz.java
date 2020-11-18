package com.example.vintest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Quiz extends AppCompatActivity {

    TextView q1, q2, q3;
    RadioButton q1r1, q1r2, q1r3, q2r1, q2r2, q2r3, q3r1, q3r2, q3r3;
    FirebaseFirestore firebaseFirestore;
    String sub;
    Button submit;
    FirebaseAuth mAuth;
    RadioGroup rg1, rg2, rg3;
    int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        sub = getIntent().getStringExtra("sub").toLowerCase();
        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        submit = findViewById(R.id.submit);

        q1 = findViewById(R.id.question1);
        q2 = findViewById(R.id.question2);
        q3 = findViewById(R.id.question3);

        q1r1 = findViewById(R.id.q1r1);
        q1r2 = findViewById(R.id.q1r2);
        q1r3 = findViewById(R.id.q1r3);

        q2r1 = findViewById(R.id.q2r1);
        q2r2 = findViewById(R.id.q2r2);
        q2r3 = findViewById(R.id.q2r3);

        q3r1 = findViewById(R.id.q3r1);
        q3r2 = findViewById(R.id.q3r2);
        q3r3 = findViewById(R.id.q3r3);

        rg1 = findViewById(R.id.q1rg);
        rg2 = findViewById(R.id.q2rg);
        rg3 = findViewById(R.id.q3rg);

        setquestions();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkanswer();
            }
        });

    }

    private void checkanswer() {
        score =0;

        int selectedId = rg1.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) findViewById(selectedId);
        String ans1 = radioButton.getText().toString();

        int selectedId2 = rg2.getCheckedRadioButtonId();
        RadioButton radioButton2 = (RadioButton) findViewById(selectedId2);
        String ans2 = radioButton2.getText().toString();

        int selectedId3 = rg3.getCheckedRadioButtonId();
        RadioButton radioButton3 = (RadioButton) findViewById(selectedId3);
        String ans3 = radioButton3.getText().toString();

        firebaseFirestore.collection(sub).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful())
                {
                    QuerySnapshot querySnapshot = task.getResult();
                    if (querySnapshot!=null) {

                        List<DocumentSnapshot> documentSnapshots = querySnapshot.getDocuments();
                        if (ans1.equals(documentSnapshots.get(0).getString("ans")))
                            score++;
                        if (ans2.equals(documentSnapshots.get(1).getString("ans")))
                            score++;
                        if (ans3.equals(documentSnapshots.get(2).getString("ans")))
                            score++;

                        Map<String, Object> s2 = new HashMap<>();
                        s2.put(sub, score+"/3");

                        firebaseFirestore.collection("scores").document(mAuth.getCurrentUser().getUid()).set(s2);
                        //firebaseFirestore.collection("scores").document(mAuth.getCurrentUser().getUid()).collection(sub).document("s").set(s);

                        score f = new score();
                        Bundle b = new Bundle();
                        b.putString("sub", sub);
                        f.setArguments(b);
                        getSupportFragmentManager().beginTransaction().add(R.id.c, f).addToBackStack(null).commit();
                    }

                }
            }
        });



    }

    private void setquestions() {

        firebaseFirestore.collection(sub).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful())
                {
                    QuerySnapshot querySnapshot = task.getResult();
                    if (querySnapshot!=null)
                    {
                        List<DocumentSnapshot> documentSnapshots = querySnapshot.getDocuments();
                        q1.setText(documentSnapshots.get(0).getString("q"));
                        ArrayList<String> a = (ArrayList<String>) documentSnapshots.get(0).get("options");
                        q1r1.setText(a.get(0));
                        q1r2.setText(a.get(1));
                        q1r3.setText(a.get(2));

                        q2.setText(documentSnapshots.get(1).getString("q"));
                        ArrayList<String> a2 = (ArrayList<String>) documentSnapshots.get(1).get("options");
                        q2r1.setText(a2.get(0));
                        q2r2.setText(a2.get(1));
                        q2r3.setText(a2.get(2));

                        q3.setText(documentSnapshots.get(2).getString("q"));
                        ArrayList<String> a3 = (ArrayList<String>) documentSnapshots.get(2).get("options");
                        q3r1.setText(a3.get(0));
                        q3r2.setText(a3.get(1));
                        q3r3.setText(a3.get(2));
                    }
                    else
                    {
                        Toast.makeText(Quiz.this, "Sorry an error occurred!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

}