package com.zack.openweatherapp.bookmarkcities.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.MapFragment;
import com.zack.openweatherapp.R;
import com.zack.openweatherapp.bookmarkcities.repository.BookMarkRepository;
import com.zack.openweatherapp.bookmarkcities.viewmodel.BookmarkViewModel;
import com.zack.openweatherapp.bookmarkcities.viewmodel.BookmarkViewModelFactory;
import com.zack.openweatherapp.common.model.BookMarkedCity;
import com.zack.openweatherapp.databinding.BookmarksListLayoutBinding;
import com.zack.openweatherapp.weatherforecast.ui.WeatherForecastFragment;

import static com.zack.openweatherapp.ServiceLocatorUtil.executor;
import static com.zack.openweatherapp.ServiceLocatorUtil.initializeDb;

public class HomeFragment extends Fragment implements RecyclerViewCallback{
    private BookmarkViewModel viewModel;
    private BookmarksListLayoutBinding binding;
    private RecyclerView recyclerView;
    private BookMarksAdapter adapter;
    private final static String TAG_FRAGMENT = "HOME_FRAGMENT";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.bookmarks_list_layout, container, false);
        recyclerView = binding.recyclerView;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CityFragment fragment = new CityFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        BookMarkRepository bookMarkRepository = new BookMarkRepository(initializeDb().bookmarkDao(), executor);

        viewModel =new ViewModelProvider(this,
                new BookmarkViewModelFactory(bookMarkRepository))
                .get(BookmarkViewModel.class);
        viewModel.fetchBookMarks().observe(this, bookMarks -> {
            if (bookMarks.size()>0) {
                adapter = new BookMarksAdapter(getContext(), this);
                adapter.setBookMarksList(bookMarks);
                recyclerView.setAdapter(adapter);

            } else {
                recyclerView.setVisibility(View.GONE);
                binding.noBookmarksText.setVisibility(View.VISIBLE);
            }
        });



        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onItemClick(int position, BookMarkedCity bookMark, View v) {
        WeatherForecastFragment fragment = new WeatherForecastFragment();
        Bundle args = new Bundle();
        args.putSerializable("bookmark", bookMark);
        fragment.setArguments(args);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content, fragment);
        transaction.addToBackStack(TAG_FRAGMENT);
        transaction.commit();
    }

    @Override
    public void onDelete(int position, BookMarkedCity bookMark, View v) {
        viewModel.deleteBookMark(bookMark);
    }


}
