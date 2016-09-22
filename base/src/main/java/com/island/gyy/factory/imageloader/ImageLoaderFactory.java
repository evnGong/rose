package com.island.gyy.factory.imageloader;

/**
 * ImageLoader工厂类
 * <p>
 * Created by Clock on 2016/1/18.
 */
public enum  ImageLoaderFactory {
    LOAD;
    private volatile ImageLoaderWrapper sInstance;

    private ImageLoaderFactory() {

    }

    /**
     * 获取图片加载器
     *
     * @return
     */
    public <T extends ImageLoaderWrapper> T getLoader(Class<T> tClass) {
        if(sInstance == null)throw  new NullPointerException("Loader Not setting");
        return (T)sInstance;

    }

    public ImageLoaderWrapper getLoader() {
        if(sInstance == null)throw  new NullPointerException("Loader Not setting");
        return sInstance;
    }
    
    public void setsInstance(ImageLoaderWrapper sInstance) {
        this.sInstance = sInstance;
    }
}
