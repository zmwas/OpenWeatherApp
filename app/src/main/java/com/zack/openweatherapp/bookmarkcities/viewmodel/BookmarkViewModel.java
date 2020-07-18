package com.zack.openweatherapp.bookmarkcities.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.zack.openweatherapp.bookmarkcities.repository.BookMarkRepository;
import com.zack.openweatherapp.common.model.BookMarkedCity;

import java.util.List;

public class BookmarkViewModel extends ViewModel {

    private BookMarkRepository repository;

    public BookmarkViewModel(BookMarkRepository repository) {
        this.repository = repository;
    }

    public void saveBookMark(BookMarkedCity bookMark) {
        repository.saveBookMark(bookMark);
    }

    public LiveData<List<BookMarkedCity>> fetchBookMarks() {
        return repository.fetchBookMarks();
    }

    public void deleteBookMark(BookMarkedCity bookMark) {
        repository.deleteBookMark(bookMark);
    }

}
