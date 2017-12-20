本文档旨在说明如果快速的接入集成推送SDK,快速实现小米,华为,魅族的推送接入，有关详细的设计文档参考`集成推送设计说明文档`

## 准备工作

编译本项目,配置完android sdk后执行以下命令，或者你可以将本工程导入到android studio中
```
 ./gradlew clean assemble
```

## 一 AndroidManifest配置

由于三方SDK的权限,组件全部配置在`ups-push-sdk`的aar的`AndroidManifest`中,因此开发者只需关注各个平台与应用相关的配置即可,更加详细的配置参考`Ups_PushDemo`

### 1.1 权限声明

**NOTE:** 请将`${PACKAGE_NAME}`换成实际应用的包名

```
    <!-- 小米个性化配置 需要到应用的主manifest中去配置-->
    <!-- the following 2 ${PACKAGE_NAME} should be changed to your package name -->
    <permission android:name="${PACKAGE_NAME}.permission.MIPUSH_RECEIVE"
                android:protectionLevel="signature" />
    <uses-permission android:name="${PACKAGE_NAME}.permission.MIPUSH_RECEIVE" />


    <!-- 魅族个性化配置 需要到应用的主manifest中去配置-->
    <!-- the following 4 ${PACKAGE_NAME} should be changed to your package name -->
    <uses-permission android:name="com.meizu.flyme.push.permission.RECEIVE"></uses-permission>
    <permission android:name="${PACKAGE_NAME}.push.permission.MESSAGE" android:protectionLevel="signature"/>
    <uses-permission android:name="${PACKAGE_NAME}.push.permission.MESSAGE"></uses-permission>

    <uses-permission android:name="com.meizu.c2dm.permission.RECEIVE" />
    <permission android:name="${PACKAGE_NAME}.permission.C2D_MESSAGE"
                android:protectionLevel="signature"></permission>
    <uses-permission android:name="${PACKAGE_NAME}.permission.C2D_MESSAGE"/>
```


### 1.2 基础组件

```
   <!-- 华为移动服务配置,将${PACKAGE_NAME}替换为实际包名 -->
   <provider
      android:name="com.huawei.hms.update.provider.UpdateProvider"
      android:authorities="${PACKAGE_NAME}.hms.update.provider"
      android:exported="false"
      android:grantUriPermissions="true" >
   </provider>

   <!-- 统一推送配置 -->
   <receiver android:name="com.meizu.upspushdemo.UpsReceiver">
     <intent-filter>
       <!-- 接收push消息 -->
       <action android:name="com.meizu.ups.push.intent.MESSAGE" />
     </intent-filter>
   </receiver>

```

### 1.3 应用配置信息

**NOTE:** 华为需要在AndroidManifest自行配置

```
<!-- APPID 华为移动服务配置 请将value替换成实际的appid -->
   <meta-data
      android:name="com.huawei.hms.client.appid"
      android:value="${HUAWEI_APP_ID}" />
```

### 1.4  库引入说明

魅族，华为的包默认依赖相关的artifactory库，需要在你的工程根目录加入如下maven url配置
```
       //魅族的pushsdk存放在jcenter中
       jcenter()
       //华为的库存放其私有仓库中
       maven {
            url 'http://developer.huawei.com/repo/'
        }
```
小米的库需要手动将其jar放到工程的lib目录下，小米的pushsdk jar[下载](https://dev.mi.com/mipush/downpage/)

### 1.5 UpsPushSDK库引入

```
compile ('com.meizu.flyme.internet:push-ups:1.0.4@aar'){
       //当指定aar类型时注意要设置transitive设置为true，不然依赖关系无法传递
        transitive=true
}
```


## 二 统一推送平台配置

### 2.1 应用信息平台设置

推荐到[魅族统一推送平台](http://mzups.meizu.com)应用配置-> 添加多渠道添加各个平台的`AppId`,`AppKey`,`AppSecret`,如下图所示:

![image](attach/app-setting.png)



### 2.2 应用信息本地设置测试

应用也可以在AndroidManifest配置`AppID`,`AppKey`信息方便本地测试，`ups-pushsdk`会优先读取本地`AndroidManifest`中的配置信息

**NOTE:** 小米的`APP_ID`,`APP_KEY`需要进行字符转义,不然无法正确读出,例如`android:value="\02882303761517631454"`

```     
        <meta-data
            android:name="XIAOMI_APP_ID"
            android:value="${XIAOMI_APP_ID}"/>

        <meta-data
            android:name="XIAOMI_APP_KEY"
            android:value="${XIAOMI_APP_KEY}"/>


        <meta-data
            android:name="MEIZU_APP_ID"
            android:value="${MEIZU_APP_ID}"/>

        <meta-data
            android:name="MEIZU_APP_KEY"
            android:value="${MEIZU_APP_KEY}"/>

```


## 三 通知栏自定义行为使用方式

统一推送平台目前由五种方式，[详情参见](UpsIntegrateReadme.md)


## 问题说明

* 华为无法订阅成功
  * 升级华为移动服务至最新版本，到华为应用商店更新即可
* Ups平台报签名错误
  * 无法获取手机唯一识别码,需要到各个平台的手机管家中,打开获取手机信息的权限

* 通知栏问题  
  * OPPO ColorOS 需要打开通知栏权限才可展示通知栏
  * Android O 平台没有设置channelId,无法展示通知栏问题
 
* 判定厂商
  * OPPO MANUFACTURER:OPPO model:R7Plusm
  * VIVO MANUFACTURER:vivo model:vivo X6D
  * HUAWEI MANUFACTURER:HUAWEI model:MHA-AL00 brand:MHA
  * MEIZU MANUFACTURER:MEIZU
 
   