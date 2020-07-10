# CommonTools

  implementation 'com.github.byc-gh:CommonTools:v1.2'

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
  
  aCache.put(key,json,7 * ACache.TIME_DAY);     //添加缓存数据，最后参数为缓存时效，示例为时效7天，具体可自行设置，也可不设置，永久缓存
