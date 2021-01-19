package com.example.ruzun.betaqti;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MessageActivity extends AppCompatActivity {

    TextView textViewMessage;
    User currentUser;
    ArrayList<User> users = new ArrayList<User>();
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        textViewMessage = (TextView) findViewById(R.id.textViewMessage);

        mFirebaseAuth = FirebaseAuth.getInstance();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        databaseReference.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    users.add(user);
                    dispaly();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void dispaly(){
        User currentUser = null;
        FirebaseUser mFiberbaseUser = mFirebaseAuth.getCurrentUser();

        for (User i: users){
            textViewMessage.setText(i.getEmail() +"   " + mFiberbaseUser.getEmail());

          if (i.getEmail().equals(mFiberbaseUser.getEmail())){
              currentUser = i;
              break;
          }

        }

        if (currentUser != null)
        textViewMessage.setText(currentUser.toString());
        else
            textViewMessage.setText("no shuch user");
    }
}
