/*
 * MIT License
 *
 * Copyright (c) [2017] [Meizu.inc]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.meizu.upspushsdklib.handler.impl;

import android.content.Context;
import android.text.TextUtils;

import com.meizu.cloud.pushsdk.platform.message.BasicPushStatus;
import com.meizu.upspushsdklib.Company;
import com.meizu.upspushsdklib.handler.HandlerContext;
import com.meizu.upspushsdklib.network.Response;
import com.meizu.upspushsdklib.receiver.dispatcher.UpsPushAPI;
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

        if(UpsUtils.isMeizu(context)){
            setAppInfo(ctx,appId,appKey,Company.MEIZU);
        } else if(UpsUtils.isXiaoMi()){
            setAppInfo(ctx,appId,appKey,Company.XIAOMI);
        } else if(UpsUtils.isHuaWei()){
            UpsLogger.e(this,"current device model is huawei");
            ctx.fireRegister(null,null);
        } else {
            UpsLogger.e(this,"other mode "+UpsUtils.deviceModel());
        }
    }

    private void setAppInfo(HandlerContext ctx,String upsAppId,String upsAppKey,Company company){
        Context context = ctx.pipeline().context();
        UpsLogger.e(this,"current device model is "+company.name());
        String cpAppId =  getAppId(context, company.name());
        String cpAppKey = getAppKey(context,company.name());
        if(TextUtils.isEmpty(cpAppId) || TextUtils.isEmpty(cpAppKey)){
            //本地获取配置信息
            if(Company.MEIZU == company){
                cpAppId = UpsUtils.getMetaIntValueByName(context, company.name()+"_APP_ID");
            } else {
                cpAppId = UpsUtils.getMetaStringValueByName(context, company.name()+"_APP_ID");
            }
            cpAppKey = UpsUtils.getMetaStringValueByName(context,company.name()+"_APP_KEY");
            if(!TextUtils.isEmpty(cpAppId) && !TextUtils.isEmpty(cpAppKey)){
                UpsLogger.e(this,"store cpAppId "+cpAppId+" cpAppKey "+cpAppKey+" from manifest");
                putAppId(context,company.name(),cpAppId);
                putAppKey(context,company.name(),cpAppKey);
            }
        }

        if(TextUtils.isEmpty(cpAppId) || TextUtils.isEmpty(cpAppKey)){
            //从统一push平台获取
            //ANResponse<String> anResponse = UpsPushAPI.getCpInfo(upsAppId,upsAppKey, Company.MEIZU.code(),context.getPackageName());
            Response<String> response = UpsPushAPI.getCpInfo0(upsAppId,upsAppKey, Company.MEIZU.code(),context.getPackageName());
            if(response.isSuccess()){
                CompanyInfo companyInfo = new CompanyInfo(response.getBody());
                UpsLogger.i(this,"cp companyInfo "+companyInfo);
                cpAppId = companyInfo.getCpAppId();
                cpAppKey = companyInfo.getCpAppKey();
                putAppId(context,company.name(),cpAppId);
                putAppKey(context,company.name(),cpAppKey);
            } else {
                UpsLogger.e(this,"get meizu company info error "+response.getErrorBody());
            }
        }
        UpsLogger.i(this,"cpAppId "+cpAppId+" cpAppKey "+cpAppKey +" fireRegister");
        ctx.fireRegister(cpAppId,cpAppKey);
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
    public boolean isCurrentModel(HandlerContext ctx) {
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
