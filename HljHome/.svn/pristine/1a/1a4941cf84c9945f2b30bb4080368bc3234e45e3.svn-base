package com.hlj.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.live.video.constans.EpgInfo;

public class EpgHelper {

	/**
	 * 获取节目列表
	 * 
	 * @param paramString
	 * @return
	 */
	public static ArrayList<EpgInfo> getEpgInfo(String paramString, int id, int type) {

		ArrayList<EpgInfo> list = new ArrayList<EpgInfo>();

		try {
			Document document = Jsoup
					.connect(
							"http://m.tvmao.com/program/" + paramString + "-w"
									+ SwissArmyKnife.getWeekOfDate() + ".html")
					.userAgent("Mozilla").timeout(3000).get();

			Elements localElements = document.select("tr");

			Iterator localIterator = localElements.iterator();
			while (localIterator.hasNext()) {
				Element localElement = (Element) localIterator.next();

				String str = localElement.text();
				if ((!str.startsWith("0")) && (!str.startsWith("1"))
						&& (!str.startsWith("2")))
					// 重新循环
					continue;
				EpgInfo localEpgInfo = new EpgInfo();
				localEpgInfo.id = id;
				localEpgInfo.type = type;
				localEpgInfo.date = SwissArmyKnife.getDate();
				localEpgInfo.time = localElement.text().split(" ")[0];
				Logger.log("time="+localEpgInfo.time);
				localEpgInfo.detail = localElement.text().split(" ")[1];
				Logger.log("detail="+localEpgInfo.detail);
				list.add(localEpgInfo);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

}
