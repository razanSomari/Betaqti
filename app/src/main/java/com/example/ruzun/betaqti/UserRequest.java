package com.example.ruzun.betaqti;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserRequest extends AppCompatActivity {
    private ListView lv;
    ArrayList<Request> requests = new ArrayList<Request>();
    RequestAdapter2<Request> adapter;


    FirebaseAuth mFirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_request);
        lv = (ListView) findViewById(R.id.list2);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser mFiberbaseUser = mFirebaseAuth.getCurrentUser();
        final String email = mFiberbaseUser.getEmail().toLowerCase();
        databaseReference.child("Request").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                requests.removeAll(requests);
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Request request = snapshot.getValue(Request.class);
                    if(email!=null && request.getEmail()!=null)
                    {
                        if(request.getEmail().equals(email))
                            requests.add(request);
                    }
                }
                display();
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void display(){
        adapter = new RequestAdapter2<Request>(UserRequest.this,requests);
        lv.setAdapter(adapter);

    }
}
