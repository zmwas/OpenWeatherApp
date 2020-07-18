package com.zack.openweatherapp.bookmarkcities.ui;


import android.view.View;

import com.zack.openweatherapp.common.model.BookMarkedCity;

public interface RecyclerViewCallback {
    void onItemClick(int position, BookMarkedCity bookMark, View v);
    void onDelete(int position, BookMarkedCity bookMark, View v);

}
