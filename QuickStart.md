本文档旨在说明如果快速的接入集成推送SDK,快速实现小米,华为,魅族的推送接入，有关详细的设计文档参考`集成推送设计说明文档`

## AndroidManifest配置

由于三方SDK的权限,组件全部配置在`ups-push-sdk`的aar的`AndroidManifest`中,因此开发者只需关注各个平台与应用相关的配置即可,更加详细的配置参考`Ups_PushDemo`

### 权限声明

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


### 基础组件

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

### 应用配置信息

**NOTE:** 华为需要在AndroidManifest自行配置

```
<!-- APPID 华为移动服务配置 请将value替换成实际的appid -->
   <meta-data
      android:name="com.huawei.hms.client.appid"
      android:value="${HUAWEI_APP_ID}" />
```



### 统一推送平台配置

* 应用信息平台设置

推荐到[魅族统一推送平台](mzups.meizu.com)应用配置-> 添加多渠道添加各个平台的`AppId`,`AppKey`,`AppSecret`,如下图所示:

![image](attach/app-setting.png)



* 应用信息本地设置测试

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


### 通知栏自定义行为使用方式

统一推送平台目前由五种方式，[详情参见](UpsIntegrateReadme.md)