package com.zack.openweatherapp.bookmarkcities.db;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

public interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.FAIL)
    void insertAll(T objects);

    @Insert(onConflict = OnConflictStrategy.FAIL)
    long insert(T object);

    @Update
    void update(T object);

    @Delete
    void delete(T object);

}
