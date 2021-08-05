package com.github.alecbuivhp.githubclient.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import org.jetbrains.annotations.NotNull;

public class UsersViewModelFactory implements ViewModelProvider.Factory {
    private final String input;

    public UsersViewModelFactory(String input) {
        this.input = input;
    }

    @NotNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull @NotNull Class<T> modelClass) {
        if (modelClass == UsersViewModel.class) {
            return (T) new UsersViewModel(input);
        } else {
            return (T) new UserViewModel(input);
        }
    }
}
