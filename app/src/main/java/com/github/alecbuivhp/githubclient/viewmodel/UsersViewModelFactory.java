package com.github.alecbuivhp.githubclient.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import org.jetbrains.annotations.NotNull;

public class UsersViewModelFactory implements ViewModelProvider.Factory {
    private final String query;

    public UsersViewModelFactory(String query) {
        this.query = query;
    }

    @NonNull
    @NotNull
    @Override
    public <T extends ViewModel> T create(@NonNull @NotNull Class<T> modelClass) {
        return (T) new UsersViewModel(query);
    }
}
