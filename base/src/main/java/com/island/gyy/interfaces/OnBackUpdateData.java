package com.island.gyy.interfaces;

import java.io.Serializable;

/**
* @Description: 返回更新数据接口
* @author 罗深志
* @version V1.0 
*/ 
public interface OnBackUpdateData<T> extends Serializable{
	
	void updata(T obj);
}
