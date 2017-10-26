package zxing.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;
import com.example.scanning.R;

/**
 * @author : qingguoguo
 * @datetime : 2017/10/24 19:39
 * @Describe :
 */

public class ScanLifeActivity extends AppCompatActivity {

    private CaptureFragment captureFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_life);

        captureFragment = new CaptureFragment();
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.fragment_scan_life);
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();
    }

    //二维码解析回调函数
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            Log.i("ScanLifeActivity", "二维码扫描成功 ");
            Toast.makeText(ScanLifeActivity.this, "二维码信息："+result, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAnalyzeFailed() {
            Toast.makeText(ScanLifeActivity.this, "扫描二维码失败", Toast.LENGTH_SHORT).show();
        }
    };
}
