package com.github.alecbuivhp.githubclient.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.exception.ApolloException;
import com.github.alecbuivhp.githubclient.BuildConfig;
import com.github.alecbuivhp.githubclient.R;
import com.github.alecbuivhp.githubclient.SearchUserQuery;
import com.github.alecbuivhp.githubclient.type.SearchType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.jetbrains.annotations.NotNull;

public class SearchResultActivity extends AppCompatActivity {
    private String searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        searchText = getIntent().getStringExtra("search_input");

        ApolloClient client = setupApollo();
        client.query(SearchUserQuery
                        .builder().query(searchText).type(SearchType.USER).first(10).after(null).build())
                .enqueue(new ApolloCall.Callback<SearchUserQuery.Data>() {
                    @Override
                    public void onResponse(@NotNull com.apollographql.apollo.api.Response<SearchUserQuery.Data> response) {
                        Log.println(Log.INFO, "RES", String.valueOf(response));
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        Toast.makeText(SearchResultActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private ApolloClient setupApollo() {
        OkHttpClient okHttp = new OkHttpClient
                .Builder().addInterceptor(chain -> {
                    Request original = chain.request();
                    Request.Builder builder = original.newBuilder().method(original.method(), original.body());
                    builder.addHeader("Authorization", "Bearer " + BuildConfig.API_KEY);
                    return chain.proceed(builder.build());
                })
                .build();

        return ApolloClient.builder()
                .serverUrl(BuildConfig.GITHUB_URL)
                .okHttpClient(okHttp)
                .build();
    }
}