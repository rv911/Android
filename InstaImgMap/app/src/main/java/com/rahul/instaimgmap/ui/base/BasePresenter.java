package com.rahul.instaimgmap.ui.base;

/**
 * Created by rahul on 25/11/17
 */

public class BasePresenter<V extends BaseView> implements BaseContract<V> {

    private V mView;

    @Override
    public void attach(V view) {
        mView = view;
    }

    @Override
    public void detach() {
        mView = null;
    }

    @Override
    public boolean isAttached() {
        return mView != null;
    }

    public V getView() {
        return mView;
    }
}
