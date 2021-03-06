package com.nournexus.weatherapp.adapters;

/**
 * Created by Andre on 4/5/2017.
 */

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.nournexus.weatherapp.R;
import com.nournexus.weatherapp.WeatherActivity;
import com.nournexus.weatherapp.classes.WeatherClass;
import com.nournexus.weatherapp.main.YahooWeatherService;
import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class WeatherAdapter extends ArrayAdapter {
    public static ProgressDialog loadingDialog;
    private boolean isRunning = false;
    Handler handler = new Handler();

    public List<WeatherClass> list= new ArrayList();

    public WeatherAdapter(Context context, int resource) {
        super(context, resource);
        // TODO Auto-generated constructor stub
    }
    public void add(WeatherClass object) {
        // TODO Auto-generated method stub
        list.add(object);
        super.add(object);
    }
    static class ImgHolder
    {
        TextView NAME;
        TextView LOC;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return this.list.size();
    }
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return this.list.get(getCount() - position - 1);
    }




    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        // TODO Auto-generated method stub
        View row;
        row = convertView;
        final ImgHolder holder;




        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.row_layout,parent,false);
            holder = new ImgHolder();
            holder.NAME = (TextView) row.findViewById(R.id.loc_name);
            holder.LOC = (TextView) row.findViewById(R.id.loc_coord);
            row.setTag(holder);
        }
        else
        {
            holder = (ImgHolder) row.getTag();
        }
        final Runnable logCheck = new Runnable(){
            @Override
            public void run()
            {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialog = new ProgressDialog(parent.getContext());
                        loadingDialog.setMessage("Loading...");
                        loadingDialog.setCancelable(false);
                        loadingDialog.show();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                YahooWeatherService.refreshWeather(holder.LOC.getText().toString());
                                loadingDialog.dismiss();
                                isRunning = false;
                            }
                        }, 1000);
                    }
                });
            }
        };
        final WeatherClass FR = (WeatherClass) getItem(position);
        holder.NAME.setText(FR.getLoc_name());
        holder.NAME.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if(isRunning == false){
                    isRunning = true;
                    //Toast.makeText(parent.getContext(), String.format("%s", holder.LOC.getText().toString()), Toast.LENGTH_SHORT).show();
                    handler.post(logCheck);
                }
            }
        });
        holder.LOC.setText(FR.getLoc_qty());
        holder.LOC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if(isRunning == false){
                    isRunning = true;
                    //Toast.makeText(parent.getContext(), String.format("%s", holder.LOC.getText().toString()), Toast.LENGTH_SHORT).show();
                    handler.post(logCheck);
                }
            }
        });
        return row;
    }
}
