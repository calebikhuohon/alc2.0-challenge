package com.caleb.cryptocurrencyconverter.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.caleb.cryptocurrencyconverter.ExchangeRate;
import com.caleb.cryptocurrencyconverter.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by caleb on 10/12/17.
 */

public class ExchangeRateAdapter extends ArrayAdapter<ExchangeRate> {

    public ExchangeRateAdapter(Activity context, ArrayList<ExchangeRate> exchangeRates) {
        super(context, 0, exchangeRates);
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position    The position in the list of data that should be displayed in the
     *                    list item view.
     * @param convertView The recycled view to populate.
     * @param parent      The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        //Get the {@link EXchangeRate} object located at this position in the list
        ExchangeRate currentExchangeRate = getItem(position);

        TextView textView = (TextView) listItemView.findViewById(R.id.textView1);
        textView.setText(currentExchangeRate.getText());

        Double exRateValue = currentExchangeRate.getExRateValue();
        DecimalFormat decimalFormat = new DecimalFormat();
        String output = decimalFormat.format(exRateValue);
        TextView exRateTextview = (TextView) listItemView.findViewById(R.id.textView2);
        exRateTextview.setText(output);

        return listItemView;
    }
}
