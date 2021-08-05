package com.github.alecbuivhp.githubclient.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.github.alecbuivhp.githubclient.R;
import com.github.alecbuivhp.githubclient.SearchUserQuery;
import com.github.alecbuivhp.githubclient.view.listener.UserListener;
import org.jetbrains.annotations.NotNull;

public class UsersAdapter extends PagingDataAdapter<SearchUserQuery.Edge, UsersAdapter.UserViewHolder> {
    private UserListener userListener;

    public UsersAdapter(@NotNull DiffUtil.ItemCallback<SearchUserQuery.Edge> diffCallback) {
        super(diffCallback);
    }

    public void setUserListener(UserListener userListener) {
        this.userListener = userListener;
    }

    @NonNull
    @NotNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new UserViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull UserViewHolder holder, int position) {
        // Get current movie
        SearchUserQuery.Edge userEdge = getItem(position);
        // Check for null
        if (userEdge != null) {
            SearchUserQuery.Node userNode = userEdge.node();
            // Bind data to view
            holder.bindUserItem(userNode);
        }
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        View userItem;

        public UserViewHolder(@NonNull View userItem) {
            super(userItem.getRootView());
            this.userItem = userItem;
        }

        void bindUserItem(SearchUserQuery.Node user) {
            if (user instanceof SearchUserQuery.AsUser) {
                TextView userName = userItem.findViewById(R.id.user_name);
                TextView userUsername = userItem.findViewById(R.id.user_username);
                TextView userBio = userItem.findViewById(R.id.user_bio);
                userName.setText(((SearchUserQuery.AsUser) user).name());
                userUsername.setText(((SearchUserQuery.AsUser) user).login());
                userBio.setText(((SearchUserQuery.AsUser) user).bio());

                ImageView userAvatar = userItem.findViewById(R.id.user_avatar);
                Glide.with(userAvatar).load(((SearchUserQuery.AsUser) user).avatarUrl())
                        .placeholder(R.color.white)
                        .transition(DrawableTransitionOptions.withCrossFade(500))
                        .circleCrop()
                        .into(userAvatar);

                userItem.setOnClickListener(v -> {
                    if (userListener != null) {
                        userListener.onUserClick(user);
                    }
                });
            }
        }
    }
}