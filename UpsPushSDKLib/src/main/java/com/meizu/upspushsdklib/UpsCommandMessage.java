package com.meizu.upspushsdklib;

import java.io.Serializable;


public class UpsCommandMessage implements Serializable{
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
