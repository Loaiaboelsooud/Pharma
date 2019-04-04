package com.example.loaiaboelsooud.pharma;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class prescriptionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    String[] items;
    User user;

    public prescriptionsAdapter(Context context, String[] items) {

        this.context = context;
        this.items = items;

    }

    @Override
    public prescriptionsAdapter.Prescriptions onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.prescriptions_adapter_content, parent, false);
        Prescriptions prescriptions = new Prescriptions(row);
        return prescriptions;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        ((Prescriptions) holder).userName.setText(items[position]);
        // ((Item) holder).stemPressingBox.setText(items[position]);
        //((Item) holder).dryCleanBox.setText(items[position]);
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    public class Prescriptions extends RecyclerView.ViewHolder {
        TextView userName;
        ImageView avatar;
        TextView textView2;


        public Prescriptions(View prescriptionsView) {
            super(prescriptionsView);
            //  userName = prescriptionsView.findViewById(R.id.presecription_user_name);
            // avatar = prescriptionsView.findViewById(R.id.presecription_profile_picture);
            //textView2 = prescriptionsView.findViewById(R.id.name2);
        }
    }
}