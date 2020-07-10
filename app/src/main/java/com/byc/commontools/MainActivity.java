package com.byc.commontools;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.byc.mylibrary.ACache;
import com.byc.mylibrary.AppUtils;
import com.byc.mylibrary.DateUtils;
import com.byc.mylibrary.IDCardValidate;
import com.byc.mylibrary.IPAddressUtil;
import com.byc.mylibrary.L;
import com.byc.mylibrary.PrefUtil;
import com.byc.mylibrary.ScreenUtils;

public class MainActivity extends AppCompatActivity {

    private String time = "1594287701";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView tv_time = findViewById(R.id.tv_time);
        final TextView tv_ip = findViewById(R.id.tv_ip);
        final TextView tv_crash = findViewById(R.id.tv_crash);
        final TextView tv_add_cache = findViewById(R.id.tv_add_cache);
        final TextView tv_get_cache = findViewById(R.id.tv_get_cache);
        final TextView tv_clear_cache = findViewById(R.id.tv_clear_cache);
        final TextView tv_add_sp = findViewById(R.id.tv_add_sp);
        final TextView tv_get_sp = findViewById(R.id.tv_get_sp);
        final TextView tv_clear_sp = findViewById(R.id.tv_clear_sp);
        final TextView tv_get_screen_width_height = findViewById(R.id.tv_get_screen_width_height);
        final TextView tv_shoot_screen_status = findViewById(R.id.tv_shoot_screen_status);
        final TextView tv_shoot_screen = findViewById(R.id.tv_shoot_screen);
        final ImageView iv_screen_shoot = findViewById(R.id.iv_screen_shoot);
        final TextView tv_log = findViewById(R.id.tv_log);
        final TextView tv_get_version = findViewById(R.id.tv_get_version);

        String timestamp = DateUtils.FormatTimestamp(time, DateUtils.TIME_FORMAT_ZERO);
        tv_time.setText(timestamp);
        tv_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long l = System.currentTimeMillis() / 1000;
                String currentTime = String.valueOf(l);
                String times = DateUtils.FormatTimestamp(currentTime, DateUtils.TIME_FORMAT_ZERO);
                tv_time.setText(times);
            }
        });

        final String s = IDCardValidate.validate_effective("3701000000000000");
        Log.e("身份证号码验证", "onCreate: " + s);

        tv_ip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ipAddress = IPAddressUtil.getIpAddress(MainActivity.this);
                tv_ip.setText(ipAddress);
            }
        });

        tv_crash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a = 10 / 0;
                Toast.makeText(MainActivity.this, "" + a, Toast.LENGTH_SHORT).show();
            }
        });

        final ACache aCache = ACache.get(MainActivity.this);
        tv_add_cache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aCache.put("test_cache","这是测试缓存添加的数据",7 * ACache.TIME_DAY); //添加缓存数据，最后参数为缓存时效，示例为时效7天，具体可自行设置，也可不设置，永久缓存
            }
        });
        tv_get_cache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = aCache.getAsString("test_cache"); //获取缓存数据
                Toast.makeText(MainActivity.this, ""+string, Toast.LENGTH_SHORT).show();
            }
        });
        tv_clear_cache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aCache.clear();
            }
        });

        tv_add_sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefUtil.putString(MainActivity.this,"test","测试SharedPreferences数据存储");
            }
        });
        tv_get_sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = PrefUtil.getString(MainActivity.this, "test", "默认数据");
                Toast.makeText(MainActivity.this, ""+string, Toast.LENGTH_SHORT).show();
            }
        });
        tv_clear_sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefUtil.removeStr(MainActivity.this,"test");
            }
        });

        tv_get_screen_width_height.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_get_screen_width_height.setText("宽："+ ScreenUtils.getScreenWidth(MainActivity.this)+"     高："+ScreenUtils.getScreenHeight(MainActivity.this)+"     状态栏高："+ScreenUtils.getStatusHeight(MainActivity.this));
            }
        });
        tv_shoot_screen_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = ScreenUtils.snapShotWithStatusBar(MainActivity.this);
                iv_screen_shoot.setImageBitmap(bitmap);
            }
        });
        tv_shoot_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = ScreenUtils.snapShotWithoutStatusBar(MainActivity.this);
                iv_screen_shoot.setImageBitmap(bitmap);
            }
        });

        tv_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                L.e("这是打印出来的内容");
            }
        });

        tv_get_version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_get_version.setText("包名："+ AppUtils.getPackageName(MainActivity.this)+"   版本号："+AppUtils.getVersionCode());
            }
        });
    }
}
