package com.meizu.upspushsdklib.handler.impl;

import android.content.Context;
import android.text.TextUtils;

import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.networking.common.ANResponse;
import com.meizu.cloud.pushsdk.platform.message.BasicPushStatus;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;
import com.meizu.upspushsdklib.Company;
import com.meizu.upspushsdklib.handler.HandlerContext;
import com.meizu.upspushsdklib.receiver.dispatcher.UpsPushAPI;
import com.meizu.upspushsdklib.util.UpsConstants;
import com.meizu.upspushsdklib.util.UpsLogger;
import com.meizu.upspushsdklib.util.UpsUtils;

import org.json.JSONException;
import org.json.JSONObject;


public class AppSettingHandler extends AbstractHandler{
    @Override
    public void register(HandlerContext ctx, String appId, String appKey) {
        Context context = ctx.pipeline().context();

        //存储统一推送平台的appId,appKey
        if(TextUtils.isEmpty(getAppId(context,Company.DEFAULT.name())) || TextUtils.isEmpty(getAppKey(context,Company.DEFAULT.name()))){
            putAppId(context,Company.DEFAULT.name(),appId);
            putAppKey(context,Company.DEFAULT.name(),appKey);
        }

        if(UpsUtils.isMeizu()){
            UpsLogger.e(this,"current device model is MEIZU");
            String mzAppId =  getAppId(context, Company.MEIZU.name());
            String mzAppKey = getAppKey(context,Company.MEIZU.name());
            if(TextUtils.isEmpty(mzAppId) || TextUtils.isEmpty(mzAppKey)){
                //本地获取配置信息
                mzAppId = UpsUtils.getMetaIntValueByName(context, UpsConstants.MZ_APP_ID);
                mzAppKey = UpsUtils.getMetaStringValueByName(context,UpsConstants.MZ_APP_KEY);
                UpsLogger.e(this,"store mzAppId "+mzAppId+" mzAppKey "+mzAppKey+" from manifest");
                putAppId(context,Company.MEIZU.name(),mzAppId);
                putAppKey(context,Company.MEIZU.name(),mzAppKey);
            }

            if(TextUtils.isEmpty(mzAppId) || TextUtils.isEmpty(mzAppKey)){
                //从统一push平台获取
                ANResponse<String> anResponse = UpsPushAPI.getCpInfo(appId,appKey, Company.MEIZU.code(),context.getPackageName());
                if(anResponse.isSuccess()){
                    CompanyInfo companyInfo = new CompanyInfo(anResponse.getResult());
                    UpsLogger.i(this,"meizu companyInfo "+companyInfo);
                    putAppId(context,Company.MEIZU.name(),companyInfo.getCpAppId());
                    putAppKey(context,Company.MEIZU.name(),companyInfo.getCpAppKey());
                } else {
                    UpsLogger.e(this,"get meizu company info error "+anResponse.getError());
                }
            }

            ctx.fireRegister(mzAppId,mzAppKey);
        } else if(UpsUtils.isXiaoMi()){
            UpsLogger.e(this,"current device model is XIAOMI");
            String xmAppId =  getAppId(context, Company.XIAOMI.name());
            String xmAppKey = getAppKey(context,Company.XIAOMI.name());
            if(TextUtils.isEmpty(xmAppId) || TextUtils.isEmpty(xmAppKey)){
                //本地获取配置信息
                xmAppId = UpsUtils.getMetaStringValueByName(context, UpsConstants.XM_APP_ID);
                xmAppKey = UpsUtils.getMetaStringValueByName(context,UpsConstants.XM_APP_KEY);
                UpsLogger.e(this,"store xmAppId "+xmAppId+" xmAppKey "+xmAppKey+" from manifest");
                putAppId(context,Company.XIAOMI.name(),xmAppId);
                putAppKey(context,Company.XIAOMI.name(),xmAppKey);
            }

            if(TextUtils.isEmpty(xmAppId) || TextUtils.isEmpty(xmAppKey)){
                //从统一push平台获取
                ANResponse<String> anResponse = UpsPushAPI.getCpInfo(appId,appKey, Company.XIAOMI.code(),context.getPackageName());
                if(anResponse.isSuccess()){
                    CompanyInfo companyInfo = new CompanyInfo(anResponse.getResult());
                    UpsLogger.i(this,"xiaomi companyInfo "+companyInfo);
                    putAppId(context,Company.XIAOMI.name(),companyInfo.getCpAppId());
                    putAppKey(context,Company.XIAOMI.name(),companyInfo.getCpAppKey());
                } else {
                    UpsLogger.e(this,"get xiaomi company info error "+anResponse.getError());
                }
            }
            ctx.fireRegister(xmAppId,xmAppKey);
        } else if(UpsUtils.isHuaWei()){
            UpsLogger.e(this,"current device model is huawei");
            ctx.fireRegister(null,null);
        }
    }

    @Override
    public void unRegister(HandlerContext ctx) {
        ctx.fireUnRegister();
    }

    @Override
    public void setAlias(HandlerContext ctx, String alias) {
        ctx.fireSetAlias(alias);
    }

    @Override
    public void unSetAlias(HandlerContext ctx, String alias) {
        ctx.fireUnSetAlias(alias);
    }

    @Override
    public boolean isCurrentModel() {
        return true;
    }

    @Override
    public String name() {
        return Company.DEFAULT.name();
    }

    private class CompanyInfo extends BasicPushStatus{
        String cpAppId;
        String cpAppKey;

        public CompanyInfo(String json){
            super(json);
        }

        @Override
        public void parseValueData(JSONObject jsonObject) throws JSONException {
            if(!jsonObject.isNull("cpAppId")){
                 setCpAppId(jsonObject.getString("cpAppId"));
            }
            if(!jsonObject.isNull("cpAppKey")){
                setCpAppKey(jsonObject.getString("cpAppKey"));
            }
        }

        public String getCpAppId() {
            return cpAppId;
        }

        public void setCpAppId(String cpAppId) {
            this.cpAppId = cpAppId;
        }

        public String getCpAppKey() {
            return cpAppKey;
        }

        public void setCpAppKey(String cpAppKey) {
            this.cpAppKey = cpAppKey;
        }

        @Override
        public String toString() {
            return "CompanyInfo{" +
                    "cpAppId='" + cpAppId + '\'' +
                    ", cpAppKey='" + cpAppKey + '\'' +
                    '}';
        }
    }
}
