package com.github.alecbuivhp.githubclient.util;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import com.github.alecbuivhp.githubclient.SearchUserQuery;
import org.jetbrains.annotations.NotNull;

public class UserComparator extends DiffUtil.ItemCallback<SearchUserQuery.Edge> {
    @Override
    public boolean areItemsTheSame(@NonNull @NotNull SearchUserQuery.Edge oldItem, @NonNull @NotNull SearchUserQuery.Edge newItem) {
        return compareItem(oldItem, newItem);
    }

    private boolean compareItem(@NotNull @NonNull SearchUserQuery.Edge oldItem, @NotNull @NonNull SearchUserQuery.Edge newItem) {
        SearchUserQuery.Node oldItemNode = oldItem.node();
        SearchUserQuery.Node newItemNode = newItem.node();
        if (oldItemNode == null || newItemNode == null) {
            return false;
        }
        return ((SearchUserQuery.AsUser) oldItemNode).id().equals(((SearchUserQuery.AsUser) newItemNode).id());
    }

    @Override
    public boolean areContentsTheSame(@NonNull SearchUserQuery.Edge oldItem, @NotNull SearchUserQuery.Edge newItem) {
        return compareItem(oldItem, newItem);
    }
}
