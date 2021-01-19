package com.example.ruzun.betaqti;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    Button buttonSignIn;
    TextView textViewSignUp;
    Context context;
    FirebaseAuth mFirebaseAuth;
    Typeface myFont;

     final String ERROR_MESSAGE = "The password is invalid or the user does not have a password.";
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        context = this;

        mFirebaseAuth = FirebaseAuth.getInstance();

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewSignUp = (TextView) findViewById(R.id.textViewSignUp);
        buttonSignIn = (Button) findViewById(R.id.buttonSignIn);

        myFont = Typeface.createFromAsset(this.getAssets(), "fonts/Effra_Md.ttf");

        textViewSignUp.setTypeface(myFont);
        editTextEmail.setTypeface(myFont);
        editTextPassword.setTypeface(myFont);
        buttonSignIn.setTypeface(myFont);



        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            FirebaseUser mFiberbaseUser = mFirebaseAuth.getCurrentUser();


            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (mFiberbaseUser != null) {
                    Toast.makeText(LogInActivity.this, "تم تسجيل الدخول بنجاح", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LogInActivity.this, HomeActivity.class));
                }
                else
                    Toast.makeText(LogInActivity.this, "يوجد شيء خاطئ", Toast.LENGTH_SHORT).show();
            }
        };


        buttonSignIn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                 final String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if (email.isEmpty()) {
                    Toast.makeText(context, "الرجاء ادخال البريد", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(context, "الرجاء ادخال كلمة مرور", Toast.LENGTH_SHORT).show();
                } else if (email.isEmpty() && password.isEmpty()) {
                    Toast.makeText(context, "الحقول فارغة", Toast.LENGTH_SHORT).show();
                } else if (!(email.isEmpty() && password.isEmpty())) {

                    mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful())
                            {

                                if(task.getException().getMessage().equals(ERROR_MESSAGE))
                                Toast.makeText(context,  "كلمة المرور خاطئة", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(context,  "يوجد شيء خاطئ", Toast.LENGTH_SHORT).show();

                            }
                            else{
                                Toast.makeText(context, "تم تسجيل الدخول بنجاح", Toast.LENGTH_SHORT).show();
                                if ( email.equals("Admin@hotmail.com") ){
                                    startActivity(new Intent(LogInActivity.this, Records.class));
                                }
                                else
                                    startActivity(new Intent(LogInActivity.this, HomeActivity.class));
                            }


                        }
                    });
                }
                else {
                    Toast.makeText(context, "يوجد شيء خاطئ", Toast.LENGTH_SHORT).show();
                }


            }
        });

        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this, MainActivity.class));
            }
        });
    }

   /*
    @Override
    protected void onStart(){
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    */
}
