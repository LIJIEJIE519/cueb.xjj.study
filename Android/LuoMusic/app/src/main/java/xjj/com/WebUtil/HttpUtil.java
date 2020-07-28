package xjj.com.WebUtil;

import android.widget.ImageView;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by XJJ on 2018/12/9.
 * 使用OkHttp3进行网络请求
 */

public class HttpUtil {
    // 调用该方法进行网络请求
    public static void sendOkHttpRequest(final String address, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
