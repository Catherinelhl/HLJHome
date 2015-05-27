package com.hlj.HomeActivity.set;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.hlj.HomeActivity.R;
import com.hlj.utils.Logger;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 速度测试
 * 
 * @author huangyuchao
 * 
 */
public class SettingSpeedTest extends Activity implements OnClickListener {

	View speedView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.setting_main);
		speedView = ((ViewStub) findViewById(R.id.set_speed)).inflate();
		super.onCreate(savedInstanceState);

		initSpeedView();
	}

	TextView set_name1, set_name2;
	ImageView set_item_log;

	Button setting_speed_source;

	ProgressBar down_pb1, down_pb2, down_pb3, down_pb4;
	TextView tv1, tv2, tv3, tv4;

	TextView bottomtv;

	private ExecutorService pool = Executors.newFixedThreadPool(2);

	private String[] urls = {
			"http://202.108.17.115/v.cctv.com/flash/mp4video31/TMS/2013/11/01/be1cfb8e001c4c599d719d9acda5e76c_h264818000nero_aac32.mp4",// CNTV
			"http://g3.letv.cn/14/52/33/2141429372.0.flv",// 百度云
			"http://video.store.qq.com/o0012t23yb9.mp4",// 腾讯
			"http://owlglsb.wasu.tv/1383798271464_798651.ts?action=play&Contentid=1792427" };// 华硕

	private ArrayList<ProgressBar> progressList = new ArrayList();
	private ArrayList<TextView> textList = new ArrayList();

	private ArrayList<ImageView> imgaList = new ArrayList();

	ImageView ssim1, ssim2, ssim3, ssim4;

	private void initSpeedView() {

		this.set_name1 = ((TextView) findViewById(R.id.set_name1));
		this.set_name2 = ((TextView) findViewById(R.id.set_name2));
		this.set_name1.setText("速度测试");
		this.set_name2.setText("速度测试");

		set_item_log = (ImageView) findViewById(R.id.set_item_log);
		set_item_log.setImageResource(R.drawable.testspeet_setup);

		setting_speed_source = (Button) findViewById(R.id.setting_speed_source);
		setting_speed_source.setOnClickListener(this);

		down_pb1 = (ProgressBar) findViewById(R.id.down_pb1);
		progressList.add(down_pb1);
		down_pb2 = (ProgressBar) findViewById(R.id.down_pb2);
		progressList.add(down_pb2);
		down_pb3 = (ProgressBar) findViewById(R.id.down_pb3);
		progressList.add(down_pb3);
		down_pb4 = (ProgressBar) findViewById(R.id.down_pb4);
		progressList.add(down_pb4);

		tv1 = (TextView) findViewById(R.id.tv1);
		textList.add(tv1);
		tv2 = (TextView) findViewById(R.id.tv2);
		textList.add(tv2);
		tv3 = (TextView) findViewById(R.id.tv3);
		textList.add(tv3);
		tv4 = (TextView) findViewById(R.id.tv4);
		textList.add(tv4);

		bottomtv = (TextView) findViewById(R.id.bottomtv);

		ssim1 = (ImageView) findViewById(R.id.ssim1);
		ssim2 = (ImageView) findViewById(R.id.ssim2);
		ssim3 = (ImageView) findViewById(R.id.ssim3);
		ssim4 = (ImageView) findViewById(R.id.ssim4);

		imgaList.add(ssim1);
		imgaList.add(ssim2);
		imgaList.add(ssim3);
		imgaList.add(ssim4);

	}

	private Runnable downTask = new Runnable() {
		public void run() {

			// for (int i = 0; i < urls.length; i++) {
			downSourceCheck(urls[i], i);
			// }

		};
	};

	private static int TESTTIME = 5 * 1000;
	int downTime;
	int speed;

	private void downSourceCheck(String address, int position) {
		try {
			URLConnection connection = new URL(address).openConnection();
			connection.connect();
			InputStream inputStream = connection.getInputStream();
			handler.sendEmptyMessage(0);

			byte[] buffer = new byte[1024];
			long readnum = 0L;
			long time2 = System.currentTimeMillis();

			while (-1 != inputStream.read(buffer)) {
				readnum += inputStream.read(buffer);
				long time3 = System.currentTimeMillis();
				int timediss = (int) (time3 - time2);
				if (timediss > 500) {
					time2 = time3;
					downTime += timediss;
					speed = (int) (1000L * readnum / (1024 * downTime));
					// message.what = 1;
					handler.sendEmptyMessage(1);
				}

				if (downTime >= TESTTIME) {
					speed = (int) (1000L * readnum / (1024 * TESTTIME));
					downTime = TESTTIME;
					// message.what = 2;
					handler.sendEmptyMessage(1);
					break;
				}
			}
			Logger.log("downTime:" + downTime);

			handler.sendEmptyMessage(2);

			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			Logger.log(e.getLocalizedMessage());
		}
	}

	int i = 0;

	private int[] speeds = new int[4];

	Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case 0:
				progressList.get(i).setMax(TESTTIME);
				textList.get(i).setVisibility(View.VISIBLE);
				break;
			case 1:
				progressList.get(i).setProgress(downTime);
				textList.get(i).setText(speed + "kb/sec");
				break;
			case 2:
				// textList.get(i).setText(speed + "kb/sec");
				speeds[i] = speed;
				speed = 0;
				downTime = 0;

				i = i + 1;
				Logger.log("i=" + i);
				if (i > 3) {
					setting_speed_source.setText("开始资源测速");
					setting_speed_source.setEnabled(true);
					i = 0;
					// bottomtv.setText("建议您选择的高清源为：");
					int num1 = 0;
					int num2 = 0;
					int num3 = 0;
					int num4 = 0;
					for (int j = 0; j < speeds.length; j++) {
						// if (speeds[j] < 1000) {
						// imgaList.get(j).setVisibility(View.GONE);
						// } else {
						// imgaList.get(j).setVisibility(View.VISIBLE);
						// }

						if (speeds[j] < 150) {
							num1++;
						} else if (speeds[j] > 150 && speeds[j] < 250) {
							num2++;
						} else if (speeds[j] > 250 && speeds[j] < 1000) {
							num3++;
						} else if (speeds[j] > 1000) {
							num4++;
						}

						if (num1 >= 4) {
							bottomtv.setText("带宽太古老,观看会不正常,建议跟上时代潮流。");
						} else if (num2 >= 2) {
							bottomtv.setText("带宽很一般，可能部分节目会有卡顿现象。");
						} else if (num3 >= 3) {
							bottomtv.setText("带宽不错，看高清节目有可能出现不流畅。");
						} else if (num4 >= 3) {
							bottomtv.setText("带宽很牛，高清节目观看无忧，尽情享受吧！");
						}

					}
				} else {
					pool.execute(downTask);
				}
				break;
			default:
				pool.execute(downTask);
				break;
			}
		};
	};

	@Override
	public void onClick(View v) {
		setting_speed_source.setText("测试中......");
		setting_speed_source.setEnabled(false);
		down_pb1.setProgress(0);
		down_pb2.setProgress(0);
		down_pb3.setProgress(0);
		down_pb4.setProgress(0);
		bottomtv.setVisibility(View.VISIBLE);

		pool.execute(this.downTask);
	}
}
