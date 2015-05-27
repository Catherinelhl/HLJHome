package com.hlj.utils;

import java.io.*;

/**
 * 文件操作工具
 * @author hyc
 *
 */
public class FileUtils {
	
	/**
	 * 保存到文件中
	 * @param id
	 * @param path
	 * @throws IOException
	 */
	public static void saveData(String id, String path) throws IOException {
		FileOutputStream fOut = null;
		if (CommonTools.hasSDCard()) {
			File fpic = new File(path);
			if (fpic.exists() && fpic.isFile() && fpic.length() > 100)
				return;
			try {
				if (!fpic.getParentFile().exists()) {
					// 如果父文件夹不存在，先创建
					fpic.getParentFile().mkdirs();
				}
				fpic.createNewFile();
			} catch (IOException e) {
			}
			try {
				fOut = new FileOutputStream(fpic);
				fOut.write(id.getBytes());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {
					assert fOut != null;
					fOut.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					fOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void readToBuffer(StringBuffer buffer, String filePath)
			throws IOException {
		InputStream is = new FileInputStream(filePath);
		String line; // 用来保存每行读取的内容
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		line = reader.readLine(); // 读取第一行
		while (line != null) { // 如果 line 为空说明读完了
			buffer.append(line); // 将读到的内容添加到 buffer 中
			buffer.append("\n"); // 添加换行符
			line = reader.readLine(); // 读取下一行
		}
		reader.close();
		is.close();
	}

	public static String readFile(String filePath) throws IOException {
		StringBuffer sb = new StringBuffer();
		FileUtils.readToBuffer(sb, filePath);
		return sb.toString().trim();
	}

	// public static boolean deleteFoder(String filePath) throws IOException {
	//
	// }

	/**
	 * 删除文件
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static boolean delectFile(String filePath) throws IOException {
		boolean isSuccess = false;
		if (CommonTools.hasSDCard()) {
			File file = new File(filePath);
			if (file.exists()) { // 判断文件是否存在
				if (file.isFile()) { // 判断是否是文件
					isSuccess = file.delete(); // delete()方法 你应该知道 是删除的意思;
				}
			}
		}
		return isSuccess;
	}

	/**
	 * 删除指定的zip文件
	 * 
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static boolean delectZipFile(String filename) throws IOException {
		boolean isSuccess = false;
		File file = new File(filename);
		Logger.log("exists:" + file.exists());
		// 判断文件是否存在
		if (file.exists()) {
			file.setExecutable(true, false);
			file.setReadable(true, false);
			file.setWritable(true, false);
			isSuccess = file.delete();
			Logger.log("isSuccess:" + isSuccess);
		}
		return isSuccess;
	}

}
