package com.example.ruzun.betaqti;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Records extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ListView lv;
    ArrayList<Request> requests = new ArrayList<Request>();
    List<String> your_array_list = new ArrayList<String>();
    ArrayList<Request> backUpRequests = new ArrayList<Request>();
    RequestAdapter<Request> adapter;

    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    User currentUser2;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    NavigationView navigationView;
    int size=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        lv = (ListView) findViewById(R.id.list);

        mFirebaseAuth = FirebaseAuth.getInstance();

        setUpFirebaseListener();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer2);
        navigationView = (NavigationView)  findViewById(R.id.navigation_view2);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //initialization of attributes related to database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        databaseReference.child("Request").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                requests.removeAll(requests);
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Request request = snapshot.getValue(Request.class);
                    if(!request.getState())
                        requests.add(request);
                    size++;
                }
                display();
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        // Instanciating an array list (you don't need to do this,
        // you already have yours).


        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.

    }

    public void display(){
        adapter = new RequestAdapter<Request>(Records.this,requests);
        lv.setAdapter(adapter);
    }

    public void undo(View view){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Request").child(RequestAdapter.lastUpdate.getId());
        mDatabase.child("state").setValue(false);

        Toast.makeText(Records.this, "تم التراجع", Toast.LENGTH_SHORT).show();
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int item_id = menuItem.getItemId();

        switch (item_id){
            case R.id.requests: startActivity(new Intent(Records.this, Records.class));
                break;
            case R.id.UsersAppointment:
                Intent intent = new Intent(Records.this, UserAppoints.class);
                startActivity(intent);
                break;
            case R.id.sign_out:
                new AlertDialog.Builder(this,R.style.AlertDialogStyle)
                        .setMessage("هل أنت متأكد من تسجيل الخروج؟")
                        .setPositiveButton("نعم", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                FirebaseAuth.getInstance().signOut();
                                Toast.makeText(Records.this, "تم تسجيل الخروج بنجاح", Toast.LENGTH_SHORT).show();
                            }})
                        .setNegativeButton("لا", null).show();
                break;
        }
        return false;
    }

    private void setUpFirebaseListener(){
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user ==null ){
                    Intent intent = new Intent(Records.this, LogInActivity.class);
                    startActivity(intent);
                }


            }
        };
    }

    public  boolean onOptionsItemSelected(MenuItem item){
        if (mActionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //----------------------------------------------------------------------
    public void onStart(){
        super.onStart();

        FirebaseAuth.getInstance().addAuthStateListener(mAuthStateListener);
    }

    //----------------------------------------------------------------------
    @Override
    public void onStop(){
        super.onStop();
        if(mAuthStateListener!=null)
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthStateListener);
    }


}
