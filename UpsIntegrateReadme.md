# 接入说明

## 接入准备工作

关于组件的基本配置将会全部打包到aar中的AndroidManifest中，用户只需要手动配置一些与报名相关的权限配置

### 小米

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

### 魅族

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

### 华为

#### AppID和APPKey本地配置

无此配置项目，即是不需要配置

#### Android Manifest 配置

```
    <!-- 华为个性化配置 需要到应用的主manifest中去配置-->
    <!-- the following 2 ${your packageName} should be changed to your package name -->
    <permission
    android:name="${your packageName}.permission.MIPUSH_RECEIVE"
    android:protectionLevel="signature" />
    <uses-permission android:name="${your packageName}.permission.MIPUSH_RECEIVE" />
```

#### UpsPushReceiver 配置