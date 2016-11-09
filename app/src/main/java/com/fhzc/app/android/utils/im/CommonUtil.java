package com.fhzc.app.android.utils.im;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Window;
import android.view.WindowManager;

import com.base.platform.utils.android.ToastTool;
import com.base.platform.utils.java.StringTools;
import com.fhzc.app.android.configer.constants.Constant;
import com.fhzc.app.android.models.ShareModel;
import com.fhzc.app.android.utils.android.IntentTools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by User on 2016/7/14.
 */
public class CommonUtil {
    /**
     * @return
     * @Description 判断存储卡是否存在
     */
    public static boolean checkSDCard() {
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        }

        return false;
    }

    /**
     * @param text
     * @return
     * @Description 判断是否是url
     */
    public static String matchUrl(String text) {
        if (TextUtils.isEmpty(text)) {
            return null;
        }
        Pattern p = Pattern.compile(
                "[http]+[://]+[0-9A-Za-z:/[-]_#[?][=][.][&]]*",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        } else {
            return null;
        }
    }

    /**
     * encodeBase64File:(将文件转成base64 字符串). <br/>
     *
     * @param path 文件路径
     * @return
     * @throws Exception
     * @author guhaizhou@126.com
     * @since JDK 1.6
     */
    public static String encodeBase64File(String path) throws Exception {
        File file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return Base64.encodeToString(buffer, Base64.DEFAULT);
    }

    /**
     * decoderBase64File:(将base64字符解码保存文件). <br/>
     *
     * @param base64Code 编码后的字串
     * @param savePath   文件保存路径
     * @throws Exception
     * @author guhaizhou@126.com
     * @since JDK 1.6
     */
    public static void decoderBase64File(String base64Code, String savePath) throws Exception {
        byte[] buffer = Base64.decode(base64Code, Base64.DEFAULT);
        File file = new File(savePath);
        FileOutputStream out = new FileOutputStream(file);
        out.write(buffer);
        out.close();
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /****
     * 是否大于kiakat系统(4.4)
     *
     * @return
     */
    public static boolean isUpperKK() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    public static boolean isBelowVersion(final int v) {
        return Build.VERSION.SDK_INT < v;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * Create a color integer value with specified alpha.
     * This may be useful to change alpha value of background color.
     *
     * @param alpha     alpha value from 0.0f to 1.0f.
     * @param baseColor base color. alpha value will be ignored.
     * @return a color with alpha made from base color
     */
    public static int getColorWithAlpha(float alpha, int baseColor) {
        int a = Math.min(255, Math.max(0, (int) (alpha * 255))) << 24;
        int rgb = 0x00ffffff & baseColor;
        return a + rgb;
    }


    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }


    /**
     * 设置状态栏字体图标为深色，需要MIUIV6以上
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    public static synchronized String getWebStorageDirectory(Context context) {
        return context.getApplicationInfo().dataDir + "/databases/";
    }

    /**
     * 隐藏手机号码中间四位
     *
     * @param mobile
     * @return
     */
    public static String getPhoneNum(String mobile) {
        if (StringTools.isNullOrEmpty(mobile)) {
            return "";
        }
        return mobile.substring(0, 3) + "****" + mobile.substring(7, mobile.length());
    }

    /**
     * 隐藏身份证号生日
     *
     * @param id
     * @return
     */
    public static String getIDNum(String id) {
        if (StringTools.isNullOrEmpty(id)) {
            return "";
        }
        return id.substring(0, 6) + "****" + id.substring(14, id.length());
    }

    /**
     * 判断是否为密码
     */
    public static boolean isPassword(String password) {
        if (StringTools.isNullOrEmpty(password)) {
            ToastTool.show("请输入密码");
            return false;
        }
        if (password.length() < 6 || password.length() > 20) {
            ToastTool.show("请输入6-20位密码");
            return false;
        }
        return true;

    }

    /**
     * 判断是否为密码
     */
    public static boolean isPasswordEqul(String password, String comfirmPsw) {
        if (!StringTools.isEqual(password, comfirmPsw)) {
            ToastTool.show("两次密码输入不一致");
            return false;
        }
        return true;

    }

    /**
     * 拨打电话
     *
     * @param context
     * @param phoneNum
     */
    public static void call(Context context, String phoneNum) {
        try {
            Intent intent = new Intent();
            //设置要拨打的号码
            intent.setData(Uri.parse("tel:" + phoneNum));
            //设置动作,拨号 动作
            intent.setAction(intent.ACTION_CALL);
            //跳转到拨号界面
            context.startActivity(intent);
        } catch (Exception e) {
        }

    }

    /**
     * 判断消息是否为分享
     *
     * @param content
     * @return
     */
    public static boolean isShareText(String content) {
        return content.startsWith("share$_$rights$_$") || content.startsWith("share$_$activity$_$") || content.startsWith("share$_$report$_$") || content.startsWith("share$_$product$_$");

    }

    /**
     * 得到分享的内容
     *
     * @param content
     * @return
     */
    public static ShareModel getShareModel(String content) {
        ShareModel model = new ShareModel();
        if (StringTools.isNullOrEmpty(content)) {
            return model;
        }
        try {
            String[] strings = content.split("\\$_\\$");
            String type = strings[1];
//            String title,introduce;
            model.setType(type);
//            if(type.equals("activity")){
//                title = strings[3];
//                introduce = strings[4];
//            }else if(type.equals("report")){
//                title = strings[3];
//                introduce = strings[4];
//            }else if(type.equals("product")){
//                title = strings[3];
//                introduce = strings[4];
//            }else if(type.equals("rights")){
//                title = strings[3];
//                introduce = strings[4];
//            }
            model.setFid(Integer.parseInt(strings[2]));
            model.setTitle(strings[3]);
            model.setIntroduce(strings[4]);

        } catch (Exception e) {

        }


        return model;

    }

    public static void onShareClick(Context context, ShareModel model) {

        if (model == null)
            return;
        switch (model.getType()) {
            case Constant.SHARE_TYPE_ACTIVITY:
                IntentTools.startActivityDetail(context, model.getFid());
                break;
            case Constant.SHARE_TYPE_REPORT:
                IntentTools.startReportDetail(context, model.getFid());
                break;
            case Constant.SHARE_TYPE_RIGHTS:
                IntentTools.startRightDetail(context, model.getFid());
                break;
            case Constant.SHARE_TYPE_PRODUCT:
                IntentTools.startProduceDetail(context, model.getFid());
                break;
        }
    }
     public static String getShareTextByType(String type){
         switch (type) {
             case Constant.SHARE_TYPE_ACTIVITY:
                 return "查看活动详情";
             case Constant.SHARE_TYPE_REPORT:
                 return "查看报告详情";
             case Constant.SHARE_TYPE_RIGHTS:
                 return "查看权益详情";
             case Constant.SHARE_TYPE_PRODUCT:
                 return "查看产品详情";
         }
             return "查看产品详情";
     }
    /**
     * double 保留两位小数
     */
    public static String keepTwoDecimalPlaces(double privce) {
        if (privce != 0) {
            String keepTwoDecimalPlaces = "";
            keepTwoDecimalPlaces = new DecimalFormat("######0.00")
                    .format(privce);
            return keepTwoDecimalPlaces;
        } else {
            return "0.00";
        }
    }
}
