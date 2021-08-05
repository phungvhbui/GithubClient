package com.github.alecbuivhp.githubclient.repository.pagingsource;

import androidx.annotation.NonNull;
import androidx.paging.PagingState;
import androidx.paging.rxjava3.RxPagingSource;
import com.apollographql.apollo.api.Response;
import com.github.alecbuivhp.githubclient.SearchUserQuery;
import com.github.alecbuivhp.githubclient.repository.UsersRepository;
import com.github.alecbuivhp.githubclient.repository.UsersRepositoryImp;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class UsersPagingSource extends RxPagingSource<String, SearchUserQuery.Edge> {
    private final String query;
    @NonNull
    private final UsersRepository usersRepository;

    public UsersPagingSource(String query) {
        this.query = query;
        this.usersRepository = new UsersRepositoryImp();
    }

    @NotNull
    @Override
    public Single<LoadResult<String, SearchUserQuery.Edge>> loadSingle(@NotNull LoadParams<String> loadParams) {
        try {
            return usersRepository.searchUsers(query, loadParams.getKey())
                    .subscribeOn(Schedulers.io())
                    .singleOrError()
                    .map(this::toLoadResult);
        } catch (Exception e) {
            // Request ran into error return error
            return Single.just(new LoadResult.Error(e));
        }
    }

    @NotNull
    private LoadResult<String, SearchUserQuery.Edge> toLoadResult(Response<SearchUserQuery.Data> result) {
        SearchUserQuery.Data data = result.getData();
        assert data != null;
        return new LoadResult.Page<>(Objects.requireNonNull(data.search().edges()), data.search().pageInfo().startCursor(), data.search().pageInfo().endCursor());
    }

    @Nullable
    @Override
    public String getRefreshKey(@NotNull PagingState<String, SearchUserQuery.Edge> pagingState) {
        return null;
    }
}
