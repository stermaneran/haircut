package com.example.user.eran;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Sefi on 21/12/2017.
 */

public class AppointmentsListAdapter extends RecyclerView.Adapter<AppointmentsListAdapter.AppointmentsListViewHolder> {


    private List<AppointmentDisplayItem> _appList;
    private Context mContext;


    protected AppointmentsListAdapter(List<AppointmentDisplayItem> appList, Context context){
        _appList = appList;
        mContext = context;
    }

    @Override
    public AppointmentsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointmentlist_item, parent, false);
        return new AppointmentsListViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final AppointmentsListViewHolder holder, int position) {
        final AppointmentDisplayItem app = _appList.get(position);
        holder.addDate.setText("Date: " + app.get_date());
        holder.appTime.setText("Time: " + app.get_time() +"$");
        holder.customer.setText("Customer: " + app.get_custFullName());
        holder.haircutStyle.setText("Haircut: " + app.get_haircusStyle());
        holder.appDuration.setText("Appointment duration requierd: " + app.get_appDuration() + "min");
    }

    @Override
    public int getItemCount() {
        return _appList.size();
    }

    protected class AppointmentsListViewHolder extends RecyclerView.ViewHolder {

        TextView addDate;
        TextView appTime;
        TextView customer;
        TextView haircutStyle;
        TextView appDuration;


        public AppointmentsListViewHolder(View itemView) {
            super(itemView);
            addDate = (TextView) itemView.findViewById(R.id.addDate);
            appTime = (TextView) itemView.findViewById(R.id.appTime);
            customer = (TextView) itemView.findViewById(R.id.customer);
            haircutStyle = (TextView) itemView.findViewById(R.id.haircutStyle);
            appDuration = (TextView) itemView.findViewById(R.id.haircutStyle);
        }

    }

}