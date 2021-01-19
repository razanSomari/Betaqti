package com.example.ruzun.betaqti;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class RequestAdapter <T> extends ArrayAdapter<Request> {

    public static Request lastUpdate;
    public RequestAdapter(@NonNull Context context, @NonNull List<Request> objects) {
        super(context, 0, objects);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView==null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }


        final Request currentRequest = getItem(position);

        TextView requester =  listItemView.findViewById(R.id.requester);

        requester.setText(currentRequest.getRequester());

        TextView type = (TextView) listItemView.findViewById(R.id.type);

        type.setText(currentRequest.getRequestName());

        CheckBox state = (CheckBox) listItemView.findViewById(R.id.state);

            state.setChecked(currentRequest.getState());

        state.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                lastUpdate = currentRequest;
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Request").child(currentRequest.getId());
                mDatabase.child("state").setValue(true);

                Toast.makeText(getContext(), "تم حفظ التعديلات", Toast.LENGTH_SHORT).show();
            }
        });

        return listItemView;

    }

}
