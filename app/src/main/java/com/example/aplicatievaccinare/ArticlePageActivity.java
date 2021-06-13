package com.example.aplicatievaccinare;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

public class ArticlePageActivity extends AppCompatActivity {

    TextView articleBody;
    TextView articleTitle;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_page);

        articleTitle = findViewById(R.id.articleTitle);
        articleBody = findViewById(R.id.articleBody);

        String title = getIntent().getStringExtra("title");
        String body = getIntent().getStringExtra("body");

        articleTitle.setText(title);
        articleBody.setText(body);
    }
}