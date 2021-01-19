package com.example.ruzun.betaqti;

import android.content.Context;
import android.graphics.Typeface;
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

import java.util.List;


public class RequestAdapter2<T> extends ArrayAdapter<Request> {

    public RequestAdapter2(@NonNull Context context, @NonNull List<Request> objects) {
        super(context, 0, objects);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView==null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item2, parent, false);
        }


        final Request currentRequest = getItem(position);

        TextView requester =  listItemView.findViewById(R.id.dateOfOrder);

        requester.setText(currentRequest.getRequestDate());

        TextView type = (TextView) listItemView.findViewById(R.id.typeOfOrder);

        type.setText(currentRequest.getRequestName());

        TextView state = (TextView) listItemView.findViewById(R.id.stateOfOrder);

        if (currentRequest.getState())
            state.setText("تام");
        else
            state.setText("تحت الاجراء");

        return listItemView;

    }

}
