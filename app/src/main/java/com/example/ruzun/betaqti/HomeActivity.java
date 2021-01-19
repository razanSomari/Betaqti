package com.example.ruzun.betaqti;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    //declaration attributes
    public Context context;
    private javax.mail.Session session;
    private ProgressDialog progressDialog;


    User currentUser;
    ArrayList<User> users = new ArrayList<User>();
    String email= "rzoon.7227@hotmail.com", subject= "طلب", TextMessage;

    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    TextView textViewCreatCard, textViewResetPassword, textViewReprocessData, textViewBankDataUpdate,
            textViewMovingDate, textViewActivateCard, textViewRecreateCard, clickedView;

    Typeface myFont;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    NavigationView navigationView;
    DatabaseReference databaseReference;


//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //setting up sign out
        setUpFirebaseListener();

        //initialization of attributes related to menu
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        navigationView = (NavigationView)  findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //initialization of attributes related to authentication
        mFirebaseAuth = FirebaseAuth.getInstance();

        //initialization of attributes related to database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
         databaseReference = firebaseDatabase.getReference();

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

        // initialization & setting font & setting on click listener to textViews
        myFont = Typeface.createFromAsset(this.getAssets(), "fonts/Effra_Md.ttf");


        textViewCreatCard = (TextView) findViewById(R.id.textViewCreateCard);
        textViewResetPassword = (TextView) findViewById(R.id.textViewResetPassword);
        textViewReprocessData = (TextView) findViewById(R.id.textViewReprocessData);
        textViewBankDataUpdate = (TextView) findViewById(R.id.textViewBankDataUpdate);
        textViewMovingDate = (TextView) findViewById(R.id.textViewMovingDate);
        textViewActivateCard = (TextView) findViewById(R.id.textViewActivateCard);
        textViewRecreateCard = (TextView) findViewById(R.id.textViewRecreateCard);

        textViewRecreateCard.setTypeface(myFont);
        textViewActivateCard.setTypeface(myFont);
        textViewMovingDate.setTypeface(myFont);
        textViewBankDataUpdate.setTypeface(myFont);
        textViewReprocessData.setTypeface(myFont);
        textViewResetPassword.setTypeface(myFont);
        textViewCreatCard.setTypeface(myFont);

        textViewRecreateCard.setOnClickListener(this);
        textViewActivateCard.setOnClickListener(this);
        textViewMovingDate.setOnClickListener(this);
        textViewBankDataUpdate.setOnClickListener(this);
        textViewReprocessData.setOnClickListener(this);
        textViewResetPassword.setOnClickListener(this);
        textViewCreatCard.setOnClickListener(this);

        context = this;



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

        if (currentUser != null)
        {
            TextMessage =currentUser.toString();
            username = currentUser.getName();
            email2 = currentUser.getEmail();
        }


    }

String username;
    String email2;
//----------------------------------------------------------------------
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {

        clickedView = (TextView) v;

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        session = javax.mail.Session.getDefaultInstance(props, new javax.mail.Authenticator(){
            protected javax.mail.PasswordAuthentication getPasswordAuthentication(){
                return new javax.mail.PasswordAuthentication(Config.Email, Config.PASSWORD);
            }
        });

        progressDialog = ProgressDialog.show(context, " ","جاري ارسال الطلب", true);

        RetriveTask task = new RetriveTask();
        task.execute();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();

        Request request = new Request(clickedView.getText().toString(),formatter.format(date), username,email2);
        String key = databaseReference.child("Request").push().getKey();
        request.setId(key);
        databaseReference.child("Request").child(key).setValue(request);

    }



//----------------------------------------------------------------------

    class RetriveTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... strings) {

            try{
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("emailsendapptest@gmail.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
                message.setSubject(subject);
                message.setContent(TextMessage+" <br /> "+"نوع الطلب: "+ clickedView.getText(), "text/html; charset=utf-8");
                Transport.send(message);
            }
            catch(MessagingException e){
                e.printStackTrace();
            }
            catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String aVoid){
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            Toast.makeText(context, "تم ارسال الطلب", Toast.LENGTH_LONG).show();
        }


    }


//----------------------------------------------------------------------
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int item_id = menuItem.getItemId();

        switch (item_id){
            case R.id.view_file: startActivity(new Intent(HomeActivity.this, ViewProfile.class));
                break;
            case R.id.edit_file:
                Intent intent = new Intent(HomeActivity.this, EditFile.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Current User", (Serializable) currentUser);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.sign_out:
                new AlertDialog.Builder(this,R.style.AlertDialogStyle)
                        .setMessage("هل أنت متأكد من تسجيل الخروج؟")
                        .setPositiveButton("نعم", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                FirebaseAuth.getInstance().signOut();
                                Toast.makeText(HomeActivity.this, "تم تسجيل الخروج بنجاح", Toast.LENGTH_SHORT).show();
                            }})
                        .setNegativeButton("لا", null).show();
                break;
            case R.id.myRequest:
                Intent intent2 = new Intent(HomeActivity.this, UserRequest.class);
                Bundle bundle2= new Bundle();
                bundle2.putSerializable("Current User", (Serializable) currentUser);
                intent2.putExtras(bundle2);
                startActivity(intent2);
                break;
            case R.id.setting:
                startActivity(new Intent(HomeActivity.this, Setting.class));
                break;
            case R.id.myAppointment:
                Intent intent3 = new Intent(HomeActivity.this, Appointments.class);
                startActivity(intent3);
                break;
            case R.id.help:
                new AlertDialog.Builder(this,R.style.AlertDialogStyle)
                        .setMessage(". كيف أطلب خدمة؟\n" +
                                "من القائمة الرئيسية انقر على احد الخدمة التي ترغب بها وسيتم ارسالها الى شؤون الموظفين\n" +
                                "\n" +
                                "٢.كيف أتحقق من إنهاء إجراءات خدمتي؟  \n" +
                                "من القائمة الجانبية انقر على سجل طلباتي، وسيتم عرض قائمة بجميع طلباتك وحالاتها \n" +
                                "\n" +
                                "٣. كيف أتحقق من مواعيدي؟\n" +
                                "من القائمة الجانبية انقر على مواعيدي وسيتم عرض طلبات الحضور أو أي مواعيد تخص شؤون الموظفين")
                        .setPositiveButton("موافق", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {

                            }})
                        .setNegativeButton("الغاء", null).show();
                break;



        }
        return false;
    }


//----------------------------------------------------------------------
    @Override
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

//----------------------------------------------------------------------
    private void setUpFirebaseListener(){
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user ==null ){
                    Intent intent = new Intent(HomeActivity.this, LogInActivity.class);
                    startActivity(intent);
                }


            }
        };
    }
//-----------------------------------------------------------------------






}
