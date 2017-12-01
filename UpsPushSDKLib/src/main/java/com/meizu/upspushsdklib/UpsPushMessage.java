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


import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class UpsPushMessage implements Parcelable{
    /**
     * 消息的标题，如果时通知栏消息，则为通知栏标题
     * */
    private String title;
    /**
     * 消息内容,如果时通知栏则为消息通知栏内容，如果为透传消息，则为透传消息体
     * */
    private String content;

    /**
     * 通知栏消息的通知id
     * */
    private int notifyId;
    /**
     * 消息类型,0代表通知栏消息，1代表透传消息
     * */
    private PushType pushType;
    /**
     * 厂商类型包括：Company.MEIZU,Company.HUAWEI,Company.XIAOMI
     * */
    private Company company;

    /**
     * 自定义字符串,在小米上即是填写的传输数据
     * 魅族代表在选择应用自定义类型时，填写的自定义数据
     * */
    private String selfDefineString;
    /**
     * 代表各个平台的传递的对象，魅族代表selfDefineContentString，小米代表MiPushMessage，华为代表bundle,需要通过判断company进行对象类型转化
     * */
    private Object extra;

    private UpsPushMessage(Builder builder){
        this.title = builder.title;
        this.content = builder.content;
        this.pushType = builder.pushType;
        this.company = builder.company;
        this.extra = builder.extra;
        this.notifyId = builder.notifyId;
        this.selfDefineString = builder.selfDefineString;
    }

    protected UpsPushMessage(Parcel in) {
        title = in.readString();
        content = in.readString();
        notifyId = in.readInt();
        pushType = PushType.valueOf(in.readString());
        company = Company.valueOf(in.readString());
        selfDefineString = in.readString();
        if(company == Company.HUAWEI){
            extra = in.readParcelable(Bundle.class.getClassLoader());
        } else {
            extra = in.readSerializable();
        }
    }

    public static final Creator<UpsPushMessage> CREATOR = new Creator<UpsPushMessage>() {
        @Override
        public UpsPushMessage createFromParcel(Parcel in) {
            return new UpsPushMessage(in);
        }

        @Override
        public UpsPushMessage[] newArray(int size) {
            return new UpsPushMessage[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getNotifyId() {
        return notifyId;
    }

    public void setNotifyId(int notifyId) {
        this.notifyId = notifyId;
    }

    public PushType getPushType() {
        return pushType;
    }

    public void setPushType(PushType pushType) {
        this.pushType = pushType;
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

    public String getSelfDefineString() {
        return selfDefineString;
    }

    public void setSelfDefineString(String selfDefineString) {
        this.selfDefineString = selfDefineString;
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
        dest.writeString(title);
        dest.writeString(content);
        dest.writeInt(notifyId);
        dest.writeString(pushType.name());
        dest.writeString(company.name());
        dest.writeString(selfDefineString);
        if(company == Company.HUAWEI){
            dest.writeParcelable((Parcelable) extra,flags);
        } else {
            dest.writeSerializable((Serializable) extra);
        }
    }

    public static class Builder {
        /**
         * 消息的标题，如果时通知栏消息，则为通知栏标题
         * */
        private String title;
        /**
         * 消息内容,如果时通知栏则为消息通知栏内容，如果为透传消息，则为透传消息体
         * */
        private String content;

        /**
         * 通知栏消息的通知id
         * */
        private int notifyId;
        /**
         * 消息类型,0代表通知栏消息，1代表透传消息
         * */
        private PushType pushType;
        /**
         * 厂商类型包括：Company.MEIZU,Company.HUAWEI,Company.XIAOMI
         * */
        private Company company;
        /**
         * 自定义字符串,在小米上即是填写的传输数据
         * 魅族代表在选择应用自定义类型时，填写的自定义数据
         * */
        private String selfDefineString;
        /**
         * 代表各个平台的传递的对象，魅族代表selfDefineContentString，小米代表MiPushMessage，华为代表bundle,需要通过判断company进行对象类型转化
         * */
        private Object extra;

        public Builder title(String title){
            this.title = title;
            return this;
        }

        public Builder content(String content){
            this.content = content;
            return this;
        }

        public Builder noifyId(int notifyId){
            this.notifyId = notifyId;
            return this;
        }

        public Builder pushType(PushType pushType){
            this.pushType = pushType;
            return this;
        }

        public Builder company(Company company){
            this.company = company;
            return this;
        }

        public Builder selfDefineString(String selfDefineString){
            this.selfDefineString = selfDefineString;
            return this;
        }

        public Builder extra(Object extra){
            this.extra = extra;
            return this;
        }

        public UpsPushMessage build(){
           return new UpsPushMessage(this);
        }
    }

    @Override
    public String toString() {
        return "UpsPushMessage{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", notifyId=" + notifyId +
                ", pushType=" + pushType +
                ", company=" + company +
                ", selfDefineString='" + selfDefineString + '\'' +
                ", extra=" + extra +
                '}';
    }
}
