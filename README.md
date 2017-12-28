1,如果为添加Jar包
2,import tv.wangbo.interntservicejar.IntentSerJar;
3,在mainifest中添加
	<service android:name="tv.wangbo.interntservicejar.MyIntentService"></service>
4,使用方法：
    IntentSerJar.getInstance().upload(context,String url,String data,null);
        注意data必须为Jason格式，因为Http的tent-type为aplication/json
    或者
    IntentSerJar.getInstance().upload(context,String url,String data,String contentType);

	备注:contentType为 connection.setRequestProperty("Content-Type",contentType)中的contentType;
		加入了判断当contentType为空时，默认为"application/json"
5,其他：
POST，懒汉模式，生命周期由intentServer处理，内部为HttpURLConnection方式





Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://www.jitpack.io' }
		}
	}
Copy

Step 2. Add the dependency

	dependencies {
	        compile 'com.github.FlynnRriy:IntentServiceUploadJar:2.0'
	}
