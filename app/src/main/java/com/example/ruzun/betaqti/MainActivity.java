package com.example.ruzun.betaqti;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import net.alhazmy13.hijridatepicker.date.hijri.HijriDatePickerDialog;

import java.util.Calendar;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,HijriDatePickerDialog.OnDateSetListener {


    Typeface myFont;

    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextID;
    private EditText editTextIDexpiryDate;
    private EditText editTextIDType;
    private EditText editTextName;
    private TextView title;
    private TextView label;
    Button buttonReg;


    TextView textViewSignIn;

    Context context;

    FirebaseAuth mFirebaseAuth;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Spinner IDType;
    User user;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner dropdown = findViewById(R.id.spinner1);
        String[] items = new String[]{"هوية وطنية", "بطاقة العائلة"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        label = findViewById(R.id.typelable);

        //initializition
        context = this;
        mFirebaseAuth = FirebaseAuth.getInstance();

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewSignIn = (TextView) findViewById(R.id.textViewSignIn);
        editTextID = (EditText) findViewById(R.id.editTextID);
        editTextIDexpiryDate = (EditText) findViewById(R.id.editTextExpiryDate);
        editTextName = (EditText) findViewById(R.id.editTextName);

        IDType = findViewById(R.id.spinner1);

        buttonReg = (Button) findViewById(R.id.buttonReg);
        myFont = Typeface.createFromAsset(this.getAssets(), "fonts/Effra_Md.ttf");

        label.setTypeface(myFont);
        textViewSignIn.setTypeface(myFont);
        editTextID.setTypeface(myFont);
        editTextIDexpiryDate.setTypeface(myFont);
        buttonReg.setTypeface(myFont);
        editTextEmail.setTypeface(myFont);
        editTextPassword.setTypeface(myFont);
        editTextName.setTypeface(myFont);

         firebaseDatabase = FirebaseDatabase.getInstance();
         databaseReference = firebaseDatabase.getReference();


        //set click listener for the button
        buttonReg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                //specifiy all the user's input cases
                if (email.isEmpty()) {
                    Toast.makeText(context, "الرجاء ادخال بريد الكتروني", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(context, "الرجاء ادخال كلمة المرور", Toast.LENGTH_SHORT).show();
                } else if (email.isEmpty() && password.isEmpty()) {
                    Toast.makeText(context, "الحقول فارغة", Toast.LENGTH_SHORT).show();
                } else if (!(email.isEmpty() && password.isEmpty())) {

                    //
                    mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful())
                                Toast.makeText(context, "يوجد شيء خاطئ", Toast.LENGTH_SHORT).show();
                            else {
                                user = new User(editTextEmail.getText().toString(), editTextPassword.getText().toString(), editTextName.getText().toString(),
                                        editTextID.getText().toString(), editTextIDexpiryDate.getText().toString(), IDType.getSelectedItem().toString());
                                String key = databaseReference.child("User").push().getKey();
                                user.setUserID(key);
                                databaseReference.child("User").child(key).setValue(user);
                                Toast.makeText(MainActivity.this,"تم انشاء حساب بنجاح",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(MainActivity.this, HomeActivity.class));

                            }
                        }
                    });
                }
                else {
                    Toast.makeText(context, "يوجد شيء خاطئ", Toast.LENGTH_SHORT).show();
                }


            }
        });


        textViewSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LogInActivity.class));
            }
        });

    }

    public void showDialog(View view){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    public void showHijiri(View view){
        UmmalquraCalendar now = new UmmalquraCalendar();
        HijriDatePickerDialog dpd = HijriDatePickerDialog.newInstance(
                MainActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setLocale(new Locale("ar"));
        dpd.show(getFragmentManager(),"weew");
    }



    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

        String date = ""+i+"   "+i1+"    "+i2;
        editTextIDexpiryDate.setText(date);
    }


    @Override
    public void onDateSet(HijriDatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = ""+year+"   "+monthOfYear+"    "+dayOfMonth;
        editTextIDexpiryDate.setText(date);
    }
}
