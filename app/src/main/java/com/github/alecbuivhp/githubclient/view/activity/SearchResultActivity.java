package com.github.alecbuivhp.githubclient.view.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.github.alecbuivhp.githubclient.R;
import com.github.alecbuivhp.githubclient.util.UserComparator;
import com.github.alecbuivhp.githubclient.view.adapter.UsersAdapter;
import com.github.alecbuivhp.githubclient.viewmodel.UsersViewModel;
import com.github.alecbuivhp.githubclient.viewmodel.UsersViewModelFactory;

public class SearchResultActivity extends AppCompatActivity {
    private String searchText;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        recyclerView = findViewById(R.id.user_list);
        searchText = getIntent().getStringExtra("search_input");

        // Create Adapter
        UsersAdapter usersAdapter = new UsersAdapter(new UserComparator());

        // Create ViewModel
        UsersViewModel usersViewModel = new ViewModelProvider(this, new UsersViewModelFactory(searchText)).get(UsersViewModel.class);

        // Subscribe
        usersViewModel.pagingDataFlow.subscribe(userPagingData -> usersAdapter.submitData(getLifecycle(), userPagingData));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(usersAdapter);
    }
}