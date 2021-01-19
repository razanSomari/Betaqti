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

public class Appointments extends AppCompatActivity {

    String userID;
    ArrayList<User> users = new ArrayList<User>();

    ArrayList<Appointment> appointments = new ArrayList<Appointment>();

    User currentUser2;
    FirebaseAuth mFirebaseAuth;

    ListView lv;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);
        mFirebaseAuth = FirebaseAuth.getInstance();

        lv = (ListView) findViewById(R.id.list3);

        //initialization of attributes related to database
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

        databaseReference.child("Appointment").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Appointment appointment = snapshot.getValue(Appointment.class);
                    if(id.length()!=0){
                        if(id.equals(appointment.getId()))
                            appointments.add(appointment);
                    }
                    list();
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

            if (i.getEmail().equals(mFiberbaseUser.getEmail())){
                currentUser = i;
                break;
            }

        }

        if (currentUser != null){
            id = currentUser.getID();
        }


    }

    public void list(){

        RequestAdapter3 adapter = new RequestAdapter3<Request>(Appointments.this,appointments);
        lv.setAdapter(adapter);
    }
}
