package com.rahul.instaimgmap.ui.gallery;

import com.rahul.instaimgmap.ui.base.BaseContract;
import com.rahul.instaimgmap.ui.base.BaseView;

/**
 * Created by rahul on 25/11/17
 */

public interface GalleryContract {

    interface GalleryView extends BaseView {

        void initRecyclerView();

        void setupGallery();

    }

    interface Presenter extends BaseContract<GalleryView> {

        void getImages();

    }

}
