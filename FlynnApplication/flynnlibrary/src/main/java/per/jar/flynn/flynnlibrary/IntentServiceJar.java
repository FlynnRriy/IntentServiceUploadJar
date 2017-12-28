package per.jar.flynn.flynnlibrary;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by flynn on 2017/11/14.
 */

public class IntentServiceJar {
    Context context;

    private IntentServiceJar(){}
    private static class IntentSerJarHolder {
        static final IntentServiceJar INSTANCE = new IntentServiceJar();
    }
    public static IntentServiceJar getInstance() {
        return IntentSerJarHolder.INSTANCE;
    }

    public void upload(Context cnt,String url,String data,String contentType){
        Log.d("IntentServiceJar", "IntentServiceJar-》upload（）");
        this.context = cnt;

        //同一服务只会开启一个worker thread，在onHandleIntent函数里依次处理intent请求。
        Intent myIntent = new Intent(context,MyIntentService.class);
        myIntent.putExtra("url",url);
        myIntent.putExtra("data",data);
        myIntent.putExtra("contentType",contentType);
        context.startService(myIntent);
    }
}
