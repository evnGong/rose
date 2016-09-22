package com.island.gyy.factory.analysis;

import com.island.gyy.factory.imageloader.ImageLoaderWrapper;

/**
 * Created with Android Studio.
 * User: Admin
 * Date: 2016/5/9
 * Time: 15:08
 * Describe:
 */
public enum AnalysisFractory {

    ANALYSIS;

    private volatile AnalysisWrapper  sInstance;


    private AnalysisFractory() {
    }


    /**
     * 获取图片加载器
     *
     * @return
     */
    public <T extends AnalysisWrapper> T getLoader(Class<T> tClass) {
        if(sInstance == null)throw  new NullPointerException("Analysis Not setting");
        return (T)sInstance;

    }

    public AnalysisWrapper getLoader() {
        if(sInstance == null)throw  new NullPointerException("Analysis Not setting");
        return sInstance;
    }

    public void setsInstance(AnalysisWrapper sInstance) {
        this.sInstance = sInstance;
    }

}
