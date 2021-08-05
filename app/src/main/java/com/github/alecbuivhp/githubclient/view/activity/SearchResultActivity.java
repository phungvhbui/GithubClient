package com.github.alecbuivhp.githubclient.view.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.github.alecbuivhp.githubclient.R;
import com.github.alecbuivhp.githubclient.SearchUserQuery;
import com.github.alecbuivhp.githubclient.util.UserComparator;
import com.github.alecbuivhp.githubclient.view.adapter.UsersAdapter;
import com.github.alecbuivhp.githubclient.viewmodel.UsersViewModel;
import com.github.alecbuivhp.githubclient.viewmodel.UsersViewModelFactory;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class SearchResultActivity extends AppCompatActivity {
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        RecyclerView recyclerView = findViewById(R.id.user_list);
        String searchText = getIntent().getStringExtra("search_input");

        // Create Adapter
        UsersAdapter usersAdapter = new UsersAdapter(new UserComparator());
        usersAdapter.setUserListener(user -> {
            Intent intent = new Intent(SearchResultActivity.this, ShowUserActivity.class);
            intent.putExtra("login", ((SearchUserQuery.AsUser) user).login());
            startActivity(intent);
        });

        // Create ViewModel
        UsersViewModel usersViewModel = new ViewModelProvider(this, new UsersViewModelFactory(searchText)).get(UsersViewModel.class);

        // Subscribe
        compositeDisposable.add(usersViewModel.getPagingDataFlow()
                .subscribe(userPagingData ->
                        usersAdapter.submitData(getLifecycle(), userPagingData)
                )
        );

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(usersAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    @Override
    protected void onPause() {
        super.onPause();
        compositeDisposable.clear();
    }
}