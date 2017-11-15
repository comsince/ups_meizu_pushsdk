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
    }

    protected UpsPushMessage(Parcel in) {
        title = in.readString();
        content = in.readString();
        notifyId = in.readInt();
        pushType = PushType.valueOf(in.readString());
        company = Company.valueOf(in.readString());
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
                ", extra=" + extra +
                '}';
    }
}
