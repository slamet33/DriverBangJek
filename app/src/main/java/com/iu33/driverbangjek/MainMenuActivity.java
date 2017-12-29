package com.iu33.driverbangjek;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.iu33.driverbangjek.ConfigRetrofit.InstanceRetrofit;
import com.iu33.driverbangjek.History.HistoryActivity;
import com.iu33.driverbangjek.ResponeGoogle.Distance;
import com.iu33.driverbangjek.ResponeGoogle.LegsItem;
import com.iu33.driverbangjek.ResponeGoogle.OverviewPolyline;
import com.iu33.driverbangjek.ResponeGoogle.ResponseRoute;
import com.iu33.driverbangjek.ResponeGoogle.RoutesItem;
import com.iu33.driverbangjek.ResponeServer.ResponseTarif;
import com.iu33.driverbangjek.helper.DirectionMapsV2;
import com.iu33.driverbangjek.helper.GPSTracker;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainMenuActivity extends BaseActivity implements OnMapReadyCallback {
    private static final String TAG = "bang jek yes";
    GoogleMap map;
    @BindView(R.id.lokasiawal)
    TextView lokasiawal;
    @BindView(R.id.lokasitujuan)
    TextView lokasitujuan;
    @BindView(R.id.txtharga)
    TextView txtharga;
    @BindView(R.id.requestorder)
    Button requestorder;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE_1 = 2;
    LatLng select, select1;
    Double latawal, lonawal;
    Double lat1, lat2, lon1, lon2;
    Double tarifUser;
    String idUser;
    Double totalkm, harga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        ButterKnife.bind(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    //    @TargetApi() use Target api for all android version
    @RequiresApi(Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        //TODO Inizialize Google Maps
        map = googleMap;

        GPSTracker gpsTracker = new GPSTracker(context);

        if (gpsTracker.canGetLocation() == true) {
            lat1 = gpsTracker.getLatitude();
            lon1 = gpsTracker.getLongitude();

            select = new LatLng(lat1, lon1);

            LatLng posisi = new LatLng(lat1, lon1);

            posisi.toString();


            String name = null;
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            try {
                List<Address> hasil = geocoder.getFromLocation(lat1, lon1, 1);
                name = hasil.get(0).getAddressLine(0);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IndexOutOfBoundsException e) {

            }
            lokasiawal.setText(name);

            map.addMarker(new MarkerOptions().position(posisi).title(name));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(posisi, 16));
        } else {
            gpsTracker.showSettingGps();
        }


        //TODO Set Maps lAUNCHER Location
        map.getUiSettings().setCompassEnabled(true);
        map.getUiSettings().setCompassEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.setPadding(20, 180, 20, 180);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 12);
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        map.setMyLocationEnabled(true);
    }

    @OnClick({R.id.lokasiawal, R.id.lokasitujuan, R.id.requestorder})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lokasiawal:
                try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .build(this);
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    // TODO: Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                }
                break;
            case R.id.lokasitujuan:
                try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .build(this);
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE_1);
                } catch (GooglePlayServicesRepairableException e) {
                    // TODO: Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                }
                break;
            case R.id.requestorder:
                if (lokasiawal.toString().isEmpty()) {
                    Toast.makeText(context, "Lokasi Awal harus diisi", Toast.LENGTH_SHORT).show();
                } else if (lokasitujuan.toString().isEmpty()) {
                    Toast.makeText(context, "Lokasi Tujuan harus diisi", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        requestOrder();
                    } catch (NullPointerException e) {
                    }
                    break;
                }
        }
    }

    private void requestOrder() {
        try {
            InstanceRetrofit.api.requestTarif(sessionManager.getIdUser(), lat1.toString(), lon1.toString(), lokasiawal.getText().toString(), lat2.toString(), lon2.toString(), lokasitujuan.getText().toString(), lokasitujuan.getText().toString(), totalkm.toString(), harga.toString())
                    .enqueue(new Callback<ResponseTarif>() {
                        @Override
                        public void onResponse(Call<ResponseTarif> call, Response<ResponseTarif> response) {
                            Log.d("respone tarif :", response.message());

                            if (response.isSuccessful()) {
                                String status = response.body().getResult();
                                int idBooking = response.body().getIdBooking();

                                if (status.equals("true")) {
                                    Intent intent = new Intent(context, SearchDriverActivity.class);
                                    intent.putExtra("idbooking", idBooking);
                                    startActivity(intent);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseTarif> call, Throwable t) {

                        }
                    });
        } catch (NullPointerException e) {
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);

                //TODO Get LatLang
                lat1 = place.getLatLng().latitude;
                lon1 = place.getLatLng().longitude;
                select = new LatLng(lat1, lon1);

                //TODO GET Name Location
                String namalokasi = place.getAddress().toString();

                //TODO if lokasi awal choice again. The Maps Route would be clear
                if (!lokasiawal.getText().toString().isEmpty()) {
                    map.clear();
                    map.addMarker(new MarkerOptions().position(select));
                }

                //TODO MARK lATlANG
                map.addMarker(new MarkerOptions().position(select).title(namalokasi));

                //TODO Set Auto Zoom
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(select, 16f));
                Log.i(TAG, "Place: " + place.getName());

                lokasiawal.setText(namalokasi);
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i(TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }

        } else if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE_1) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);

                //TODO Get LatLang
                lat2 = place.getLatLng().latitude;
                lon2 = place.getLatLng().longitude;
                select1 = new LatLng(lat2, lon2);

                //TODO GET Name Location
                String namalokasi1 = place.getAddress().toString();


                //TODO if lokasi tujuan choice again. The Maps Route would be clear
                if (!lokasitujuan.getText().toString().isEmpty()) {
                    map.clear();
                    map.addMarker(new MarkerOptions().position(select));
                }

                //TODO MARK lATlANG
                map.addMarker(new MarkerOptions().position(select1).title(namalokasi1).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

                //TODO Set Auto Zoom
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(select1, 16));

                //TODO Latlangbound Menampilkan 2 kOrdinat yang dipilih

                LatLngBounds.Builder bound = new LatLngBounds.Builder();

                //TODO Kordinat yang mau dimasukkan
                bound.include(select);
                bound.include(select1);

