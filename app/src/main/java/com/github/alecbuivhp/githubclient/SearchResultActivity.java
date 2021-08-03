package com.github.alecbuivhp.githubclient;

import android.content.Intent;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class SearchResultActivity extends AppCompatActivity {
    private String searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        searchText = getIntent().getStringExtra("search_input");

        Toast.makeText(SearchResultActivity.this, searchText, Toast.LENGTH_SHORT).show();
    }
}