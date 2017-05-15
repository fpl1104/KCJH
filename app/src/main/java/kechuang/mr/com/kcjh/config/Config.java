package kechuang.mr.com.kcjh.config;

/**
 * 配置文件
 */
public class Config {
	

    // 环境变量
    public static final String DEVELOPMENT_ENVIRONMENT = "http://139.199.44.121";
    public static final String TEST_ENVIRONMENT = "http://139.199.44.121:8081"; // 测试外网环境
    public static final String PRODUCTION_ENVIRONMENT = "http://www.szcitizencard.com";//生产
    /**
     * 服务器地址
     */
    public static String configWebUrl = "http://106.14.81.196:8280/sysapp/eapppub1/";
//    public static String configWebUrl = "http://120.76.190.223:8980/mrsyg/kqgoods/";

    /**
     * 服务器地址后缀
     *
     */
    public static  String webUrlSuffix = ".json";

    //文件上传地址
    public static  String FileUrl = "http://120.76.190.223:8980/picserver/";

    //html5地址
    public static String h5Url = "http://120.76.190.223:8980/html5/KeChuangProject/";
    public static String h5ErrorUrl = "file:///android_asset/network-error.html";


    public static String PASS_KEY = "PASS_KEY_MAP";
    public static String INTENT_SECONDACTIVITY_KEY = "INTENT_SECONDACTIVITY_KEY_MAP";
}
