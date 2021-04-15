package com.example.aplicatievaccinare;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ExploreFragment extends Fragment {

    private static final String TAG = "ExploreFragment";

    private ArrayList<String> mTitlesInfo = new ArrayList<>();
    private ArrayList<String> mTimesInfo = new ArrayList<>();
    private ArrayList<String> mImageUrlsInfo = new ArrayList<>();

    private ArrayList<String> mTitlesNews = new ArrayList<>();
    private ArrayList<String> mTimesNews = new ArrayList<>();
    private ArrayList<String> mImageUrlsNews = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private View view;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ExploreFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
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
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_explore, container, false);

        getImagesInfo();
        getImagesNews();

        return view;
    }
    private void getImagesInfo() {
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        mImageUrlsInfo.add("https://i.imgur.com/Wwx25wY.jpg");
        mTitlesInfo.add("Test 1");
        mTimesInfo.add("x minute");

        mImageUrlsInfo.add("https://i.imgur.com/Wwx25wY.jpg");
        mTitlesInfo.add("Test 12");
        mTimesInfo.add("x2 minute");

        mImageUrlsInfo.add("https://i.imgur.com/Wwx25wY.jpg");
        mTitlesInfo.add("Test 13");
        mTimesInfo.add("x3 minute");

        mImageUrlsInfo.add("https://i.imgur.com/Wwx25wY.jpg");
        mTitlesInfo.add("Test 14");
        mTimesInfo.add("x4 minute");

        initRecyclerViewInfo();
    }

    private void getImagesNews() {
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        mImageUrlsNews.add("https://i.imgur.com/cH3a6Ye.png");
        mTitlesNews.add("Test 2");
        mTimesNews.add("x2 minute");

        mImageUrlsNews.add("https://i.imgur.com/cH3a6Ye.png");
        mTitlesNews.add("Test 22");
        mTimesNews.add("x4 minute");

        mImageUrlsNews.add("https://i.imgur.com/cH3a6Ye.png");
        mTitlesNews.add("Test 23");
        mTimesNews.add("x5 minute");

        mImageUrlsNews.add("https://i.imgur.com/cH3a6Ye.png");
        mTitlesNews.add("Test 24");
        mTimesNews.add("x6 minute");

        mImageUrlsNews.add("https://i.imgur.com/cH3a6Ye.png");
        mTitlesNews.add("Test 25");
        mTimesNews.add("x8 minute");

        mImageUrlsNews.add("https://i.imgur.com/cH3a6Ye.png");
        mTitlesNews.add("Test 26");
        mTimesNews.add("x10 minute");

        initRecyclerViewNews();
    }

    private void initRecyclerViewInfo() {
        Log.d(TAG, "initRecyclerViewInfo: init recyclerview");

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        RecyclerView recyclerViewInfo = (RecyclerView) view.findViewById(R.id.recyclerViewInfo);
        recyclerViewInfo.setLayoutManager(layoutManager);

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity(), mTitlesInfo, mTimesInfo, mImageUrlsInfo);
        recyclerViewInfo.setAdapter(adapter);
    }

    private void initRecyclerViewNews() {
        Log.d(TAG, "initRecyclerViewNews: init recyclerview");

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        RecyclerView recyclerViewInfo = (RecyclerView) view.findViewById(R.id.recyclerViewNews);
        recyclerViewInfo.setLayoutManager(layoutManager);

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity(), mTitlesNews, mTimesNews, mImageUrlsNews);
        recyclerViewInfo.setAdapter(adapter);
    }

}