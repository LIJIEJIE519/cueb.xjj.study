package xjj.com.WebUtil;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by XJJ on 2018/12/19.
 * 加载网络图片，并赋值给ImageView
 */

public class LoadImg {
    public static void loadWebImg(final ImageView img, String url, final Activity activity) {
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 获取网络图片
                byte[] bingPic = response.body().bytes();
                // 压缩图片，防止内存泄露
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = false; // 是否加载到内存 false为是
                //options.inSampleSize = 5;       // 压缩倍数

                final Bitmap bmp = BitmapFactory.decodeByteArray(bingPic, 0, bingPic.length);
                // 将当前线程切换到主线程绘制UI
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        img.setImageBitmap(bmp);
                    }
                });
            }
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }
}
