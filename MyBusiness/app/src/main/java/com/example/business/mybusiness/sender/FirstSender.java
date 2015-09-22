package com.example.business.mybusiness.sender;

import com.example.business.mybusiness.communication.Sender;
import com.example.business.mybusiness.communication.interfaces.SenderCallBack;
import com.example.business.mybusiness.communication.model.RequestEntity;
import com.example.business.mybusiness.communication.model.ResponseEntity;
import com.example.business.mybusiness.communication.model.SendResultModel;
import com.example.business.mybusiness.communication.thread.ThreadTask;
import com.example.business.mybusiness.model.FirstRequstModel;
import com.example.business.mybusiness.model.FirstResponseModel;
import com.example.business.mybusiness.page.cachebean.FirstCacheBean;

/**
 * Created by zhang.la on 2015/9/18.
 */
public class FirstSender extends Sender {
    private static FirstSender mInstance = new FirstSender();

    private FirstSender() {
    }

    public static FirstSender getInstance() {
        return mInstance;
    }

    public SendResultModel sendFirstBusiness(final FirstCacheBean firstCacheBean) {
        SendResultModel resultModel = createResultModel("sendFirstBusiness");
        final FirstRequstModel firstRequstModel = new FirstRequstModel();
        RequestEntity requestEntity = RequestEntity.getInstance();
        requestEntity.setRequestBean(firstRequstModel);
        SenderCallBack senderCallBack = new SenderCallBack() {
            @Override
            public boolean senderSuccess(ThreadTask task, int index) {
                ResponseEntity[] responseEntities = task.getResponseEntities();
                FirstResponseModel firstResponseModels = (FirstResponseModel) responseEntities[index].getReponseBean();
                if (null != firstRequstModel) {

                }
                return true;
            }

            @Override
            public boolean senderFail(ThreadTask task, int index) {
                return false;
            }
        };
        super.sender(resultModel.getToken(), senderCallBack, requestEntity);
        return resultModel;
    }

}
