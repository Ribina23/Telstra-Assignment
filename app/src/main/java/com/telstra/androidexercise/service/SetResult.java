package com.telstra.androidexercise.service;

import com.telstra.androidexercise.data.RowsData;

import java.util.ArrayList;
//interface for passing data to fragment from activity

public interface SetResult {
    public void setData(ArrayList<RowsData> data);
}
