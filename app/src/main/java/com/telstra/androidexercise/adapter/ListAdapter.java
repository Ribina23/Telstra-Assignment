package com.telstra.androidexercise.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.telstra.androidexercise.R;
import com.telstra.androidexercise.data.RowsData;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{

    private List<RowsData> listdata;
    public ListAdapter(List<RowsData> responseData, FragmentActivity requireActivity) {
        this.listdata=responseData;
    }
    // RecyclerView recyclerView;
   /* public ListAdapter(MyListData[] listdata) {
        this.listdata = listdata;
    }*/
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final RowsData myListData = listdata.get(position);
        holder.textView.setText(listdata.get(position).getTitle());

    }


    @Override
    public int getItemCount() {
        return 5;
//        return listdata.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.userIv);
            this.textView = (TextView) itemView.findViewById(R.id.titleTv);
        }
    }
}