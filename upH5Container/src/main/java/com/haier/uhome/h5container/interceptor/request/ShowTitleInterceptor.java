package com.haier.uhome.h5container.interceptor.request;


import static com.haier.uhome.h5container.utils.PetrelLog.logger;

import android.app.Activity;
import android.content.Context;

import com.haier.uhome.h5container.Constant;
import com.haier.uhome.h5container.R;
import com.haier.uhome.h5container.interceptor.Interceptor;
import com.haier.uhome.h5container.interceptor.InterceptorCallback;
import com.haier.uhome.h5container.interceptor.MainLooper;
import com.haier.uhome.h5container.message.Message;
import com.haier.uhome.h5container.message.RequestMessage;
import com.haier.uhome.h5container.view.CustomizeTitleView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @description:
 * @author: pangrui
 * @email: pangrui@haier.com
 * @date: 2023/1/10 16:00
 */
public class ShowTitleInterceptor implements Interceptor {
    private final String intercepterName = "setShowTitle";
    private Context context;

    @Override
    public void init(Context context) {
        this.context = context;
        logger().info(Constant.TAG, "ShowTitleInterceptor init.");
    }

    @Override
    public void process(Message message, InterceptorCallback callback) {
        if (message instanceof RequestMessage) {
            String requestName = ((RequestMessage) message).getRequestName();
            if (intercepterName.equals(requestName)) {
                String data = message.getData();
                try {
                    boolean show = new JSONObject(data).optJSONObject("param").optBoolean("show");
                    String title = new JSONObject(data).optJSONObject("param").optString("title");
                    CustomizeTitleView titleView = ((Activity) context).findViewById(R.id.title_bar);
                    MainLooper.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (show) {
                                titleView.setTitleBarVisible(show);
                                titleView.setTitleText(title);
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("retCode", "000000");
                    jsonObject.put("retInfo", "操作成功");
                    jsonObject.put("retData", "");
                    message.setData(jsonObject.toString());
                } catch (JSONException e) {
                    logger().warn(e.getMessage(), e);
                }

                callback.onInterrupt(message, null);

            } else {
                callback.onContinue(message);
            }
        } else {
            callback.onContinue(message);
        }
    }
}
