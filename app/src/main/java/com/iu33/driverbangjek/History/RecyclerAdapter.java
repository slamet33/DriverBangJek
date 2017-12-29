package com.iu33.driverbangjek.History;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iu33.driverbangjek.R;
import com.iu33.driverbangjek.ResponeServer.DataItem;

import java.util.List;

/**
 * Created by hp on 12/28/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyHolder> {
    public RecyclerAdapter(List<DataItem> data) {
        this.data = data;
    }

    List<DataItem> data;

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.status.setText("Proses ." + data.get(position).getBookingTanggal());
        holder.lokasi.setText(data.get(position).getBookingTujuan());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView status;
        TextView lokasi;

        public MyHolder(View itemView) {
            super(itemView);
            status = itemView.findViewById(R.id.dateandstatus);
            lokasi = itemView.findViewById(R.id.location);
        }
    }
}
