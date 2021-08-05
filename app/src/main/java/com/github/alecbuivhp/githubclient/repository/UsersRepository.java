package com.github.alecbuivhp.githubclient.repository;

import com.apollographql.apollo.api.Response;
import com.github.alecbuivhp.githubclient.SearchUserQuery;
import io.reactivex.rxjava3.core.Observable;

public interface UsersRepository {
    Observable<Response<SearchUserQuery.Data>> searchUsers(String query, String after);
}
