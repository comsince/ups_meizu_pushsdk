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

package com.meizu.upspushsdklib;

import android.os.Parcel;
import android.os.Parcelable;

import com.meizu.cloud.pushsdk.pushtracer.utils.Util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class UpsCommandMessage implements Parcelable{
    private int code;
    private String message = "";
    /**
     * 表示请求服务类型,目前包括四种CommandType.REGISTER,UpsManager.CommandType,CommandType.SUBALIAS,CommandType.UNSUBALIAS
     * */
    private CommandType commandType;

    /**
     * 表示执行成功后，服务端返回的结果参数，例如订阅成功后，返回的pushId
     * */
    private String commandResult;

    /**
     * 厂商标记
     * */
    private Company company;

    /**
     * 代表各个平台传递对象，魅族为空，小米为MiPushCommandMessage，华为为Bundle
     * */
    private Object extra;

    private UpsCommandMessage(Builder builder){
        this.code = builder.code;
        this.message = builder.message;
        this.commandType = builder.commandType;
        this.commandResult = builder.commandResult;
        this.company = builder.company;
        this.extra = builder.extra;
    }

    public UpsCommandMessage(){

    }

    protected UpsCommandMessage(Parcel in) {
        code = in.readInt();
        message = in.readString();
        commandResult = in.readString();
        company = Company.valueOf(in.readString());
        commandType = CommandType.valueOf(in.readString());
        if(company == Company.HUAWEI){
            extra = in.readParcelable(Object.class.getClassLoader());
        } else {
            extra = in.readSerializable();
        }
    }

    public static final Creator<UpsCommandMessage> CREATOR = new Creator<UpsCommandMessage>() {
        @Override
        public UpsCommandMessage createFromParcel(Parcel in) {
            return new UpsCommandMessage(in);
        }

        @Override
        public UpsCommandMessage[] newArray(int size) {
            return new UpsCommandMessage[size];
        }
    };

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }

    public String getCommandResult() {
        return commandResult;
    }

    public void setCommandResult(String commandResult) {
        this.commandResult = commandResult;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Object getExtra() {
        return extra;
    }

    public void setExtra(Object extra) {
        this.extra = extra;
    }

    public static Builder builder(){
        return new Builder();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(message);
        dest.writeString(commandResult);
        dest.writeString(company.name());
        dest.writeString(commandType.name());

        if(company == Company.HUAWEI){
            dest.writeParcelable((Parcelable) extra,flags);
        } else {
            dest.writeSerializable((Serializable) extra);
        }
    }

    public static class Builder{
        private int code;
        private String message;
        /**
         * 表示请求服务类型,目前包括四种CommandType.REGISTER,UpsManager.CommandType,CommandType.SUBALIAS,CommandType.UNSUBALIAS
         * */
        private CommandType commandType;

        /**
         * 表示执行成功后，服务端返回的结果参数，例如订阅成功后，返回的pushId
         * */
        private String commandResult;

        /**
         * 厂商标记
         * */
        private Company company;

        /**
         * 代表各个平台传递对象，魅族为空，小米为MiPushCommandMessage，华为为Bundle
         * */
        private Object extra;

        public Builder code(int code){
            this.code = code;
            return this;
        }

        public Builder message(String message){
            this.message = message;
            return this;
        }

        public Builder commandType(CommandType commandType){
            this.commandType = commandType;
            return this;
        }

        public Builder commandResult(String commandResult){
            this.commandResult = commandResult;
            return this;
        }

        public Builder company(Company company){
            this.company = company;
            return this;
        }

        public Builder extra(Object extra){
            this.extra = extra;
            return this;
        }

        public UpsCommandMessage build(){
            return  new UpsCommandMessage(this);
        }

    }

    @Override
    public String toString() {
        return "UpsCommandMessage{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", commandType=" + commandType +
                ", commandResult='" + commandResult + '\'' +
                ", company=" + company +
                ", extra=" + extra +
                '}';
    }
}
