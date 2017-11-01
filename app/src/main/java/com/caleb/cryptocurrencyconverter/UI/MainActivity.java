package com.caleb.cryptocurrencyconverter.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.caleb.cryptocurrencyconverter.R;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CardView btcCardView = (CardView) findViewById(R.id.bitcoin);
        CardView ethCardView = (CardView) findViewById(R.id.ethereum);

        btcCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BitcoinExchangeRateActivity.class);
                startActivity(intent);
            }
        });

        ethCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EthereumExchangeRateActivity.class);
                startActivity(intent);
            }
        });
    }


}