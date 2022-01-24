package com.example.amit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {
    ArrayList<ForecastModel> arrayList;
    Context context;

    public ForecastAdapter(ArrayList<ForecastModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.forecast_list_item,parent,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
   ForecastModel model=arrayList.get(position);
   holder.fconditiontext.setText(model.getConditionText());
   holder.fwindspeed.setText(model.getWindspeed());
   holder.fhumidity.setText(model.getHumidity());

        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        SimpleDateFormat output = new SimpleDateFormat("mmm, dd");
        try {
            Date t =  input.parse(model.getDate());
            holder.fdate.setText(output.format(t));
        }
        catch (ParseException e){
            e.printStackTrace();
        }

        Picasso.get().load("https:".concat(model.getIcon())).resize(250,250).into(holder.ficon);
         holder.ftemp.setText(model.getTemp()+"°c");
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       public TextView fdate,ftemp,fwindspeed,fhumidity,fconditiontext;
       public ImageView ficon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fdate=itemView.findViewById(R.id.fdate);
            ftemp=itemView.findViewById(R.id.fTemp);
            fwindspeed=itemView.findViewById(R.id.fwind);
            fhumidity=itemView.findViewById(R.id.fhumdity);
            fconditiontext=itemView.findViewById(R.id.fconditionText);
            ficon=itemView.findViewById(R.id.ficon);


        }
    }
}
