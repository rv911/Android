package com.rahul.instaimgmap.ui.base;

/**
 * Created by rahul on 25/11/17
 */

public interface BaseContract<V extends BaseView> {

    void attach(V view);

    void detach();

    boolean isAttached();

}
