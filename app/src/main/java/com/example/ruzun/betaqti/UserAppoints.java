package com.example.ruzun.betaqti;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class UserAppoints extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private TextView time_text;
    private TextView id;
    private TextView content;
    private ImageView addButton;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_appoints);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        time_text = findViewById(R.id.time);
        ImageView button = findViewById(R.id.button1);

        id = findViewById(R.id.idnumber);
        content = findViewById(R.id.contentofAppoint);

        addButton = findViewById(R.id.addApoointments);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id.getText().toString().length()==0||content.getText().toString().length()==0||time_text.getText().toString().length()==0)
                {
                    Toast.makeText(UserAppoints.this, "الرجاء ملئ جميع الخانات",Toast.LENGTH_LONG).show();

                }
                else{
                    Appointment appointment = new Appointment(id.getText().toString(), content.getText().toString(), time_text.getText().toString());
                    String key = databaseReference.child("Appointment").push().getKey();
                    databaseReference.child("Appointment").child(key).setValue(appointment);
                    Toast.makeText(UserAppoints.this, "تم تحديد الموعد بنجاح",Toast.LENGTH_LONG).show();
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }

    private void showDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

        String date = ""+i+"   "+i1+"    "+i2;
        time_text.setText(date);
    }
}
