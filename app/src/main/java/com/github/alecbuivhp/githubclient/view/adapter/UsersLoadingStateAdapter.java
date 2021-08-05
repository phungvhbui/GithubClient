package com.github.alecbuivhp.githubclient.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.paging.LoadState;
import androidx.paging.LoadStateAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.github.alecbuivhp.githubclient.R;
import org.jetbrains.annotations.NotNull;

public class UsersLoadingStateAdapter extends LoadStateAdapter<UsersLoadingStateAdapter.NetworkStateViewHolder> {
    // Define Retry Callback
    private final View.OnClickListener retryCallback;

    public UsersLoadingStateAdapter(View.OnClickListener retryCallback) {
        // Init Retry Callback
        this.retryCallback = retryCallback;
    }

    @Override
    public void onBindViewHolder(@NotNull NetworkStateViewHolder networkStateViewHolder, @NotNull LoadState loadState) {
        networkStateViewHolder.bindLoadingState(loadState);
    }

    @NotNull
    @Override
    public NetworkStateViewHolder onCreateViewHolder(@NotNull ViewGroup viewGroup, @NotNull LoadState loadState) {
        return new NetworkStateViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.loading_state, viewGroup, false), retryCallback);
    }

    static class NetworkStateViewHolder extends RecyclerView.ViewHolder {
        View loadingStateItem;
        View.OnClickListener retryCallback;

        public NetworkStateViewHolder(@NonNull @NotNull View loadingStateItem, @NonNull View.OnClickListener retryCallback) {
            super(loadingStateItem);
            this.loadingStateItem = loadingStateItem;
            this.retryCallback = retryCallback;
        }

        void bindLoadingState(LoadState loadState) {
            ProgressBar progressBar = loadingStateItem.findViewById(R.id.progress_bar);
            Button retryButton = loadingStateItem.findViewById(R.id.retry_button);
            TextView errMsg = loadingStateItem.findViewById(R.id.error_msg);

            retryButton.setOnClickListener(retryCallback);
            // Check load state
            if (loadState instanceof LoadState.Error) {
                // Get the error
                LoadState.Error loadStateError = (LoadState.Error) loadState;
                // Set text of Error message
                errMsg.setText(loadStateError.getError().getLocalizedMessage());

            }
            // set visibility of widgets based on LoadState
            progressBar.setVisibility(loadState instanceof LoadState.Loading
                    ? View.VISIBLE : View.GONE);
            retryButton.setVisibility(loadState instanceof LoadState.Error
                    ? View.VISIBLE : View.GONE);
            errMsg.setVisibility(loadState instanceof LoadState.Error
                    ? View.VISIBLE : View.GONE);
        }
    }
}
