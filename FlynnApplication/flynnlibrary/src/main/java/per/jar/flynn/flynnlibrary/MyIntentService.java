package per.jar.flynn.flynnlibrary;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by flynn on 2017/11/14.
 */

public class MyIntentService extends IntentService {
    private static String TAG = "MyIntentService";

    public MyIntentService(){
        super("MyIntentService");
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "MyIntentService onHandleIntent");
        String url = intent.getExtras().getString("url");
        String data = intent.getExtras().getString("data");
        String contentType = intent.getExtras().getString("contentType");
        Log.d(TAG, "url ="+url);
        Log.d(TAG, "data ="+data);
        if(contentType!=null){
            Log.d(TAG, "contentType ="+contentType);
        }else{
            Log.d(TAG, "contentType =null");
        }

        //upload方法.....
        String postResult=null;
        postResult = loginByPost(url,data,contentType);
        if(postResult==null){
            Log.d(TAG, "MyIntentService loginByPost 请求失败");
        }else{
            Log.d(TAG, "MyIntentService loginByPost 请求成功");
            Log.d(TAG, "MyIntentService postResult="+postResult);
        }
    }
    @Override
    public void onCreate() {
        Log.d(TAG, "MyIntentService onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "MyIntentService onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "MyIntentService onDestroy");
        super.onDestroy();
    }

    /**
     *post的方式请求
     *@param myurl url地址
     *@param mydata 数据
     *@return 返回null 登录异常
     * */
    public static String loginByPost(String myurl,String mydata,String mycontentType){
        String path = myurl;
        String contentType = mycontentType;
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setDoInput(true);                  //打开输入流，以便从服务器获取数据
            connection.setDoOutput(true);                 //打开输出流，以便向服务器提交数据
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);               //使用Post方式不能使用缓存
            //数据准备
            String data = mydata;
//            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");//设置请求体的类型是文本类型
            if (contentType==null){
                connection.setRequestProperty("Content-Type"," application/json");//设置请求体的类型是文本类型
            }else{
                connection.setRequestProperty("Content-Type",contentType);//设置请求体的类型是文本类型
            }
            connection.setRequestProperty("Content-Length", String.valueOf(data.getBytes().length));//设置请求体的长度
            //post的方式提交
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(data.getBytes());
            //获得结果码
            int responseCode = connection.getResponseCode();
            if(responseCode ==HttpURLConnection.HTTP_OK){
                //请求成功
                InputStream is = connection.getInputStream();
                return dealResponseResult(is);
            }else { //请求失败
                Log.d(TAG, "MyIntentService loginByPost responseCode!=HTTP_OK 请求失败ServiceJar="+((Integer)responseCode).toString());
                return null;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
   * @Function 处理服务器的响应结果（将输入流转化成字符串）
   * @param inputStream服务器的响应输入流
   */
    public static String dealResponseResult(InputStream inputStream) {
        String resultData = null;      //存储处理结果
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        try {
            while((len = inputStream.read(data)) != -1) {
                byteArrayOutputStream.write(data, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        resultData = new String(byteArrayOutputStream.toByteArray());
        return resultData;
    }
}
