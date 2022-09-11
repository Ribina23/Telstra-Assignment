package com.telstra.androidexercise.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.telstra.androidexercise.R;
import com.telstra.androidexercise.data.RowsData;
import com.telstra.androidexercise.viewmodel.ListViewModel;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<RowsData> listdata= new ArrayList<>();
    private Context context;

    public ListAdapter(List<RowsData> responseData, FragmentActivity requireActivity, Context context) {
        this.listdata = responseData;
        this.context = context;
    }
/* public    ListAdapter(ListViewModel viewModel, LifecycleOwner lifecycleOwner, Context context) {
        this.context = context;
        viewModel.getRepos().observe(lifecycleOwner, repos -> {
            listdata.clear();
            if (repos != null) {
                listdata.addAll(repos.getRows());
                notifyDataSetChanged();
            }
        });
        setHasStableIds(true);
    }*/
    // RecyclerView recyclerView;
   /* public ListAdapter(MyListData[] listdata) {
        this.listdata = listdata;
    }*/
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
        if(myListData.getTitle() == null && myListData.getDescription() ==null)
        {
            ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
            params.height = 0;
            holder.itemView.setLayoutParams(params);
//            holder.cardView.setVisibility(View.GONE);
        }
        /*else{
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            holder.cardView.setVisibility(View.VISIBLE);
        }*/

        holder.titleTv.setText(myListData.getTitle());
        holder.descriptionTv.setText(myListData.getDescription());
     /*   Glide.with(context)
                .setDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.user).error(R.drawable.user))
                .load("http://www.donegalhimalayans.com/images/That%20fish%20was%20this%20big.jpg")
                .into(holder.imageView);
*/
      /*  Picasso.get()
                .load("http://icons.iconarchive.com/icons/iconshock/alaska/256/Igloo-icon.png")
                .resize(150, 50)
                .centerCrop()
                .into(holder.imageView);*/
        Picasso.get()
                .load(myListData.getImageHref()).fit().placeholder(R.drawable.placeholder).error(R.drawable.placeholder)
                .into(holder.imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.d("ErrorLog",e.toString());
                    }
                });

    }


    @Override
    public int getItemCount() {
        return listdata.size();
//        return listdata.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView titleTv;
        public TextView descriptionTv;
public CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.userIv);
            this.titleTv = (TextView) itemView.findViewById(R.id.titleTv);
            this.descriptionTv = (TextView) itemView.findViewById(R.id.descriptionTv);
            this.cardView = (CardView) itemView.findViewById(R.id.cardView);
        }
    }
}