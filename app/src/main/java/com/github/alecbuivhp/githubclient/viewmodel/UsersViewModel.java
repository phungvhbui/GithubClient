package com.github.alecbuivhp.githubclient.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;
import com.github.alecbuivhp.githubclient.SearchUserQuery;
import com.github.alecbuivhp.githubclient.repository.pagingsource.UsersPagingSource;
import io.reactivex.rxjava3.core.Flowable;
import kotlinx.coroutines.CoroutineScope;

public class UsersViewModel extends ViewModel {
    private final Flowable<PagingData<SearchUserQuery.Edge>> pagingDataFlow;

    public UsersViewModel(String query) {
        UsersPagingSource usersPagingSource = new UsersPagingSource(query);

        // Create new Pager
        Pager<String, SearchUserQuery.Edge> pager = new Pager<>(
                // Create new paging config
                new PagingConfig(20, // pageSize - Count of items in one page
                        20, // prefetchDistance - Number of items to prefetch
                        false, // enablePlaceholders - Enable placeholders for data which is not yet loaded
                        20, // initialLoadSize - Count of items to be loaded initially
                        20 * 499),// maxSize - Count of total items to be shown in recyclerview
                () -> usersPagingSource); // set paging source

        // init Flowable
        this.pagingDataFlow = PagingRx.getFlowable(pager);
        CoroutineScope coroutineScope = ViewModelKt.getViewModelScope(this);
        PagingRx.cachedIn(this.pagingDataFlow, coroutineScope);
    }
    
    public Flowable<PagingData<SearchUserQuery.Edge>> getPagingDataFlow() {
        return this.pagingDataFlow;
    }
}
