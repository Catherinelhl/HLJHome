package com.hlj.net;

import com.live.video.constans.HomeConstants.FunctionTagTable;
import com.live.video.net.callback.IHomeCallBackRequest;

/**
 * 关于我们请求
 * 
 * @author huangyuchao
 * 
 */
public class RequestAboutUs implements IHomeCallBackRequest {

	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FunctionTagTable getNetTag() {
		// TODO Auto-generated method stub
		return FunctionTagTable.ABOUTUS;
	}

}
