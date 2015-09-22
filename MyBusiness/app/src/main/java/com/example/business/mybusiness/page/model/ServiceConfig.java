package com.example.business.mybusiness.page.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.business.mybusiness.communication.interfaces.BusinessCallBack;
import com.example.business.mybusiness.communication.model.SendResultModel;

/**
 * 主动获取服务response
 * Created by zhang.la on 2015/9/17.
 */
public class ServiceConfig {
    private BusinessCallBack businessCallBack;

    private ExchangeServiceModel exchangeServiceModel;

    public ServiceConfig(ExchangeServiceModel exchangeServiceModel) {
        this.exchangeServiceModel = exchangeServiceModel;
    }

    public SendResultModel getResultModel() {
        return exchangeServiceModel.getResultModel();
    }

    public BusinessCallBack getBusinessCallBack() {
        return businessCallBack;
    }

    public ServiceConfig setBusinessCallBack(BusinessCallBack businessCallBack) {
        this.businessCallBack = businessCallBack;
        return this;
    }

    /**
     * 服务model
     */
    public static class ExchangeServiceModel implements Parcelable {
        protected SendResultModel resultModel;

        public ExchangeServiceModel(SendResultModel resultModel) {
            this.resultModel = resultModel;
        }

        protected ExchangeServiceModel(Parcel in) {
            resultModel = (SendResultModel) in.readSerializable();
        }

        public static final Creator<ExchangeServiceModel> CREATOR = new Creator<ExchangeServiceModel>() {
            @Override
            public ExchangeServiceModel createFromParcel(Parcel in) {
                return new ExchangeServiceModel(in);
            }

            @Override
            public ExchangeServiceModel[] newArray(int size) {
                return new ExchangeServiceModel[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeSerializable(resultModel);
        }

        public SendResultModel getResultModel() {
            return resultModel;
        }

        public void setResultModel(SendResultModel resultModel) {
            this.resultModel = resultModel;
        }
    }
}