//                map.setPadding(100,100,100,100);

                map.moveCamera(CameraUpdateFactory.newLatLngBounds(bound.build(), 15));

                lokasitujuan.setText(namalokasi1);
                createRoute();

                Log.i(TAG, "Place: " + place.getName());

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i(TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    private void createRoute() {
        InstanceRetrofit.apiGoogle.requestRoute(lat1.toString() + "," + lon1.toString(), lat2.toString() + "," + lon2.toString()).enqueue(new Callback<ResponseRoute>() {
            @Override
            public void onResponse(Call<ResponseRoute> call, Response<ResponseRoute> response) {
                Log.d("respone route", response.message());

                if (response.isSuccessful()) {
                    //TODO Get JSON Route
                    ResponseRoute json = response.body();

                    //TODO Get JSON Array JSON
                    List<RoutesItem> routes = json.getRoutes();

                    //TODO Get Object 0 & overview_polyline
                    RoutesItem index = routes.get(0);

                    OverviewPolyline polylines = index.getOverviewPolyline();

                    String points = polylines.getPoints();

                    Log.d("respone points", points);

                    new DirectionMapsV2(context).gambarRoute(map, points);

                    List<LegsItem> legs = index.getLegs();

                    LegsItem indexlegs = legs.get(0);

                    Distance distance = indexlegs.getDistance();

                    String textDistance = distance.getText();
                    int valueDistance = distance.getValue();

                    Log.d("Jaraknya ", String.valueOf(valueDistance));

                    //TODO Convert Value Distance
                    totalkm = Math.ceil(Double.parseDouble(String.valueOf(valueDistance)) / 1000);
                    harga = totalkm * 1000;

                    txtharga.setText("Rp. " + harga.toString());

//                    Duration duration = indexlegs.getDuration();
//
//                    String textDuration = duration.getText();
//
//                    int valueDuration = duration.getValue();


                } else {
                    Log.d("respone error", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseRoute> call, Throwable t) {
                Log.d("error", t.getLocalizedMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.history:
                startActivity(new Intent(context, HistoryActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}