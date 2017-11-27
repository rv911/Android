package com.rahul.instaimgmap.ui.base;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rahul.instaimgmap.dialogs.CustomAlertDialogs;
import com.rahul.instaimgmap.dialogs.CustomProgressDialogs;
import com.rahul.instaimgmap.utils.AppUtils;

import butterknife.ButterKnife;

/**
 * Created by rahul on 26/11/17
 */

public abstract class BaseFragment<T extends BaseContract> extends Fragment implements BaseView {

    private T mPresenter;
    private Activity mActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getContentResourceId(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mActivity = getActivity();
        init(view, savedInstanceState);
    }

    protected abstract int getContentResourceId();

    protected abstract void init(View view, @Nullable Bundle savedInstanceState);

    public T getPresenter() {
        return mPresenter;
    }

    @SuppressWarnings("unchecked")
    public void setPresenter(T mPresenter) {
        this.mPresenter = mPresenter;
        mPresenter.attach(this);
    }

    @Override
    public void showAlertDialog(String title, String message) {
        CustomProgressDialogs.hideProgressDialog(mActivity);
        CustomAlertDialogs.showSimpleAlertDialog(mActivity, title, message);
    }

    @Override
    public void showLoadingDialog(String message) {
        CustomProgressDialogs.showProgressDialog(mActivity, null, message);
    }

    @Override
    public void hideLoadingDialog() {
        CustomProgressDialogs.hideProgressDialog(mActivity);
    }

    @Override
    public void showToastMsg(String message) {
        AppUtils.showToastMsg(mActivity, message);
    }

    @Override
    public void showLog(String message) {
        AppUtils.showLog("rv911 - " + mPresenter.getClass().getSimpleName(), message);
    }

    @Override
    public void onDetach() {
        mPresenter.detach();
        super.onDetach();
    }
}
