package com.github.alecbuivhp.githubclient.repository;

import com.apollographql.apollo.ApolloQueryCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.rx3.Rx3Apollo;
import com.github.alecbuivhp.githubclient.GetUserQuery;
import com.github.alecbuivhp.githubclient.SearchUserQuery;
import com.github.alecbuivhp.githubclient.service.GithubService;
import com.github.alecbuivhp.githubclient.type.SearchType;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UsersRepositoryImp implements UsersRepository {
    // TODO: Can we make this a Singleton?
    @Override
    public Observable<Response<SearchUserQuery.Data>> searchUsers(String query, String after, int pageSize) {
        ApolloQueryCall<SearchUserQuery.Data> call = GithubService.getApolloClient()
                .query(SearchUserQuery
                        .builder()
                        .query(query)
                        .type(SearchType.USER)
                        .first(pageSize)
                        .after(after)
                        .build());

        return Rx3Apollo.from(call)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter((dataResponse -> dataResponse.getData() != null));
    }

    @Override
    public Observable<Response<GetUserQuery.Data>> getUser(String login) {
        ApolloQueryCall<GetUserQuery.Data> call = GithubService.getApolloClient()
                .query(GetUserQuery
                        .builder()
                        .login(login)
                        .build());

        return Rx3Apollo.from(call)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter((dataResponse -> dataResponse.getData() != null));
    }
}
