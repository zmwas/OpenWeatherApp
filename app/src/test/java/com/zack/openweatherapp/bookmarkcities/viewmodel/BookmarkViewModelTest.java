package com.zack.openweatherapp.bookmarkcities.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.zack.openweatherapp.LiveDataTestUtil;
import com.zack.openweatherapp.bookmarkcities.repository.BookMarkRepository;
import com.zack.openweatherapp.common.model.BookMarkedCity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

public class BookmarkViewModelTest {
    @Mock
    BookMarkRepository bookMarkRepository;
    @Rule
    public InstantTaskExecutorRule executorRule = new InstantTaskExecutorRule();

    BookmarkViewModel viewModel;
    MutableLiveData<List<BookMarkedCity>> testBookmarksLiveData = new MutableLiveData<>();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        viewModel = new BookmarkViewModel(bookMarkRepository);
    }

    @Test
    public void test_fetching_of_bookmarks() throws InterruptedException {
        List<BookMarkedCity> cities = new ArrayList<>();

        cities.add(new BookMarkedCity());
        cities.add(new BookMarkedCity());
        cities.add(new BookMarkedCity());
        testBookmarksLiveData.setValue(cities);

        Mockito.when(bookMarkRepository.fetchBookMarks()).thenReturn(testBookmarksLiveData);
        LiveData<List<BookMarkedCity>> result = viewModel.fetchBookMarks();
        Assert.assertEquals(3, LiveDataTestUtil.getOrAwaitValue(result).size());

    }


    @Test
    public void test_adding_of_bookmarks() throws InterruptedException {
        viewModel.saveBookMark(new BookMarkedCity());
        Mockito.verify(bookMarkRepository).saveBookMark(any(BookMarkedCity.class));
    }

    @Test
    public void test_deleting_of_bookmarks() throws InterruptedException {
        viewModel.deleteBookMark(new BookMarkedCity());
        Mockito.verify(bookMarkRepository).deleteBookMark(any(BookMarkedCity.class));
    }

}