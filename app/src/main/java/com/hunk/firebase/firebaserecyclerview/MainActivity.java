package com.hunk.firebase.firebaserecyclerview;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private Button InsertBtn, UsersListBtn;
    private TextView NameTV,AgeTV,EmailTV;

    //Firebase
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        //Android Fields
        InsertBtn =findViewById(R.id.btn_insert_data);
        UsersListBtn =findViewById(R.id.btn_users_list);
        NameTV =findViewById(R.id.user_nameTV);
        AgeTV = findViewById(R.id.user_ageTV);
        EmailTV =findViewById(R.id.user_emailTV);


        InsertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertData();
            }
        });

        UsersListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,UserListActivity.class);
                startActivity(intent);


            }
        });

    }

    private void InsertData() {

        InsertBtn.setEnabled(false);

        //Gathering data from text fields
        String name = NameTV.getText().toString().trim();
        String age =  AgeTV.getText().toString().trim();
        String email = EmailTV.getText().toString().trim();

        //Making a HashMap
        HashMap<String,String> UserData = new HashMap<>();
        UserData.put("name", name);
        UserData.put("age", age);
        UserData.put("email", email);

        //writing the data to real time database
        mDatabase.push().setValue(UserData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Data inserted to database", Toast.LENGTH_SHORT).show();
                    InsertBtn.setEnabled(true);
                    NameTV.setText("");
                    AgeTV.setText("");
                    EmailTV.setText("");
                }else{
                    Toast.makeText(MainActivity.this, "Something went wrong.Try again ", Toast.LENGTH_SHORT).show();
                    InsertBtn.setEnabled(true);
                }

            }
        });

    }
}
