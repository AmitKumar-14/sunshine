package com.example.amit;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {
    ArrayList<WeatherModel> arrayList;
    Context context;

    public WeatherAdapter(ArrayList<WeatherModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
         return  new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
         WeatherModel model=arrayList.get(position);

         holder.condtionText.setText(model.getConditionText());
         holder.maxTemp.setText(model.getMaxTemp()+"°c");
         holder.minTemp.setText(model.getMinTemp()+"°c");
         try{
             Picasso.get().load("https:".concat(model.getIcon())).resize(150,150).into(holder.LVicon);
         } catch (IllegalArgumentException e) {
             Log.d("tag",e.getMessage(),e);
             e.printStackTrace();
         }


        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        SimpleDateFormat output = new SimpleDateFormat("hh:mm aa");
        try {
            Date t =  input.parse(model.getDate());
            holder.date.setText(output.format(t));
        }
        catch (ParseException e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView date;
        public TextView condtionText;
        public TextView maxTemp;
        public TextView minTemp;
        public ImageView LVicon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.LVDate);
            condtionText=itemView.findViewById(R.id.LVconditionText);
            maxTemp=itemView.findViewById(R.id.LVMaxtemp);
            minTemp=itemView.findViewById(R.id.LVMintemp);
            LVicon=itemView.findViewById(R.id.LVIcon);

        }
    }
}
