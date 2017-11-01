package com.caleb.cryptocurrencyconverter.UI;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.caleb.cryptocurrencyconverter.Adapter.ExchangeRateAdapter;
import com.caleb.cryptocurrencyconverter.ConversionActivity;
import com.caleb.cryptocurrencyconverter.ExchangeRate;

import com.caleb.cryptocurrencyconverter.R;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class BitcoinExchangeRateActivity extends AppCompatActivity {

    public static final String EXRATE_VALUE = "exchange Rate Value";

    public static final String EXRATE_TEXT = "exchange Currency";

    private static final String BASE_URL = "https://min-api.cryptocompare.com/data/pricemulti?fsyms=ETH,BTC&tsyms=NGN,USD,EUR,JPY,GBP,CHF,INR,CAD,DZD,AUD,SGD,CNY,ZAR,NZD,SGD,BTC,ETH";

    public static final String LOG_TAG = BitcoinExchangeRateActivity.class.getSimpleName();

    ArrayList<ExchangeRate> btcExchangeRates = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitcoin_exchange_rate);

        ExchangeRateTask task = new ExchangeRateTask();
        task.execute();


    }


    private void updateUi(final ArrayList<ExchangeRate> exchangeRate) {
        ListView exchangeRateListview = (ListView) findViewById(R.id.list);
        ExchangeRateAdapter adapter = new ExchangeRateAdapter(this, btcExchangeRates);
        exchangeRateListview.setAdapter(adapter);

        exchangeRateListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ExchangeRate exRate = btcExchangeRates.get(i);
                Bundle bundle = new Bundle();
                bundle.putString(EXRATE_TEXT, exRate.getText());
                bundle.putDouble(EXRATE_VALUE, exRate.getExRateValue());
                Intent intent = new Intent(BitcoinExchangeRateActivity.this, ConversionActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }


    /**
     * {@link AsyncTask} to perform the network request on a background thread, and then
     * update the UI with the first earthquake in the response.
     */

    private class ExchangeRateTask extends AsyncTask<Object, Void, ArrayList<ExchangeRate>> {
        @Override
        protected ArrayList<ExchangeRate> doInBackground(Object... urls) {
            //create url object
            URL url = createUrl(BASE_URL);

            //perform HTTP request to the url and receive a JSON response back
            String jsonResponse = "";
            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {

                Toast.makeText(getApplication(), "Failed!", Toast.LENGTH_LONG).show();
            }
            //extract relevant fields from the json response
            ArrayList<ExchangeRate> exchangeRate = extractRatesFromJson(jsonResponse);

            return exchangeRate;

        }

        @Override
        protected void onPostExecute(ArrayList<ExchangeRate> exchangeRates) {
            if (exchangeRates == null) {
                return;
            }
            updateUi(exchangeRates);
        }
    }


    /**
     * Returns new URL object from the given string URL.
     */
    private URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e(LOG_TAG, "Error with creating URL", exception);
            return null;
        }
        return url;
    }

    private String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000/*milliseconds*/);
            urlConnection.setConnectTimeout(15000/*milliseconds*/);
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }


    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private ArrayList<ExchangeRate> extractRatesFromJson(String exchangeRateJson) {
        if (TextUtils.isEmpty(exchangeRateJson)) {
            return null;
        }

        try {
            JSONObject jsonResponse = new JSONObject(exchangeRateJson);
            JSONObject btc = jsonResponse.getJSONObject("BTC");
            double ngn = btc.getDouble("NGN");
            double usd = btc.getDouble("USD");
            double eur = btc.getDouble("EUR");
            double jpy = btc.getDouble("JPY");
            double gbp = btc.getDouble("GBP");
            double chf = btc.getDouble("CHF");
            double inr = btc.getDouble("INR");
            double cad = btc.getDouble("CAD");
            double dzd = btc.getDouble("DZD");
            double aud = btc.getDouble("AUD");
            double sgd = btc.getDouble("SGD");
            double cny = btc.getDouble("CNY");
            double zar = btc.getDouble("ZAR");
            double nzd = btc.getDouble("NZD");
            double eth = btc.getDouble("ETH");


            btcExchangeRates.add(new ExchangeRate("Naira", ngn));
            btcExchangeRates.add(new ExchangeRate("Dollar", usd));
            btcExchangeRates.add(new ExchangeRate("Euro", eur));
            btcExchangeRates.add(new ExchangeRate("Yen", jpy));
            btcExchangeRates.add(new ExchangeRate("Pound", gbp));
            btcExchangeRates.add(new ExchangeRate("Swiss Franc", chf));
            btcExchangeRates.add(new ExchangeRate("Rupee", inr));
            btcExchangeRates.add(new ExchangeRate("Canadian Dollar", cad));
            btcExchangeRates.add(new ExchangeRate("Dinar", dzd));
            btcExchangeRates.add(new ExchangeRate("Austrailia Dollar", aud));
            btcExchangeRates.add(new ExchangeRate("Singapore Dollar", sgd));
            btcExchangeRates.add(new ExchangeRate("China Yuan", cny));
            btcExchangeRates.add(new ExchangeRate("South Africa Rand", zar));
            btcExchangeRates.add(new ExchangeRate("New Zealand Dollar", nzd));
            btcExchangeRates.add(new ExchangeRate("Ethereum", eth));


        } catch (JSONException e)

        {
            Log.e("BitcoinExchangeActivity", "Problem parsing the earthquake JSON results", e);
        }
        return btcExchangeRates;
    }


}




