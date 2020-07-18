package com.zack.openweatherapp.bookmarkcities.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zack.openweatherapp.common.model.BookMarkedCity;
import com.zack.openweatherapp.databinding.CityItemBinding;

import java.util.List;

public class BookMarksAdapter  extends RecyclerView.Adapter<BookMarksAdapter.BookMarkViewHolder> {

    Context context;
    List<BookMarkedCity> bookMarks;
    RecyclerViewCallback callback;

    public BookMarksAdapter(Context context, RecyclerViewCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @NonNull
    @Override
    public BookMarkViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        CityItemBinding binding = CityItemBinding.inflate(inflater, viewGroup, false);
        return new BookMarkViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookMarkViewHolder viewHolder, int i) {
        BookMarkedCity bookMark = bookMarks.get(i);
        viewHolder.bind(bookMark);
        viewHolder.binding.getRoot().setOnClickListener(v -> callback.onItemClick(i, bookMark, v));
        ((ImageButton)viewHolder.binding.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onDelete(i, bookMark, v);
            }
        });
    }

    public void setBookMarksList(List<BookMarkedCity> bookMarks) {
        this.bookMarks = bookMarks;
    }

    @Override
    public int getItemCount() {
        return bookMarks.size();
    }

    public class BookMarkViewHolder extends RecyclerView.ViewHolder {
        CityItemBinding binding;

        public BookMarkViewHolder(@NonNull CityItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(BookMarkedCity bookMark) {
            binding.cityName.setText(bookMark.getName());
            binding.countryName.setText(bookMark.getCountry());
        }
    }
}


