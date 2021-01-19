package com.example.ruzun.betaqti;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewProfile extends AppCompatActivity {

    Typeface myFont;

    TextView studentName;
    TextView studentNumber;
    TextView studentEmail;
    TextView studentIDExpiryDate;
    TextView studentIDType;

    FirebaseAuth mFirebaseAuth;
    User currentUser;
    ArrayList<User> users = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        //Authentication
        mFirebaseAuth = FirebaseAuth.getInstance();


        //database
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

        //related to font and initilization of textViews
        myFont = Typeface.createFromAsset(this.getAssets(), "fonts/Effra_Md.ttf");

        studentName = (TextView) findViewById(R.id.textView_student_name);
        studentNumber = (TextView) findViewById(R.id.textView_student_number);
        studentEmail = (TextView) findViewById(R.id.textView_student_email);
        studentIDExpiryDate = (TextView) findViewById(R.id.textView_student_ID_expiry_date);
        studentIDType = (TextView) findViewById(R.id.textView_student_ID_type);

        studentName.setTypeface(myFont);
        studentNumber.setTypeface(myFont);
        studentEmail.setTypeface(myFont);
        studentIDExpiryDate.setTypeface(myFont);
        studentIDType.setTypeface(myFont);

        studentName = (TextView) findViewById(R.id.student_name);
        studentNumber = (TextView) findViewById(R.id.student_number);
        studentEmail = (TextView) findViewById(R.id.student_email);
        studentIDExpiryDate = (TextView) findViewById(R.id.student_id_expiry_date);
        studentIDType = (TextView) findViewById(R.id.student_id_type);

        studentName.setTypeface(myFont);
        studentNumber.setTypeface(myFont);
        studentEmail.setTypeface(myFont);
        studentIDExpiryDate.setTypeface(myFont);
        studentIDType.setTypeface(myFont);


        //setting text to textviews







    }

    public void dispaly(){
        User currentUser = null;
        FirebaseUser mFiberbaseUser = mFirebaseAuth.getCurrentUser();

        for (User i: users){

            if (i.getEmail().toLowerCase().equals(mFiberbaseUser.getEmail())){
                currentUser = i;
                break;
            }

        }

        if (currentUser != null){
            studentName.setText(currentUser.getName());
            studentNumber.setText(currentUser.getID());
            studentEmail.setText(currentUser.getEmail());
            studentIDExpiryDate.setText(currentUser.getIDExpiryDay());
            studentIDType.setText(currentUser.getIDType());
        }


    }
}
