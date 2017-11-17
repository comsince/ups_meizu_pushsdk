
此文章将会根据不同厂商的SDK的接入方式逐步分析，进而梳理出最为精简的接入方式，同时说明各个接入配置的细节问题

## 一 接入准备工作

关于组件的基本配置将会全部打包到aar中的AndroidManifest中，用户只需要手动配置一些与报名相关的权限配置

### 1.2 小米


#### 混淆配置

基于aar整体打包的方式

#### AppID和APPKey本地配置

```
  <meta-data
     android:name="XM_APP_ID"
     android:value="${XM_APP_ID}"/>

  <meta-data
     android:name="XM_APP_KEY"
     android:value="${XM_APP_KEY}"/>
```

#### Android Manifest 配置

```
    <!-- 小米个性化配置 需要到应用的主manifest中去配置-->
    <!-- the following 2 ${your packageName} should be changed to your package name -->
    <permission
    android:name="${your packageName}.permission.MIPUSH_RECEIVE"
    android:protectionLevel="signature" />
    <uses-permission android:name="${your packageName}.permission.MIPUSH_RECEIVE" />
```

#### UpsPushReceiver 配置

### 1.2 魅族

#### AppID和APPKey本地配置

```
  <meta-data
       android:name="MZ_APP_ID"
       android:value="${MZ_APP_ID}"/>

  <meta-data
      android:name="MZ_APP_KEY"
      android:value="${MZ_APP_KEY}"/>
```

#### Android Manifest 配置

```
    <!-- 魅族个性化配置 需要到应用的主manifest中去配置-->
    <uses-permission android:name="com.meizu.flyme.push.permission.RECEIVE"></uses-permission>
    <permission android:name="${PACKAGE_NAME}.push.permission.MESSAGE" android:protectionLevel="signature"/>
    <uses-permission android:name="${PACKAGE_NAME}.push.permission.MESSAGE"></uses-permission>
    
    <uses-permission android:name="com.meizu.c2dm.permission.RECEIVE" />
    <permission android:name="${PACKAGE_NAME}.permission.C2D_MESSAGE"
                android:protectionLevel="signature"></permission>
    <uses-permission android:name="${PACKAGE_NAME}.permission.C2D_MESSAGE"/>
```

#### UpsPushReceiver 配置

### 1.3 华为

#### AppID和APPKey本地配置

```
<!-- APPID 华为移动服务配置 请将value替换成实际的appid -->
   <meta-data
        android:name="com.huawei.hms.client.appid"
        android:value="此处为华为开发者联盟为开发者应用分配的appid" />
```

#### Android Manifest 配置

```
    <!-- 华为个性化配置 需要到应用的主manifest中去配置-->
    <!-- the following 2 ${your packageName} should be changed to your package name -->
    <permission
    android:name="${your packageName}.permission.MIPUSH_RECEIVE"
    android:protectionLevel="signature" />
    <uses-permission android:name="${your packageName}.permission.MIPUSH_RECEIVE" />
    
    
    <!-- 华为移动服务配置,将xxx替换为实际包名 -->
    <provider
       android:name="com.huawei.hms.update.provider.UpdateProvider"
       android:authorities="com.meizu.upspushdemo.hms.update.provider"
       android:exported="false"
       android:grantUriPermissions="true" >
    </provider>
```

#### UpsPushReceiver 配置


## 二 统一推送配置

### AndroidManifest 配置

```
        <!-- 统一推送配置 -->
        <receiver android:name=".UpsReceiver">
            <intent-filter>
            <!-- 接收push消息 -->
            <action android:name="com.meizu.ups.push.intent.MESSAGE" />
            </intent-filter>
        </receiver>
```


## 三 消息自定义行为分析
现在各个厂商目前支持以下三种类型
### 3.1  打开应用
  各个厂商使用方法一致
### 3.2 打开应用内页面

* 魅族
 打开页面只需要填写应用页面的全路径名称，例如```com.meizu.upspushdemo.TestActivty```
* 小米
  打开页面需要获取Intent uri，具体获取方法如下
```
 Intent intent = new Intent(this,TestActivity.class);
 intent.putExtra("key","value");
 UpsLogger.i(this,"intent uri "+intent.toUri(Intent.URI_INTENT_SCHEME));
```

  intent uri 例子如下：
```
 intent:#Intent;component=com.meizu.upspushdemo/.TestActivity;S.key=value;end
```  

* 华为

华为拼接的intent uri有点不同，主要是`component`缩写为`compo`，格式如下：

```
intent:#Intent;compo=com.meizu.upspushdemo/.TestActivity;S.key=传递给应用;end
```
  
### 3.3 打开web页面

魅族小米基本一致
* 华为
华为需要intent toUri转换，获取方法：

```
 Intent hwIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.baidu.com"));
 hwIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
 UpsLogger.i(this,"hw uri "+hwIntent.toUri(Intent.URI_INTENT_SCHEME));
```

规则如下：

```
intent://www.baidu.com#Intent;scheme=http;launchFlags=0x10000000;end
```


### 3.4 应用客户端自定义

目前仅仅华为与小米支持

* 小米
在推送时只要不指定notify_effect，即是代表自定义动作
服务端推送代码如下

```
    /**
     * 创建自定义消息内容的格式
     * **/
    private static Message buildCustomMessage() throws Exception {
        //自定义消息体
        String messagePayload = "This is a message";
        String title = "notification title";
        String description = "notification description";
        Message message = new Message.Builder()
                .title(title)
                .description(description)
                .payload(messagePayload)
                .restrictedPackageName(MY_PACKAGE_NAME)
                .passThrough(0)  //消息使用通知栏方式
                .notifyType(1)
                .build();
        return message;
    }
```