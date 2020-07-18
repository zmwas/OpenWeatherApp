package com.zack.openweatherapp.bookmarkcities.ui;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.zack.openweatherapp.bookmarkcities.db.AppDatabase;
import com.zack.openweatherapp.bookmarkcities.repository.BookMarkRepository;
import com.zack.openweatherapp.bookmarkcities.viewmodel.BookmarkViewModel;
import com.zack.openweatherapp.bookmarkcities.viewmodel.BookmarkViewModelFactory;
import com.zack.openweatherapp.common.model.BookMarkedCity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.zack.openweatherapp.ServiceLocatorUtil.executor;
import static com.zack.openweatherapp.ServiceLocatorUtil.initializeDb;


public class CityFragment extends SupportMapFragment implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener {
    private GoogleMap mMap;
    private BookmarkViewModel viewModel;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        BookMarkRepository bookMarkRepository = new BookMarkRepository(initializeDb().bookmarkDao(), executor);

        viewModel =new  ViewModelProvider(this,
                new BookmarkViewModelFactory(bookMarkRepository))
                .get(BookmarkViewModel.class);

    }

    @Override
    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        if (mMap == null) getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng nairobi = new LatLng(-1.28333, 36.81667);
        mMap.addMarker(new MarkerOptions().position(nairobi));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(nairobi));
        mMap.setOnMapClickListener(this::onMapClick);
    }

    private void placeMarker(LatLng position) {
        mMap.addMarker(new MarkerOptions().position(position));
    }

    private void getLocation(LatLng position) {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.
                    getFromLocation(position.latitude, position.longitude, 1);
            bookMarkCity(addresses.get(0));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void bookMarkCity(Address address) {
        BookMarkedCity bookMark = new BookMarkedCity();
        bookMark.setCountry(address.getCountryName());
        bookMark.setName(address.getLocality());
        bookMark.setLatitude(address.getLatitude());
        bookMark.setLongitude(address.getLongitude());
        viewModel.saveBookMark(bookMark);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    private void onMapClick(LatLng position) {
        CityFragment.this.placeMarker(position);
        getLocation(position);
    }

    @Override
    public void getMapAsync(OnMapReadyCallback onMapReadyCallback) {
        super.getMapAsync(onMapReadyCallback);
    }
}
