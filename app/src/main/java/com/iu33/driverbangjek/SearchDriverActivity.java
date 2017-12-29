package com.iu33.driverbangjek;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.iu33.driverbangjek.ConfigRetrofit.InstanceRetrofit;
import com.iu33.driverbangjek.History.HistoryActivity;
import com.iu33.driverbangjek.ResponeServer.ResponseCheckBooking;
import com.iu33.driverbangjek.ResponeServer.ResponseEmail;
import com.iu33.driverbangjek.helper.HeroHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.bclogic.pulsator4droid.library.PulsatorLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchDriverActivity extends BaseActivity{

    @BindView(R.id.pulsator)
    PulsatorLayout pulsator;
    @BindView(R.id.btncancel)
    Button btncancel;
    int idbooking;
    String id_booking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_driver);
        ButterKnife.bind(this);

        checkBooking();

        //TODO Tangkap ID Booking

        idbooking = getIntent().getIntExtra("idbooking",0);
        pulsator.start();


    }

    private void checkBooking() {
        InstanceRetrofit.api.requestCheck(String.valueOf(id_booking)).enqueue(new Callback<ResponseCheckBooking>() {
            @Override
            public void onResponse(Call<ResponseCheckBooking> call, Response<ResponseCheckBooking> response) {
                String result = response.body().getResult();
                if (result.equals("true")){
                    String iddriver = response.body().getResult();
                    startActivity(new Intent(context, DriverPositionActivity.class));
                    finish();
                } else{
                    HeroHelper.pesan(context,response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResponseCheckBooking> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.btncancel)
    public void onViewClicked() {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Konfirmasi");
//        alert.setIcon(R.id.icon)
        alert.setMessage("Apakah anda yakin mau cancel?");
        alert.setPositiveButton("Yakin", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                actionCancel();
            }
        });
        alert.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alert.show();
    }

    private void actionCancel() {
        InstanceRetrofit.api.requestCancel(idbooking).enqueue(new Callback<ResponseEmail>() {
            @Override
            public void onResponse(Call<ResponseEmail> call, Response<ResponseEmail> response) {
                String result = response.body().getResult();
                if (result.equals("true")){
                    startActivity(new Intent(context, MainMenuActivity.class));
                    finish();
                } else{
                    HeroHelper.pesan(context,response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResponseEmail> call, Throwable t) {

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

        switch (item.getItemId()){
            case R.id.history:
                startActivity(new Intent(context,HistoryActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
