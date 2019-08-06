package com.tang.test;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 描述:
 * 作者 : Tong
 * e-mail : itangbei@sina.com
 * 创建时间: 2019/8/5.
 */
public class J2V8ReadJavaScript extends AsyncTask<String, String, String>
{
    private AsyncTaskBackListener<String> listener;

    private String fileName;
    private Activity activity;
    public J2V8ReadJavaScript(Activity activity, AsyncTaskBackListener<String> listener, String fileName)
    {
        this.listener = listener;
        this.fileName = fileName;
        this.activity = activity;

    }
    public J2V8ReadJavaScript()
    {
        super();
    }
    public static String getFileContent(String fileName, Context context)
    {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try
        {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null)
            {
                stringBuilder.append(line).append("\r\n");//为了保证js的严格“行”属性，这里主动追加\r\n
            }
            bf.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }
    @Override
    protected String doInBackground(String... strparams)
    {

        String jsString = getFileContent(this.fileName, this.activity);

        return jsString;
    }
    @Override
    protected void onPostExecute(String result)
    {
        if (listener != null)
        {
            listener.onAsyncTaskCallBack(result);
        }
    }
}