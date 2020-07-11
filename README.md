# CommonTools

  implementation 'com.github.byc-gh:CommonTools:v1.4'

----
1、崩溃捕获以减少不必要的程序崩溃
----
  使用：在程序的Application中进行设置，代码如下

    public class BaseApplication extends Application {
      @Override
      public void onCreate() {
          super.onCreate();
          install();
      }

      private void install() {
        Cockroach.install(BaseApplication.this, new ExceptionHandler() {
            @Override
            protected void onUncaughtExceptionHappened(Thread thread, Throwable throwable) {
                Log.e("AndroidRuntime", "--->onUncaughtExceptionHappened:" + thread + "<---", throwable);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
            }

            @Override
            protected void onBandageExceptionHappened(Throwable throwable) {
                throwable.printStackTrace();//打印警告级别log，该throwable可能是最开始的bug导致的，无需关心
            }

            @Override
            protected void onEnterSafeMode() {

            }
        });
      }
    }
    
----
2、日期格式化工具
----
  DateUtils.FormatTimestamp(String time, String format);    //将时间戳格式化为自己指定的时间格式

  目前给出的格式有如下几种：

    public static String TIME_FORMAT_ZERO = "yyyy年MM月dd日 HH时mm分ss秒";
    public static String TIME_FORMAT_ONE = "yyyy-MM-dd HH:mm:ss";
    public static String TIME_FORMAT_TWO = "yyyy.MM.dd HH:mm:ss";
    public static String TIME_FORMAT_THREE = "yyyy-MM-dd HH:mm";
    
  DateUtils.TimestampFormat(String format, String time);    //将格式化的时间转为时间戳
  
----
3、身份证号码本地验证工具
----
  IDCardValidate.validate_effective(String idcardNumber);     //填入身份证号码验证身份证号码格式是否正确，正确则返回身份证号
  
----
4、IP地址获取工具
----
  IPAddressUtil.getIpAddress(Context context);     //返回设备当前的网络IP
  
----
5、缓存数据工具
----
  ACache aCache = ACache.get(mContext);
  
  String string = aCache.getAsString(key);     //获取缓存数据
  
  aCache.put(key,json,7 * ACache.TIME_DAY);     //添加缓存数据，最后参数为缓存时效，示例为时效7天，具体可自行设置，也可不设置

----
6、SharedPreferences数据操作工具
----
  PrefUtil.putString(context,key,value);     //存储数据
  
  PrefUtil.getString(context, key, defaltValue);     //获取数据
  
  其他数据类型存储同上

----
7、屏幕信息工具
----
 ScreenUtils.getScreenWidth(context);     //获取屏幕宽度
 
 ScreenUtils.getScreenHeight(context);     //获取屏幕高度
 
 ScreenUtils.getStatusHeight(context);     //获取屏幕状态栏高度
 
 ScreenUtils.getScreenDensity(context);     //获取屏幕像素密度
 
 ScreenUtils.snapShotWithStatusBar(context);     //获取当前屏幕截图，包含状态栏
 
 ScreenUtils.snapShotWithoutStatusBar(context);     //获取当前屏幕截图，不包含状态栏
 
----
8、Log日志工具
----
 在APP的Application中设置L.isDebug = true; 此时程序中自行打印的Log日志可见，反之则不可见
 
----
9、软键盘工具
----
  KeyBoardUtils.openKeyBord(editText,context);     //打开软键盘
  
  KeyBoardUtils.closeKeyBord(context,editText);     //关闭软键盘
  
  KeyBoardUtils.isSHowKeyboard(context,view);     //判断软键盘是否弹出
  
----
10、APP操作工具
----
  AppUtils.getAppName(context);     //获取应用程序名称
  
  AppUtils.getVersionName(context);     //当前应用的版本名称
  
  AppUtils.getVersionCode(context);     //当前应用的版本号
  
  AppUtils.getPackageName(context);     //当前应用包名
  
  AppUtils.getBitmap(context);     //获取图标

----
11、网络状态监听工具
----
  在Application的onCreate方法中添加

         NetworkManager.getDefault().init(this);
  
  在AndroidManifest.xml文件中添加如下代码：
  
        <receiver android:name="com.byc.mylibrary.networklistener.NetStateReceiver">
            <intent-filter>
                <action android:name="android.net.conn.CONNECTIVITY_CHANGE"/>
            </intent-filter>
        </receiver>
        
  在Activity中添加
  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NetworkManager.getDefault().registerObserver(this);
    }
  
    //网络监听
    @NetworkListener()
    public void netork(@NetType String type){
        switch (type){
            case NetType.AUTO:
                L.e("network","AUTO");
                break;
            case NetType.CMNET:
                L.e("network","CMNET");
                break;
            case NetType.CMWAP:
                L.e("network","CMWAP");
                break;
            case NetType.WIFI:
                L.e("network","WIFI");
                break;
            case NetType.NONE:
                L.e("network","NONE");
                break;
        }
    }
