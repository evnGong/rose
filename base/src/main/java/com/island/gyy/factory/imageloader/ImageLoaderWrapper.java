package com.island.gyy.factory.imageloader;

import android.content.Context;
import android.widget.ImageView;

import java.io.File;

/**
 * 图片加载功能接口
 * <p>
 * Created by Clock on 2016/1/18.
 */
public interface ImageLoaderWrapper {

    /**
     * @method init loader
     * @author Island_gyy 【island_yy@qq.com】
     * @date 2016/6/3 11:54
     * @describe
     */
    void init(Context context);

    /**
     * 显示 图片
     *
     * @param imageView 显示图片的ImageView
     * @param imageFile 图片文件
     * @param option    显示参数设置
     */
    public void displayImage(ImageView imageView, File imageFile, DisplayOption option);

    /**
     * 显示图片
     *
     * @param imageView 显示图片的ImageView
     * @param imageUrl  图片资源的URL
     * @param option    显示参数设置
     */
    public void displayImage(ImageView imageView, String imageUrl, DisplayOption option);

    /**
     * @method show headview
     * @author Island_gyy 【island_yy@qq.com】
     * @date 2016/6/3 11:59
     * @describe
     */
    public void displayHeader(ImageView imageView, String imageUrl);

    /**
     * 图片加载参数
     */
    public static class DisplayOption {
        public enum ImageSize{
            small,middle,normal,lager,none
        }
        /**
         * 加载中的资源id
         */
        public int loadingResId;
        /**
         * 加载失败的资源id
         */
        public int loadErrorResId;

        /**
         *
         * **/
        public ImageSize loadImageSize;
    }
}
