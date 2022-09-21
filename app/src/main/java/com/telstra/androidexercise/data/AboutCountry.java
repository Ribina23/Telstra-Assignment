package com.telstra.androidexercise.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
//model class
public class AboutCountry implements Serializable {

   String title;

    ArrayList<RowsData> rows;

    public AboutCountry(String title, ArrayList<RowsData> rows) {
        this.title = title;
        this.rows = rows;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<RowsData> getRows() {
        return rows;
    }

    public void setRows(ArrayList<RowsData> rows) {
        this.rows = rows;
    }
}
