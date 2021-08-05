package com.github.alecbuivhp.githubclient.view.listener;

import com.github.alecbuivhp.githubclient.SearchUserQuery;

public interface UserListener {
    void onUserClick(SearchUserQuery.Node user);
}
