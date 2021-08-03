package com.github.alecbuivhp.githubclient;

import android.content.Intent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    EditText searchBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get search input text
        searchBox = findViewById(R.id.search_box);

        // Search input
        searchBox.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String searchInput = searchBox.getText().toString();
                if(!searchInput.equals("")){
                    Intent intent = new Intent(MainActivity.this, SearchResultActivity.class);
                    intent.putExtra("search_input", searchInput);
                    startActivity(intent);
                }
            }
            return false;
        });
    }
}