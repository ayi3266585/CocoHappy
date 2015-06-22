package com.happy.util;

/**
 * Http回调接口 
 */
public interface HttpCallbackListener {
	/**
	 * 返回成功的数据 
	 */
	void onFinish(String reponse);

	/**
	 * 返回失败的信息 
	 */
	void onError(Exception e);
}
