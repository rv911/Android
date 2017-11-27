package com.rahul.instaimgmap.ui.profile;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.rahul.instaimgmap.AppClass;
import com.rahul.instaimgmap.R;
import com.rahul.instaimgmap.custom.LabelTextView;
import com.rahul.instaimgmap.models.GeoImage;
import com.rahul.instaimgmap.models.userProfile.Counts;
import com.rahul.instaimgmap.models.userProfile.ModelUserProfile;
import com.rahul.instaimgmap.models.userProfile.User;
import com.rahul.instaimgmap.service.FeedReceiver;
import com.rahul.instaimgmap.service.FeedService;
import com.rahul.instaimgmap.ui.base.BaseActivity;
import com.rahul.instaimgmap.ui.gallery.GalleryActivity;
import com.rahul.instaimgmap.ui.gallery.GalleryFragment;
import com.rahul.instaimgmap.ui.login.LoginActivity;
import com.rahul.instaimgmap.ui.map.MapsActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;

public class ProfileActivity extends BaseActivity<ProfileContract.Presenter>
        implements ProfileContract.ProfileView {

    @BindView(R.id.ivProfilePic)
    CircleImageView ivProfilePic;

    @BindView(R.id.ltvPosts)
    LabelTextView ltvPosts;

    @BindView(R.id.ltvFollowers)
    LabelTextView ltvFollowers;

    @BindView(R.id.ltvFollowing)
    LabelTextView ltvFollowing;

    @BindView(R.id.btnImageList)
    ImageView btnImageList;

    @BindView(R.id.btnShowImageMap)
    ImageView btnShowImageMap;

    private FeedReceiver mFeedReceiver;

    @Override
    protected int getContentResourceId() {
        return R.layout.activity_profile;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setPresenter(new ProfilePresenter());
        getPresenter().getUserProfile();
        startFeedService();
    }

    private void startFeedService() {
        IntentFilter filter = new IntentFilter(FeedService.RC_CODE);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        mFeedReceiver = new FeedReceiver();
        registerReceiver(mFeedReceiver, filter);
    }

    @OnClick(R.id.btnImageList)
    void onClickShowGallery() {
        Intent intent = new Intent(this, GalleryActivity.class);
        intent.putExtra("data", getPresenter().getImageJson());
        startActivity(intent);
    }

    @OnClick(R.id.btnShowImageMap)
    void onClickShowImageMap() {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("data", getPresenter().getImageJson());
        startActivity(intent);
    }

    @Override
    public void setupProfile(ModelUserProfile userProfile) {
        User user = userProfile.getData();
        if (user != null) {
            setupUserProfile(user);
            if (user.getCounts() != null) {
                setupSocialCount(user.getCounts());
            }
        }
    }

    @Override
    public void setupUserProfile(User user) {
        String imgUrl = user.getProfilePicture();
        Glide.with(this).load(imgUrl).apply(fitCenterTransform()).into(ivProfilePic);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(user.getFullName());
        }
    }

    @Override
    public void setupSocialCount(Counts counts) {
        ltvPosts.getTvLabel().setText(String.valueOf(counts.getMedia()));
        ltvFollowers.getTvLabel().setText(String.valueOf(counts.getFollowedBy()));
        ltvFollowing.getTvLabel().setText(String.valueOf(counts.getFollows()));
    }

    @Override
    public void setupImageGrid(ArrayList<GeoImage> geoImages) {
        Fragment fragment = GalleryFragment.getInstance(getPresenter().getImageJson());
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sync:
                Intent feedServiceIntent = new Intent(this, FeedService.class);
                feedServiceIntent.putExtra("data", FeedService.RC_CODE);
                startService(feedServiceIntent);
                break;

            case R.id.menu_logout:
                logout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupImage(ImageView imageView, String url) {
        Glide.with(this).load(url).apply(fitCenterTransform()).into(imageView);
    }

    public String getDate(long milliSeconds) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    private void logout() {
        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        new AppClass().getPreference().setAccessToken("");
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        this.unregisterReceiver(mFeedReceiver);
        super.onDestroy();
    }

}
