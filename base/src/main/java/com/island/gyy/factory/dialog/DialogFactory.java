package com.island.gyy.factory.dialog;

/**
 * Created with Android Studio.
 * User: Admin
 * Date: 2016/8/31
 * Time: 10:35
 * Describe:
 */
public enum  DialogFactory {
    FACTORY;

  private IDialogFactory mDialogFactory;

    public void setDialogFactory(IDialogFactory dialogFactory) {
        mDialogFactory = dialogFactory;
    }

    private DialogFactory(){
  }

    public <T extends IDialogFactory> T getDialogFactory(Class<T> clas) {
        if(mDialogFactory == null)throw new NullPointerException("please set iDialogFactory impl");
        return (T)mDialogFactory;
    }

    public IDialogFactory getIntance(){
        if(mDialogFactory == null)throw new NullPointerException("please set iDialogFactory impl");
        return mDialogFactory;
    }
}
