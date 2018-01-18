package com.example.vincent.eip.Fragments.touristicplaces;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vincent.eip.Network.infos.openinghours.OpeningHour;
import com.example.vincent.eip.Network.infos.openinghours.OpeningHours;
import com.example.vincent.eip.Network.infos.touristicplaces.TouristicPlace;
import com.example.vincent.eip.Network.infos.touristicplaces.TouristicPlaces;
import com.example.vincent.eip.R;

/**
 * Created by iNfecteD on 29/06/2017.
 */

public class RVAdapterTouristicPlaces extends RecyclerView.Adapter<RVAdapterTouristicPlaces.MyViewHolder> {

    private TouristicPlaces touristicPlaces;
    private int expandedPosition = -1;
    private Activity mContainerActivity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nameItem, mItemDescription, mStart;
        public LinearLayout mItemExpend;
        public CardView mRootItem;
        public TextView goToMap;

        public MyViewHolder(View view) {
            super(view);
            nameItem = (TextView) view.findViewById(R.id.item_title);
            mStart = (TextView) view.findViewById(R.id.item_start);
            mItemDescription = (TextView) view.findViewById(R.id.item_description);
            mItemExpend = (LinearLayout) view.findViewById(R.id.llExpandArea);
            mRootItem = (CardView) view.findViewById(R.id.llCardBack);
            goToMap = (TextView) view.findViewById(R.id.goToMap);
        }
    }


    public RVAdapterTouristicPlaces(TouristicPlaces touristicPlaces, Context t) {
        this.touristicPlaces = touristicPlaces;
        this.mContainerActivity = (Activity) t;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_touristicplaces, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final TouristicPlace newItem = touristicPlaces.getList().get(position);
        holder.nameItem.setText(newItem.getName());
        holder.mStart.setText(newItem.getHours());
        holder.mItemDescription.setText(newItem.getDescription());
        holder.mRootItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.mItemExpend.getVisibility() == View.GONE){
                    holder.mItemExpend.setVisibility(View.VISIBLE);
                } else {
                    holder.mItemExpend.setVisibility(View.GONE);
                }
            }
        });

        Log.e("ERROR MAP",newItem.getLatitude() + "," + newItem.getLongitude() );
        if (newItem.getLongitude() != null && newItem.getLatitude() != null) {
            holder.goToMap.setVisibility(View.VISIBLE);
            holder.goToMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Uri gmmIntentUri = Uri.parse("geo:" + newItem.getLatitude() + "," + newItem.getLongitude());
                    String geo = "geo:" + newItem.getLatitude() + "," + newItem.getLongitude();
                    Log.e("test geo", geo);
                    Log.e("test geo", "geo:48.826983403182346,2.366180419921875");
                    Uri gmmIntentUri = Uri.parse(geo);

                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    if (mapIntent.resolveActivity(mContainerActivity.getPackageManager()) != null) {
                        mContainerActivity.startActivity(mapIntent);
                    }
                }
            });
        } else {
            holder.goToMap.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        return touristicPlaces.getList().size();
    }
}
