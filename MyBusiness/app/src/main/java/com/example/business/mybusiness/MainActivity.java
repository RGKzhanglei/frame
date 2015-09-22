package com.example.business.mybusiness;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.business.mybusiness.communication.interfaces.BusinessCallBack;
import com.example.business.mybusiness.communication.model.ResponseModel;
import com.example.business.mybusiness.communication.model.SendResultModel;
import com.example.business.mybusiness.page.cachebean.SecondCacheBean;
import com.example.business.mybusiness.page.BaseActivity;
import com.example.business.mybusiness.page.cachebean.FirstCacheBean;
import com.example.business.mybusiness.page.controller.PageJumpController;
import com.example.business.mybusiness.page.model.PageJumpConfig;
import com.example.business.mybusiness.page.model.ServiceConfig;
import com.example.business.mybusiness.sender.FirstSender;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * Created by zhang.la on 2015/9/8.
 */
public class MainActivity extends BaseActivity {
    private FirstCacheBean cacheBean;

    private BusinessCallBack mServiceCallBack = new BusinessCallBack() {
        @Override
        public void onBusinessSuccess(String token, ResponseModel responseModel) {
            showMsg("SUCCESS =======" + token);
        }

        @Override
        public void onBusinessFail(String token, ResponseModel responseModel) {
            showMsg("FAIL =======" + token);
        }

        @Override
        public void onBusinessCancel(String token, ResponseModel responseModel) {
            showMsg("CANCEL =======" + token);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // init fresco
        Fresco.initialize(this);

        setContentView(R.layout.main_activty_layout);
        if (null != mViewBean) {
            cacheBean = (FirstCacheBean) mViewBean;
        } else {
            cacheBean = new FirstCacheBean();
        }
        findViewById(R.id.first_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendFirstService();
            }
        });

        SimpleDraweeView draweeView = (SimpleDraweeView) findViewById(R.id.my_image_view);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse("http://img.hb.aicdn.com/5658d35e111a2d588f350ea947b65b5fd13e9f91e2362-zWMLih_fw580"))
                .setResizeOptions(new ResizeOptions(300, 300))
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setOldController(draweeView.getController())
                .setImageRequest(request)
                .setAutoPlayAnimations(true)
                .build();
        draweeView.setController(controller);

        super.onCreate(savedInstanceState);
    }

    private void sendFirstService() {
        SecondCacheBean nextCacheBean = new SecondCacheBean();
        nextCacheBean.token = System.currentTimeMillis() + "";
        SendResultModel resultModel = FirstSender.getInstance().sendFirstBusiness(cacheBean);
        ServiceConfig.ExchangeServiceModel exchangeServiceModel = new ServiceConfig.ExchangeServiceModel(resultModel);

        PageJumpConfig pageJumpConfig = new PageJumpConfig(nextCacheBean);
        pageJumpConfig.setActivityClass(SecondActivity.class);
        PageJumpController.goNextPage(pageJumpConfig, null, this, exchangeServiceModel);
    }

    public void showMsg(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }
}
