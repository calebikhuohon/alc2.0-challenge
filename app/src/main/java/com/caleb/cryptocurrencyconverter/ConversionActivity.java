package com.caleb.cryptocurrencyconverter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.caleb.cryptocurrencyconverter.UI.BitcoinExchangeRateActivity;

import java.text.DecimalFormat;
import java.util.Scanner;

import static com.caleb.cryptocurrencyconverter.UI.BitcoinExchangeRateActivity.EXRATE_TEXT;
import static com.caleb.cryptocurrencyconverter.UI.BitcoinExchangeRateActivity.EXRATE_VALUE;

public class ConversionActivity extends AppCompatActivity {
    double exchangeRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion);

        TextView currencyTextView = (TextView) findViewById(R.id.textView_currency);
        final EditText currencyEditText = (EditText) findViewById(R.id.editText_currency);

        TextView bitcoinTextView = (TextView) findViewById(R.id.textView_bitcoin);
        final TextView bitcoinValue = (TextView) findViewById(R.id.bitcoinValue);

        Bundle data = getIntent().getExtras();
        String currencyName = data.getString(EXRATE_TEXT);
        exchangeRate = data.getDouble(EXRATE_VALUE);


        final DecimalFormat decimalFormat = new DecimalFormat("0.00");

        bitcoinTextView.setText("Bitcoin");
        bitcoinValue.setText(data.getString("bitcoinValue"));
        currencyTextView.setText(currencyName);
        currencyEditText.setText(Double.toString(exchangeRate));


        currencyEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (currencyEditText.getText().toString().equals("")) {
                    bitcoinValue.setText("0");
                } else if (!currencyEditText.getText().toString().equals("")) {
                    try {
                        double newBtcvalue = Double.parseDouble(charSequence.toString());
                        double conversionFormula = (1 / exchangeRate) * newBtcvalue;
                        bitcoinValue.setText(decimalFormat.format(conversionFormula));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
