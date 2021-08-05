package com.github.alecbuivhp.githubclient.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.github.alecbuivhp.githubclient.R;
import com.github.alecbuivhp.githubclient.viewmodel.UserViewModel;
import com.github.alecbuivhp.githubclient.viewmodel.UsersViewModelFactory;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

import java.util.Objects;

public class ShowUserActivity extends AppCompatActivity {
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user);

        String login = getIntent().getStringExtra("login");

        TextView name = findViewById(R.id.user_name);
        TextView username = findViewById(R.id.user_username);
        TextView bio = findViewById(R.id.user_bio);
        TextView company = findViewById(R.id.company);
        TextView location = findViewById(R.id.location);
        TextView follower = findViewById(R.id.follower);
        TextView following = findViewById(R.id.following);
        ImageView avatar = findViewById(R.id.user_avatar);

        // Create ViewModel
        UserViewModel userViewModel = new ViewModelProvider(this, new UsersViewModelFactory(login)).get(UserViewModel.class);

        compositeDisposable.add(userViewModel.getUserSingle()
                .subscribe(user -> {
                    if (user.name() == null || Objects.requireNonNull(user.name()).isEmpty())
                        name.setVisibility(View.GONE);
                    else name.setText(user.name());

                    username.setText(user.login());

                    if (user.bio() == null || Objects.requireNonNull(user.bio()).isEmpty())
                        bio.setVisibility(View.GONE);
                    else bio.setText(user.bio());

                    if (user.company() == null || Objects.requireNonNull(user.bio()).isEmpty())
                        company.setVisibility(View.GONE);
                    else company.setText(user.company());

                    if (user.location() == null || Objects.requireNonNull(user.location()).isEmpty())
                        location.setVisibility(View.GONE);
                    else location.setText(user.location());


                    String followerString = user.followers().totalCount() + " " + getResources().getString(R.string.followers);
                    follower.setText(followerString);

                    String followingString = user.following().totalCount() + " " + getResources().getString(R.string.followings);
                    following.setText(followingString);

                    Glide.with(avatar).load(user.avatarUrl())
                            .placeholder(R.color.white)
                            .transition(DrawableTransitionOptions.withCrossFade(500))
                            .circleCrop()
                            .into(avatar);
                })
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    @Override
    protected void onPause() {
        super.onPause();
        compositeDisposable.clear();
    }
}
