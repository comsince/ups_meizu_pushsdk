package com.meizu.upspushsdklib.handler.impl;

import android.content.Context;
import android.text.TextUtils;
import com.meizu.upspushsdklib.Company;
import com.meizu.upspushsdklib.handler.HandlerContext;
import com.meizu.upspushsdklib.util.UpsConstants;
import com.meizu.upspushsdklib.util.UpsLogger;
import com.meizu.upspushsdklib.util.UpsUtils;


public class AppSettingHandler extends AbstractHandler{
    @Override
    public void register(HandlerContext ctx, String appId, String appKey) {
        Context context = ctx.pipeline().context();
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
            }

            ctx.fireRegister(mzAppId,mzAppKey);
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
        return UpsUtils.isMeizu();
    }

    @Override
    public String name() {
        return Company.DEFAULT.name();
    }
}
