package com.example.sdmonitorapp.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sdmonitorapp.R;
import com.example.sdmonitorapp.models.Sensor;

public class SensorRecyclerViewAdapter extends RecyclerView.Adapter<SensorRecyclerViewAdapter.ViewHolder> {

    private Sensor[] mData;
    private LayoutInflater mInflater;
    private SensorRecyclerViewAdapter.ItemClickListener mClickListener;


            // data is passed into the constructor
    SensorRecyclerViewAdapter(Context context, Sensor[] data) {
            this.mInflater = LayoutInflater.from(context);
            this.mData = data;
    }

    public void changeDataSet(Sensor[] data) {
            mData = data;
            notifyDataSetChanged();
    }

    // inflates the row layout from xml when needed
    @Override
    public SensorRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.item_view, parent, false);
            return new SensorRecyclerViewAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(SensorRecyclerViewAdapter.ViewHolder holder, int position) {
            Sensor sensor = mData[position];

            holder.groupOwner.setText("Proprietário: ".concat(sensor.getOwner()));
            holder.groupMeasure.setText("Mensurando: ".concat(sensor.getMeasure()));
            holder.groupUuid.setText("UUID: ".concat(sensor.getUuid()));
    }

    // total number of rows
    @Override
    public int getItemCount() {
            return mData.length;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView groupOwner;
        TextView groupMeasure;
        TextView groupUuid;


        ViewHolder(View itemView) {
            super(itemView);
            groupOwner = itemView.findViewById(R.id.tvOwner);
            groupMeasure = itemView.findViewById(R.id.tvMeasure);
            groupUuid = itemView.findViewById(R.id.tvGroupUuid);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

        // convenience method for getting data at click position
        Sensor getItem(int id) {
            return mData[id];
        }

        // allows clicks events to be caught
        void setClickListener(SensorRecyclerViewAdapter.ItemClickListener itemClickListener) {
            this.mClickListener = itemClickListener;
        }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
