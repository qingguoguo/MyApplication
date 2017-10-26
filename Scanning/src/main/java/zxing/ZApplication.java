package zxing;

import android.app.Application;
import android.util.DisplayMetrics;


/**
 * Created by aaron on 16/8/9.
 */

public class ZApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * 初始化尺寸工具类
         */
        initDisplayOpinion();
    }

    private void initDisplayOpinion() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
       /* com.uuzuche.lib_zxing.DisplayUtil.density = dm.density;
        com.uuzuche.lib_zxing.DisplayUtil.densityDPI = dm.densityDpi;
        com.uuzuche.lib_zxing.DisplayUtil.screenWidthPx = dm.widthPixels;
        com.uuzuche.lib_zxing.DisplayUtil.screenhightPx = dm.heightPixels;
        com.uuzuche.lib_zxing.DisplayUtil.screenWidthDip = com.uuzuche.lib_zxing.DisplayUtil.px2dip(getApplicationContext(), dm.widthPixels);
        com.uuzuche.lib_zxing.DisplayUtil.screenHightDip = com.uuzuche.lib_zxing.DisplayUtil.px2dip(getApplicationContext(), dm.heightPixels);*/
    }
}
