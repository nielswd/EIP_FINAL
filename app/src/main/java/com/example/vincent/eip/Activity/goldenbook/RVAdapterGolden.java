package com.example.vincent.eip.Activity.goldenbook;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vincent.eip.Network.goldenbook.GoldenBook;
import com.example.vincent.eip.Network.goldenbook.UserMessages;
import com.example.vincent.eip.R;

/**
 * Created by iNfecteD on 29/06/2017.
 */

public class RVAdapterGolden extends RecyclerView.Adapter<RVAdapterGolden.MyViewHolder> {

    private GoldenBook devices;
    private int expandedPosition = -1;
    private Activity mActivity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nameItem, mItemDescription;
        public LinearLayout mItemExpend;
        public CardView mRootItem;

        public MyViewHolder(View view) {
            super(view);
            nameItem = (TextView) view.findViewById(R.id.item_title);
            mItemDescription = (TextView) view.findViewById(R.id.item_description);
            mItemExpend = (LinearLayout) view.findViewById(R.id.llExpandArea);
            mRootItem = (CardView) view.findViewById(R.id.llCardBack);
        }
    }


    public RVAdapterGolden(GoldenBook devices, Context t) {
        this.devices = devices;
        this.mActivity = (Activity) t;
    }

    public void updateList(GoldenBook devices){
        this.devices = devices;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_item_openinghours, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final UserMessages newItem = devices.getList().get(position);
        String name = "";
        if (newItem.getFirstName() != null && newItem.getLastName() != null) {
            name = newItem.getFirstName() + newItem.getLastName();
        }
        holder.nameItem.setText(name);
        holder.mItemDescription.setText(newItem.getComment());
        holder.mItemExpend.setVisibility(View.VISIBLE);
    }


    @Override
    public int getItemCount() {
        if (devices != null && devices.getList() != null) {
            return devices.getList().size();
        } else
            return 0;
    }
}
