    package com.example.app.ui.home;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.app.R;
import com.example.app.storage.Data;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class HomeFragment extends Fragment {

    private TextView credit;
    private Button generate;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);


        generate = root.findViewById(R.id.generate);
        credit = root.findViewById(R.id.credit);
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendHttpRequest(Data.NAME);
            }
        });
        return root;
    }


    private void sendHttpRequest(String name){

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://35.220.132.167:8081//generate/credit?name=" + name)
                .get()
                .build();
        Call call = client.newCall(request);
        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response.isSuccessful()) {
            try {
                credit.setText(response.body().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}