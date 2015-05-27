package com.live.video.net;

import java.io.UnsupportedEncodingException;
import java.lang.ref.SoftReference;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.hlj.utils.Logger;
import com.hlj.utils.StringTools;
import com.live.video.constans.Constants;
import com.live.video.net.callback.ICallBackRequest;
import com.live.video.net.callback.IUpdateData;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

/**
 * 所有的http请求
 * 
 * @author huangyuchao
 * 
 */
public class HttpLoadDataTask extends
		AsyncTask<ICallBackRequest, String, String> {

	private Context mContext;
	private IUpdateData callBack;
	private boolean isShownDialog;

	public static ProgressDialog mDialog;
	private SoftReference<ProgressDialog> softReference;// ---将 dialog储存在缓存里面

	private boolean isComplete;// ---程序是否完成

	public HttpLoadDataTask(Context c, IUpdateData callBack,
			boolean isShownDialog) {
		this.mContext = c;
		this.callBack = callBack;
		this.isShownDialog = isShownDialog;

		mDialog = new ProgressDialog(mContext);
		softReference = new SoftReference<ProgressDialog>(mDialog);
	}

	@Override
	protected void onPreExecute() {
		isComplete = false;
		if (isShownDialog) {
			if ((!isComplete) && !this.isCancelled()) {
				mDialog = softReference.get();
				mDialog.setMessage("稍等片刻，精彩内容即将呈现......");
				mDialog.setCancelable(true);
				mDialog.show();
				mDialog.setOnCancelListener(new DialogCancleLisen());
			}
		}
		super.onPreExecute();
	}

	public class DialogCancleLisen implements DialogInterface.OnCancelListener {

		@Override
		public void onCancel(DialogInterface dialog) {
			if ((!HttpLoadDataTask.this.isCancelled() && !isComplete)) {
				dialog.cancel();
				HttpLoadDataTask.this.cancel(true);
			}
		}

	}

	private ICallBackRequest request;

	@Override
	protected String doInBackground(ICallBackRequest... params) {
		// ---- 进行网络传输数据
		String result = "";
		try {

			String url = "http://ipad.tongzhuo100.com/html/18195/";

			result = httpGet(url);

		} catch (Exception e) {
			Logger.log("NETERROR--" + e.getMessage());
		}
		return result;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		try {
			isComplete = true;
			if (isShownDialog) {
				if (mDialog != null) {
					mDialog.dismiss();
				}
			}

			if (callBack != null) {
				callBack.updateUi(result);

				// JSONObject jo = new JSONObject(result);
				// boolean state = JsonTools.getBoolean(jo, "success");
				// Logger.log("----JSONObject--------------" + jo);
				// Logger.log("----state--------------" + state);
				// if (state) {
				// // callBack.updateUi(result);
				// } else {
				// String msg = JsonTools.getString(jo, "msg");
				// Logger.log("----msg--------------" + msg);
				// callBack.handleErrorData(msg);
				// }

			} else {
				Logger.log("HttpRequstTask's callback is null");
			}
		} catch (Exception e) {
			Logger.log("onPostExecute error=" + e.getMessage());
			if (callBack != null) {
				callBack.handleErrorData("未知错误");
			}
		}

	}

	// ---拼接url的字符串
	private String getParams(String params) throws JSONException {
		JSONObject paramsJson = new JSONObject(params);
		StringBuilder sb = new StringBuilder();

		Iterator<String> iterator = paramsJson.keys();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			sb.append("&");
			sb.append(key);
			sb.append("=");

			String value = String.valueOf(paramsJson.get(key));

			try {
				sb.append(URLEncoder.encode(StringTools.defaultToUtf(value),
						"UTF-8"));
			} catch (Exception e) {
				Logger.log("Encoding error=" + e.getMessage());
			}

		}
		String url = sb.toString();
		String newUrl = replaceFirstChar(url);
		return newUrl;
	}

	/**
	 * 首个"&"替换成"?"
	 * 
	 * @param url
	 * @return
	 */
	private String replaceFirstChar(String url) {
		StringBuffer sb = new StringBuffer();
		String s = url.substring(1);
		sb.append("?");
		sb.append(s);
		return sb.toString();
	}

	private HttpPost getHttpPost(ICallBackRequest request) {

		List<NameValuePair> qparams = new ArrayList<NameValuePair>();

		// 把请求的 json对象拆开以post传递
		try {
			JSONObject json = new JSONObject(request.getInfo());
			for (Iterator it = json.keys(); it.hasNext();) {
				String key = (String) it.next();
				String value = (String) json.get(key);
				qparams.add(new BasicNameValuePair(key, value));
			}

		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		HttpPost httpPost = new HttpPost(Constants.basePath
				+ request.getNetTag().getFunctionname());

		UrlEncodedFormEntity paramEntity = null;
		// org.apache.http.Header header = new BasicHeader("PagingRows","") ;
		try {
			paramEntity = new UrlEncodedFormEntity(qparams, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			Logger.log("UnsupportedEncodingException=="
					+ e.getLocalizedMessage());
		}
		// httpPost.addHeader(header);
		httpPost.setEntity(paramEntity);
		return httpPost;
	}

	private String httpGet(String url) throws Exception {
		HttpGet get = new HttpGet(url);
		String strResult = "";

		get.addHeader("Content-Type", "application/json;charset=utf-8");

		get.addHeader("Cookie", "fastweb_title=fastwebtongzhuo100");
		
		strResult = HttpRequest.httpRequestGet(get);
		return strResult;
	}

}
