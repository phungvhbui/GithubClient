package com.github.alecbuivhp.githubclient.viewmodel;

import androidx.lifecycle.ViewModel;
import com.apollographql.apollo.api.Response;
import com.github.alecbuivhp.githubclient.GetUserQuery;
import com.github.alecbuivhp.githubclient.repository.UsersRepository;
import com.github.alecbuivhp.githubclient.repository.UsersRepositoryImp;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserViewModel extends ViewModel {
    private final Flowable<GetUserQuery.User> userFlowable;

    public UserViewModel(String login) {
        UsersRepository usersRepository = new UsersRepositoryImp();
        this.userFlowable = usersRepository.getUser(login)
                .subscribeOn(Schedulers.io())
                .toFlowable(BackpressureStrategy.BUFFER)
                .map(this::toUser);
    }

    private GetUserQuery.User toUser(Response<GetUserQuery.Data> result) {
        GetUserQuery.Data data = result.getData();
        assert data != null;
        return data.user();
    }

    public Flowable<GetUserQuery.User> getUserSingle() {
        return this.userFlowable;
    }
}
