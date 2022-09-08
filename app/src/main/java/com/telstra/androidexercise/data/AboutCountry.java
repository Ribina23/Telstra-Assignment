package com.telstra.androidexercise.data;

import java.util.List;

public class AboutCountry {
    String title;

    List<RowsData> rows;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<RowsData> getRows() {
        return rows;
    }

    public void setRows(List<RowsData> rows) {
        this.rows = rows;
    }
}
