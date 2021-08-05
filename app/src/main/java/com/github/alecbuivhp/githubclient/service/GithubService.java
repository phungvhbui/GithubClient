package com.github.alecbuivhp.githubclient.service;

import com.apollographql.apollo.ApolloClient;
import com.github.alecbuivhp.githubclient.BuildConfig;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class GithubService {
    private GithubService() {
    }

    public static ApolloClient getApolloClient() {
        return SingletonHelper.INSTANCE;
    }

    private static class SingletonHelper {
        private static final ApolloClient INSTANCE = ApolloClient.builder()
                .serverUrl("https://api.github.com/graphql")
                .okHttpClient(HTTPClient.getOkHttpClientInstance())
                .build();
    }


    private static class NetworkInterceptor implements Interceptor {
        @NotNull
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            Request.Builder builder = original.newBuilder().method(original.method(), original.body());
            builder.addHeader("Authorization", "Bearer " + BuildConfig.API_KEY);
            return chain.proceed(builder.build());
        }
    }

    private static class HTTPClient {
        private HTTPClient() {
        }

        public static OkHttpClient getOkHttpClientInstance() {
            return SingletonHelper.INSTANCE;
        }

        private static class SingletonHelper {
            private static final OkHttpClient INSTANCE = new OkHttpClient.Builder()
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .addInterceptor(new NetworkInterceptor())
                    .build();
        }
    }
}