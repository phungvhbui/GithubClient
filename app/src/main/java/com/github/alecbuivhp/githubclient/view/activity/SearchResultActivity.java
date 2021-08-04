package com.github.alecbuivhp.githubclient.view.activity;

import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.github.alecbuivhp.githubclient.R;

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