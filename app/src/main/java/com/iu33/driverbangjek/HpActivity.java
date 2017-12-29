package com.iu33.driverbangjek;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.iid.FirebaseInstanceId;
import com.iu33.driverbangjek.ConfigRetrofit.InstanceRetrofit;
import com.iu33.driverbangjek.History.HistoryActivity;
import com.iu33.driverbangjek.ResponeServer.ResponseDaftar;
import com.iu33.driverbangjek.helper.HeroHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HpActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hp);

        final EditText edtNohp = findViewById(R.id.edtNohp);
        Button btnsubmit = findViewById(R.id.btnsubmit);
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edtNohp.getText().toString().isEmpty()) {

                    edtNohp.requestFocus();
                    edtNohp.setError("Harus Diisi");
                } else {

                    String refreshedToken = FirebaseInstanceId.getInstance().getToken();

                    //TODO Get Input User
                    String hp = edtNohp.getText().toString();

                    //TODO get Konfigurasi Retrofit
                    InstanceRetrofit.api.request_daftar(
                            sessionManager.getNama(),
                            sessionManager.getEmail(),
                            hp,refreshedToken
                    ).enqueue(new Callback<ResponseDaftar>() {
                        @Override
                        public void onResponse(Call<ResponseDaftar> call, Response<ResponseDaftar> response) {
                            Log.d("respon daftar", response.message());
                            if (response.isSuccessful()) {
                                //Ambil Respone server
                                String msg = response.body().getMsg();
                                String result = response.body().getResult();

                                if (result.equals("true")) {
                                    int iduser = response.body().getIduser();
                                    sessionManager.setIduser(String.valueOf(iduser));

                                    startActivity(new Intent(context, HistoryActivity   .class));
                                    finish();
                                } else {
                                    HeroHelper.pesan(context, msg);
                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseDaftar> call, Throwable t) {
                            Log.d("error daftar", t.getLocalizedMessage());
                        }
                    });
                }
            }
        });
    }
}
