package com.island.gyy.factory.analysis;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created with Android Studio.
 * User: Admin
 * Date: 2016/6/1
 * Time: 15:10
 * Describe:
 */
public interface AnalysisWrapper {

    /**
     * @method 初始化设置
     * @author Island_gyy 【island_yy@qq.com】
     * @date 2016/6/3 9:54
     * @describe
     */
    void init(Context context);

    /**
     * @method 点击标签统计
     * @author Island_gyy 【island_yy@qq.com】
     * @date 2016/6/1 15:15
     * @describe
     */
    void sumClick(Context context, String tag);

    /**
     * @method 页面统计开始
     * @author Island_gyy 【island_yy@qq.com】
     * @date 2016/6/1 15:30
     * @describe
     */
    void onActivityResume(Activity activity, String pageName);

    /**
     * @method 页面统计结束
     * @author Island_gyy 【island_yy@qq.com】
     * @date 2016/6/1 16:26
     * @describe
     */
    void onActivityPause(Activity activity, String pageName);

    /**
     * @method 页面统计开始
     * @author Island_gyy 【island_yy@qq.com】
     * @date 2016/6/1 15:30
     * @describe
     */
    void onFragmentPageStart(Context context, Fragment fragment, String pageName);

    /**
     * @method 页面统计结束
     * @author Island_gyy 【island_yy@qq.com】
     * @date 2016/6/1 16:26
     * @describe
     */
    void onFragmentPageEnd(Context context, Fragment fragment, String pageName);


    /**
     * @method 在线统计
     * @param ID 用户id
     * ***/
    void onSignIn(Context context, String ID) ;


    /**
     * @method 离线线统计
     * ***/
    void onSignOff(Context context);

    void killAnayisls(Context context);

}
