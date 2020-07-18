package com.zack.openweatherapp.bookmarkcities.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.zack.openweatherapp.common.model.BookMarkedCity;

import java.util.List;
@Dao
public interface BookmarkDao extends BaseDao<BookMarkedCity> {
    @Query("SELECT * FROM bookmark")
    LiveData<List<BookMarkedCity>> fetchBookMarks();
}
