package com.haier.uhome.h5container.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.  text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.haier.uhome.h5container.Constant;
import com.haier.uhome.h5container.ContainerPluginManager;
import com.haier.uhome.h5container.browse.BridgeWebView;
import com.haier.uhome.h5container.browse.AbsWebViewClient;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: pangrui
 * @email: pangrui@haier.com
 * @date: 2022/12/26 16:53
 */
public abstract class BaseContainerActivity extends Activity implements PageContainer {
    private boolean isInitTbs;
    private static final String TAG = "BaseContainerActivity";
    BridgeWebView bridgeWebView;
    ContainerPluginManager containerManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bridgeWebView = supportedWebView();
        initX5();
        init();
        registerPlugins();
    }

    private void registerPlugins() {
        containerManager = bridgeWebView.getContainerManager();
    }

    private void init() {
        bridgeWebView.setWebViewClient(new AbsWebViewClient() {
            @Override
            public String onPageError(String url) {
                String errorPage = pageError();
                return !TextUtils.isEmpty(errorPage) ? errorPage : Constant.DEFAULT_PAGE_ERROR;
            }

            @Override
            public Map<String, String> onPageHeaders(String url) {
                return pageHeaders();
            }
        });
        bridgeWebView.loadUrl(pageUrl());
    }

    public ContainerPluginManager getContainerManager() {
        return bridgeWebView.getContainerManager();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bridgeWebView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        bridgeWebView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bridgeWebView.onDestory();
    }


    private void  initX5(){
        isInitTbs = QbSdk.canLoadX5(getApplicationContext());
        if (!isInitTbs || QbSdk.getTbsVersion(getApplicationContext()) < 46007) {
            copyAssets(getApplicationContext(), "046007_x5.tbs.apk", getTBSFileDir(getApplicationContext()).getPath() + "/046007_x5.tbs.apk");

        }
        HashMap<String, Object> map = new HashMap<>(2);
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE, true);
        QbSdk.initTbsSettings(map);

        boolean canLoadX5 = QbSdk.canLoadX5(getApplicationContext());
        Log.e(TAG, "canLoadX5: " + canLoadX5+"|TbsVersion:"+QbSdk.getTbsVersion(getApplicationContext()));
        if (canLoadX5) {
            return;
        }
        QbSdk.reset(getApplicationContext());
        QbSdk.setTbsListener(new TbsListener() {
            @Override
            public void onDownloadFinish(int i) {

            }

            @Override
            public void onInstallFinish(int i) {
                Log.e(TAG, "onInstallFinish: " + i);
                int tbsVersion = QbSdk.getTbsVersion(getApplicationContext());
                Log.e(TAG, "tbsVersion: " + tbsVersion);
            }

            @Override
            public void onDownloadProgress(int i) {

            }
        });
        QbSdk.installLocalTbsCore(getApplicationContext(), 46007, getTBSFileDir(getApplicationContext()).getPath() + "/046007_x5.tbs.apk");

    }

    public File getTBSFileDir(Context context) {
        String dirName = "TBSFile";
        return context.getExternalFilesDir(dirName);
    }

    public  boolean copyAssets(Context context, String oldPath, String newPath) {
        try {
            String fileNames[] = context.getAssets().list(oldPath);// 获取assets目录下的所有文件及目录名
            if (fileNames.length > 0) {// 如果是目录
                File file = new File(newPath);
                file.mkdirs();// 如果文件夹不存在，则递归
                for (String fileName : fileNames) {
                    copyAssets(context, oldPath + "/" + fileName, newPath + "/" + fileName);
                }
            } else {// 如果是文件
                InputStream is = context.getAssets().open(oldPath);
                FileOutputStream fos = new FileOutputStream(new File(newPath));
                byte[] buffer = new byte[1024];
                int byteCount;
                while ((byteCount = is.read(buffer)) != -1) {// 循环从输入流读取
                    // buffer字节
                    fos.write(buffer, 0, byteCount);// 将读取的输入流写入到输出流
                }
                fos.flush();// 刷新缓冲区
                is.close();
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

