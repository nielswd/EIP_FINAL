package com.example.vincent.eip.Activity;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vincent.eip.GlobalClass;
import com.example.vincent.eip.Interfaces.UpdateRequestCallback;
import com.example.vincent.eip.Network.UserClientInfo;
import com.example.vincent.eip.Network.request.Req;
import com.example.vincent.eip.Network.request.Requests;
import com.example.vincent.eip.Network.request.update.RequestUpdateRetrofit;
import com.example.vincent.eip.R;

/**
 * Created by iNfecteD on 29/06/2017.
 */

public class RVAdapterListRequest extends RecyclerView.Adapter<RVAdapterListRequest.MyViewHolder> {

    private Requests requests;
    private int expandedPosition = -1;
    private Activity mActivity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nameItem, mItemDescription, mItemCom;
        public LinearLayout mItemExpend;
        public CardView mRootItem;

        public MyViewHolder(View view) {
            super(view);
            nameItem = (TextView) view.findViewById(R.id.item_title);
            mItemDescription = (TextView) view.findViewById(R.id.item_status);
            mItemCom = (TextView) view.findViewById(R.id.item_com_client);
            mItemExpend = (LinearLayout) view.findViewById(R.id.llExpandArea);
            mRootItem = (CardView) view.findViewById(R.id.llCardBack);
        }
    }


    public RVAdapterListRequest(Requests sectors, Context t) {
        this.requests = sectors;
        this.mActivity = (Activity) t;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_requests, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Req newItem = requests.getReq().get(position);

        String requetename = "Steak-Frites";
        switch (newItem.getFkIdServices()){
            case 21:
                requetename = "Steak-Frites";
                break;
            case 22:
                requetename = "Poulet - Patates";
                break;
            case 23:
                requetename = "Salade Caesar";
                break;
            case 24:
                requetename = "Steak Tartare";
                break;
        }
        holder.nameItem.setText(requetename);
        holder.mItemCom.setText(newItem.getComClient());
        String status = "En cours";
        switch (newItem.getState()){
            case 0:
                    status = "Emise";
                    break;
            case 1:
                status = "En cours de traitement";
                    break;
            case 2:
                status = "Fini";
                    break;
            case 3:
                status = "Refusée";
                    break;
            case 4:
                status = "Annulée";
                break;
        }
        holder.mItemDescription.setText(status);
        holder.mItemExpend.setVisibility(View.VISIBLE);
        holder.mRootItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newItem.getState() == 0) {
                    //TODO delete
                    newItem.setState(4);
                    GlobalClass global = (GlobalClass) mActivity.getApplication();
                    UserClientInfo clientInfo = global.userInfos;
                    RequestUpdateRetrofit updateReq = new RequestUpdateRetrofit();
                    updateReq.login(mActivity, clientInfo, new UpdateRequestCallback() {
                        @Override
                        public void updateRequest(Requests requests) {
                            Log.d("request deleted", requests.getMessage());
                        }
                    }, newItem);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return requests.getReq().size();
    }
}
