package com.example.aplicatievaccinare;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aplicatievaccinare.classes.ArticleListing;
import com.example.aplicatievaccinare.classes.RecyclerViewAdapter;
import com.example.aplicatievaccinare.singletons.SaveState;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

public class ExploreFragment extends Fragment {

    private static final String TAG = "ExploreFragment";

    private ArrayList<String> mTitlesInfo = new ArrayList<>();
    private ArrayList<String> mTimesInfo = new ArrayList<>();
    private ArrayList<String> mImageUrlsInfo = new ArrayList<>();

    private ArrayList<String> mTitlesNews = new ArrayList<>();
    private ArrayList<String> mTimesNews = new ArrayList<>();
    private ArrayList<String> mImageUrlsNews = new ArrayList<>();

    private ArrayList<ArticleListing> articleListings = new ArrayList<>();
    private ArrayList<ArticleListing> infoListings = new ArrayList<>();
    private ArrayList<ArticleListing> newsListings = new ArrayList<>();

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private View view;

    private String mParam1;
    private String mParam2;

    public ExploreFragment() {
        // Required empty public constructor
    }

    public static ExploreFragment newInstance(String param1, String param2) {
        ExploreFragment fragment = new ExploreFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_explore, container, false);

        // Call API
        new HttpArticlesRequest().execute();

        return view;
    }

    private void getImages() {
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        for (ArticleListing articleListing : articleListings) {
            if (articleListing.getType() == 1) {
                mImageUrlsInfo.add(articleListing.getPicture());
                mTitlesInfo.add(articleListing.getTitle());
                mTimesInfo.add(articleListing.getReadingTime() + " minute");
                infoListings.add(articleListing);
            } else {
                mImageUrlsNews.add(articleListing.getPicture());
                mTitlesNews.add(articleListing.getTitle());
                mTimesNews.add(articleListing.getReadingTime() + " minute");
                newsListings.add(articleListing);
            }

        }

        initRecyclerViewNews();
        initRecyclerViewInfo();
    }

    private void initRecyclerViewInfo() {
        Log.d(TAG, "initRecyclerViewInfo: init recyclerview");

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        RecyclerView recyclerViewInfo = (RecyclerView) view.findViewById(R.id.recyclerViewInfo);
        recyclerViewInfo.setLayoutManager(layoutManager);

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity(), mTitlesInfo, mTimesInfo, mImageUrlsInfo, infoListings);
        recyclerViewInfo.setAdapter(adapter);
    }

    private void initRecyclerViewNews() {
        Log.d(TAG, "initRecyclerViewNews: init recyclerview");

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        RecyclerView recyclerViewInfo = (RecyclerView) view.findViewById(R.id.recyclerViewNews);
        recyclerViewInfo.setLayoutManager(layoutManager);

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity(), mTitlesNews, mTimesNews, mImageUrlsNews, newsListings);
        recyclerViewInfo.setAdapter(adapter);
    }

    // API Request to gett all vaccine centers
    private class HttpArticlesRequest extends AsyncTask<Void, Void, ArticleListing[]> {

        @Override
        protected ArticleListing[] doInBackground(Void... params) {

            try{
                String apiUrl = "http://" + BuildConfig.SERVER_IP + ":8080/articles/";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                ArticleListing[] articleListings = restTemplate.getForObject(apiUrl, ArticleListing[].class);

                return articleListings;
            } catch (Exception e) {
                Log.e("", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArticleListing[] ac) {
            super.onPostExecute(ac);
            articleListings.clear();

            if (ac != null) {
                for (ArticleListing articleListing : ac){
                    Log.i("Article Listing", String.valueOf(articleListing.getTitle()));
                    articleListings.add(articleListing);
                }
            } else {
                Log.i("Client Error", "No articles found");
            }

            // After API call function to put articles in app
            getImages();
        }
    }
}
