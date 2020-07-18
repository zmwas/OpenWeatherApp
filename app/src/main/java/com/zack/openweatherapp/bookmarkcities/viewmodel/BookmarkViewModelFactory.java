package com.zack.openweatherapp.bookmarkcities.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.zack.openweatherapp.bookmarkcities.repository.BookMarkRepository;

public class BookmarkViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    @NonNull
    private BookMarkRepository repository;

    public BookmarkViewModelFactory(@NonNull BookMarkRepository repository) {
        this.repository = repository;
    }

    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new BookmarkViewModel(repository);
    }

}
