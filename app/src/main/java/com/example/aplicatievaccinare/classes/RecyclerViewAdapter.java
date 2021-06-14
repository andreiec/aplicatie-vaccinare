package com.example.aplicatievaccinare.classes;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aplicatievaccinare.ArticlePageActivity;
import com.example.aplicatievaccinare.BuildConfig;
import com.example.aplicatievaccinare.R;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;

public class RecyclerViewAdapter extends  RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    public static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mTitles;
    private ArrayList<String> mTimes;
    private ArrayList<String> mImageUrls;
    private Context mContext;
    private ArrayList<ArticleListing> articleListings;

    public RecyclerViewAdapter(Context context, ArrayList<String> titles, ArrayList<String> times, ArrayList<String> imageUrls, ArrayList<ArticleListing> listings) {
        mTitles = titles;
        mTimes = times;
        mImageUrls = imageUrls;
        mContext = context;
        articleListings = listings;
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

                // Call API for click on article
                new HttpReqTask().execute(articleListings.get(position).getId());
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

    private class HttpReqTask extends AsyncTask<Integer, Void, Article> {

        @Override
        protected Article doInBackground(Integer... id) {

            try{
                String apiUrl = "http://" + BuildConfig.SERVER_IP + ":8080/articles/" + id[0];
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Article article = restTemplate.getForObject(apiUrl, Article.class);

                return article;
            } catch (Exception e) {
                Log.e("", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Article article) {
            super.onPostExecute(article);
            if (article != null) {
                try {
                    Intent i = new Intent(mContext, ArticlePageActivity.class);
                    i.putExtra("title", article.getTitle());
                    i.putExtra("id", article.getId());
                    i.putExtra("picture", article.getPicture());
                    i.putExtra("body", article.getBody());
                    i.putExtra("readingTime", article.getReadingTime());
                    i.putExtra("type", article.getType());
                    mContext.startActivity(i);
                } catch (Exception e) {
                    Log.i("Not found", Arrays.toString(e.getStackTrace()));
                }
            } else {
                Log.i("API Error", "Could not get requested article.");
            }
        }
    }
}
