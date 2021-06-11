package com.example.aplicatievaccinare;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class RecyclerViewAdapter extends  RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    public static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mTitles;
    private ArrayList<String> mTimes;
    private ArrayList<String> mImageUrls;
    private Context mContext;

    public RecyclerViewAdapter(Context context, ArrayList<String> titles, ArrayList<String> times, ArrayList<String> imageUrls) {
        mTitles = titles;
        mTimes = times;
        mImageUrls = imageUrls;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        Glide.with(mContext)
                .asBitmap()
                .load(mImageUrls.get(position))
                .into(holder.image);

        holder.title.setText(mTitles.get(position));
        holder.time.setText(mTimes.get(position));

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on an image: " + mTitles.get(position));
                //TODO Launch new activity with info from article and change HttpReqTask function
                new HttpReqTask().execute();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageUrls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        TextView time;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_view);
            title = itemView.findViewById(R.id.title);
            time = itemView.findViewById(R.id.time);
        }
    }

    private class HttpReqTask extends AsyncTask<Void, Void, VaccineCenter[]> {

        @Override
        protected VaccineCenter[] doInBackground(Void... params) {

            try{
                String apiUrl = "http://192.168.1.106:8080/centers/44.5/26/";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                VaccineCenter[] vaccineCenters = restTemplate.getForObject(apiUrl, VaccineCenter[].class);

                return vaccineCenters;
            } catch (Exception e) {
                Log.e("", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(VaccineCenter[] vaccineCenters) {
            super.onPostExecute(vaccineCenters);
            if (vaccineCenters != null) {
                for (VaccineCenter center : vaccineCenters){
                    Log.i("Vaccine center name: ", String.valueOf(center.getName()));
                }
            } else {
                Log.i("Client Error", " No vaccine center in that range");
            }

        }
    }
}
