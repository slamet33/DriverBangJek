package com.iu33.driverbangjek.History;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iu33.driverbangjek.ConfigRetrofit.InstanceRetrofit;
import com.iu33.driverbangjek.R;
import com.iu33.driverbangjek.ResponeServer.DataItem;
import com.iu33.driverbangjek.ResponeServer.ResponseGetBooking;
import com.iu33.driverbangjek.helper.SessionManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActiveFragment extends Fragment {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    Unbinder unbinder;
    SessionManager sessionManager;

    public ActiveFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_process, container, false);
        unbinder = ButterKnife.bind(this, view);
        sessionManager = new SessionManager(getActivity());
        getHistory();
        return view;
    }

    private void getHistory() {
        InstanceRetrofit.api.requesthandle(sessionManager.getIdUser()).enqueue(new Callback<ResponseGetBooking>() {
            @Override
            public void onResponse(Call<ResponseGetBooking> call, Response<ResponseGetBooking> response) {
                try {
                    Log.d("respone :", response.body().toString());

                    if (response.isSuccessful()) {
                        //TODO Jika Bisa maka akan get data dari body
                        String result = response.body().getResult();
                        if (result.equals("true")) {
                            List<DataItem> data = response.body().getData();

                            RecyclerAdapter adapter = new RecyclerAdapter(data);
                            recyclerview.setAdapter(adapter);
                            recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

                            Log.d("datanya", data.toString());

                            //TODO Data dipindah ke Recyclerview
                        }
                    }else{

                    }
                } catch (NullPointerException e) {

                }
            }

            @Override
            public void onFailure(Call<ResponseGetBooking> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
