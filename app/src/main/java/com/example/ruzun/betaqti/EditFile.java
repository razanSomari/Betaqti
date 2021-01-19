package com.example.ruzun.betaqti;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class EditFile extends AppCompatActivity {


    //declaration attributes

    String userID;
    ArrayList<User> users = new ArrayList<User>();

    User currentUser2;
    FirebaseAuth mFirebaseAuth;

    Button UpdateButton;
    private EditText name, email, number, IDType, IDExpiryDate;

    static Typeface myFont;

    TextView studentName;
    TextView studentNumber;
    TextView studentEmail;
    TextView studentIDExpiryDate;
    TextView studentIDType;

    //----------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_file);

        //initializing of editText
        name = (EditText) findViewById(R.id.editText_name);
        email = (EditText) findViewById(R.id.editText_email);
        number = (EditText) findViewById(R.id.editText_number);
        IDType = (EditText) findViewById(R.id.editText_IDType);
        IDExpiryDate = (EditText) findViewById(R.id.editText_IDexpiryDate);

        studentName = findViewById(R.id.textView_name);
        studentNumber = findViewById(R.id.textView_number);
        studentEmail =findViewById(R.id.textView_email);
        studentIDExpiryDate =findViewById(R.id.textView_IDexpiryDate);
        studentIDType =findViewById(R.id.textView_IDType);

        myFont = Typeface.createFromAsset(this.getAssets(), "fonts/Effra_Md.ttf");

        studentName.setTypeface(myFont);
        studentNumber.setTypeface(myFont);
        studentEmail.setTypeface(myFont);
        studentIDExpiryDate.setTypeface(myFont);
        studentIDType.setTypeface(myFont);
        name.setTypeface(myFont);
        email.setTypeface(myFont);
        number.setTypeface(myFont);
        IDType.setTypeface(myFont);
        IDExpiryDate.setTypeface(myFont);



        //setting

        /*
        email.setText(currentUser.getEmail());
        number.setText(currentUser.getID());
        IDType.setText(currentUser.getIDType());
        IDExpiryDate.setText(currentUser.getIDExpiryDay());
         */



        //initialization of attributes related to authentication
        mFirebaseAuth = FirebaseAuth.getInstance();

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



        //implementing handling click button
        UpdateButton = (Button) findViewById(R.id.update_button);
        UpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser2 != null){
                    updateData();
                }
            }
        });



        UpdateButton.setTypeface(myFont);

    }



//----------------------------------------------------------------------
    public void updateData(){

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("User").child(currentUser2.getUserID());
        mDatabase.child("name").setValue(name.getText().toString());
        mDatabase.child("email").setValue(email.getText().toString());
        mDatabase.child("id").setValue(number.getText().toString());
        mDatabase.child("idtype").setValue(IDType.getText().toString());
        mDatabase.child("idexpiryDay").setValue(IDExpiryDate.getText().toString());

        Toast.makeText(EditFile.this, "تم حفظ التعديلات", Toast.LENGTH_SHORT).show();
    }


//----------------------------------------------------------------------

    public void dispaly(){
        User currentUser = null;
        FirebaseUser mFiberbaseUser = mFirebaseAuth.getCurrentUser();

        for (User i: users){

            if (i.getEmail().toLowerCase().equals(mFiberbaseUser.getEmail().toLowerCase())){
                currentUser = i;
                break;
            }

        }

        if (currentUser != null){
            currentUser2 = currentUser;
            name.setText(currentUser.getName());
            email.setText(currentUser.getEmail());
            number.setText(currentUser.getID());
            IDType.setText(currentUser.getIDType());
            IDExpiryDate.setText(currentUser.getIDExpiryDay());
        }


    }

}
