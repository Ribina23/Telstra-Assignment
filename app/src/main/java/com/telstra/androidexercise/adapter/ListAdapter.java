package com.telstra.androidexercise.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.telstra.androidexercise.R;
import com.telstra.androidexercise.data.RowsData;

import java.util.ArrayList;
import java.util.List;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<RowsData> listdata = new ArrayList<>();
    private Context context;

    public ListAdapter(List<RowsData> responseData, FragmentActivity requireActivity, Context context) {
        this.listdata = responseData;
        this.context = context;
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final RowsData myListData = listdata.get(position);
        //if the title and description is null changing the layout height
        if (myListData.getTitle() == null && myListData.getDescription() == null) {
            ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
            params.height = 0;
            holder.itemView.setLayoutParams(params);
        }

        //response data setting to title and description
        holder.titleTv.setText(myListData.getTitle());
        holder.descriptionTv.setText(myListData.getDescription());
        //image setting to the imageview
        Picasso.get()
                .load(myListData.getImageHref()).fit().placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(holder.imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.d("ErrorLog", e.toString());
                    }
                });

    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView titleTv;
        public TextView descriptionTv;
        public CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            //view initialisation
            this.imageView = (ImageView) itemView.findViewById(R.id.userIv);
            this.titleTv = (TextView) itemView.findViewById(R.id.titleTv);
            this.descriptionTv = (TextView) itemView.findViewById(R.id.descriptionTv);
            this.cardView = (CardView) itemView.findViewById(R.id.cardView);
        }
    }
}