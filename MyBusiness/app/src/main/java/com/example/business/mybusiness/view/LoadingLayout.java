package com.example.business.mybusiness.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.business.mybusiness.R;
import com.example.business.mybusiness.communication.interfaces.BusinessCallBack;
import com.example.business.mybusiness.communication.model.ResponseModel;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * Created by zhang.la on 2015/9/22.
 */
public class LoadingLayout extends FrameLayout implements BusinessCallBack {
    private static final String LOADING_URL = "http://img1.imgtn.bdimg.com/it/u=3467761871,2497566162&fm=21&gp=0.jpg";

    private BusinessCallBack businessCallBack;

    private LinearLayout loadingView;

    public LoadingLayout(Context context) {
        super(context);
        initView(context);
    }

    public LoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public LoadingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    protected void initView(Context context) {
        this.setBackgroundResource(R.color.transparent);

        loadingView = (LinearLayout) ViewGroup.inflate(context, R.layout.loading_layout, null);
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) loadingView.findViewById(R.id.loading_bg);
        ImageRequest request = ImageRequestBuilder.newBuilderWithResourceId(R.drawable.loading_anim)
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setOldController(simpleDraweeView.getController())
                .setImageRequest(request)
                .setAutoPlayAnimations(true)
                .build();
        simpleDraweeView.setController(controller);

        addView(loadingView);
    }

    @Override
    public void onBusinessSuccess(String token, ResponseModel responseModel) {
        if (null != businessCallBack) {
            businessCallBack.onBusinessSuccess(token, responseModel);
        }
        loadingView.setVisibility(GONE);
    }

    @Override
    public void onBusinessFail(String token, ResponseModel responseModel) {
        if (null != businessCallBack) {
            businessCallBack.onBusinessFail(token, responseModel);
        }
        loadingView.setVisibility(GONE);
    }

    @Override
    public void onBusinessCancel(String token, ResponseModel responseModel) {
        if (null != businessCallBack) {
            businessCallBack.onBusinessCancel(token, responseModel);
        }
        loadingView.setVisibility(GONE);
    }

    public void setBusinessCallBack(BusinessCallBack businessCallBack) {
        this.businessCallBack = businessCallBack;
    }

    public void showProgress() {
        loadingView.bringToFront();
        loadingView.setVisibility(VISIBLE);
    }

    public void removeProgress() {
        loadingView.setVisibility(GONE);
    }
}
