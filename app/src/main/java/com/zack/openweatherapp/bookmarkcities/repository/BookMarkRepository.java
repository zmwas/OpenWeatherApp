package com.zack.openweatherapp.bookmarkcities.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.zack.openweatherapp.bookmarkcities.db.AppDatabase;
import com.zack.openweatherapp.common.model.BookMarkedCity;
import com.zack.openweatherapp.bookmarkcities.db.BookmarkDao;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BookMarkRepository {
    private BookmarkDao bookmarkDao;
    private Executor executor;

    public BookMarkRepository(BookmarkDao bookmarkDao, Executor executor) {
        this.bookmarkDao = bookmarkDao;
        this.executor = executor;
    }

    public void saveBookMark(BookMarkedCity bookMark) {
        executor.execute(() -> bookmarkDao.insert(bookMark));
    }

    public LiveData<List<BookMarkedCity>> fetchBookMarks() {
        return bookmarkDao.fetchBookMarks();
    }

    public void deleteBookMark(BookMarkedCity bookMark) {
        executor.execute(() -> bookmarkDao.delete(bookMark));
    }

}
