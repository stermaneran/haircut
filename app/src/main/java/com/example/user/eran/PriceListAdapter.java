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
 * adapts the displayed item to the list
 */

public class PriceListAdapter extends RecyclerView.Adapter<PriceListAdapter.PriceListViewHolder> {


    private List<Haircut> _haircutsList;
    private Context mContext;

    /**
     * ctor
     *
     * @param haircutsList list of haircut items
     * @param context      current state of the application
     */
    protected PriceListAdapter(List<Haircut> haircutsList, Context context) {
        _haircutsList = haircutsList;
        mContext = context;
    }

    @Override
    public PriceListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pricelist_item, parent, false);
        return new PriceListViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final PriceListViewHolder holder, int position) {
        final Haircut haircutItem = _haircutsList.get(position);
        holder.haircutName.setText(haircutItem._name);
        holder.haircutPrice.setText("Price: " + haircutItem._price + "$");
        holder.haircutDuration.setText("Appointment duration requierd: " + haircutItem._duration + " minutes");
    }

    @Override
    public int getItemCount() {
        return _haircutsList.size();
    }

    /**
     * represents the object we show in the list
     */
    protected class PriceListViewHolder extends RecyclerView.ViewHolder {

        TextView haircutName;
        TextView haircutPrice;
        TextView haircutDuration;

        /**
         * ctor
         *
         * @param itemView
         */
        public PriceListViewHolder(View itemView) {
            super(itemView);
            haircutName = (TextView) itemView.findViewById(R.id.haircutName);
            haircutPrice = (TextView) itemView.findViewById(R.id.customer);
            haircutDuration = (TextView) itemView.findViewById(R.id.haircutStyle);
        }

    }

}