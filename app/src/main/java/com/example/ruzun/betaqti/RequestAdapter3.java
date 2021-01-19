package com.example.ruzun.betaqti;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class RequestAdapter3<T> extends ArrayAdapter<Appointment> {

    public RequestAdapter3(@NonNull Context context, @NonNull List<Appointment> objects) {
        super(context, 0, objects);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView==null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item3, parent, false);
        }


        final Appointment appointment = getItem(position);

        TextView requester =  listItemView.findViewById(R.id.dateOfApp);

        requester.setText(appointment.getDate());

        TextView type = (TextView) listItemView.findViewById(R.id.contentOfApp);

        type.setText(appointment.getContent());

        TextView state = (TextView) listItemView.findViewById(R.id.stateOfOrder);



        return listItemView;

    }

}
