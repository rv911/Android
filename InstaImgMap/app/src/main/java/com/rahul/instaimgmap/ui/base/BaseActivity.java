package com.rahul.instaimgmap.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.rahul.instaimgmap.dialogs.CustomAlertDialogs;
import com.rahul.instaimgmap.dialogs.CustomProgressDialogs;
import com.rahul.instaimgmap.utils.AppUtils;

import butterknife.ButterKnife;

/**
 * Created by rahul on 25/11/17
 */

public abstract class BaseActivity<T extends BaseContract> extends AppCompatActivity
        implements BaseView {

    private T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentResourceId());
        ButterKnife.bind(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        init(savedInstanceState);
    }

    protected abstract int getContentResourceId();

    protected abstract void init(Bundle savedInstanceState);

    public T getPresenter() {
        return mPresenter;
    }

    @SuppressWarnings("unchecked")
    public void setPresenter(T mPresenter) {
        this.mPresenter = mPresenter;
        mPresenter.attach(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showAlertDialog(String title, String message) {
        CustomProgressDialogs.hideProgressDialog(this);
        CustomAlertDialogs.showSimpleAlertDialog(this, title, message);
    }

    @Override
    public void showLoadingDialog(String message) {
        CustomProgressDialogs.showProgressDialog(this, null, message);
    }

    @Override
    public void hideLoadingDialog() {
        CustomProgressDialogs.hideProgressDialog(this);
    }

    @Override
    public void showToastMsg(String message) {
        AppUtils.showToastMsg(this, message);
    }

    @Override
    public void showLog(String message) {
        AppUtils.showLog("rv911 - " + mPresenter.getClass().getSimpleName(), message);
    }

    @Override
    protected void onDestroy() {
        mPresenter.detach();
        super.onDestroy();
    }
}
