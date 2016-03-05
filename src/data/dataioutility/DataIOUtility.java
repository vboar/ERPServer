/**
 * 数据层操作的公共类
 * @author Vboar
 * @date 2014/11/15
 */

package data.dataioutility;

import java.io.*;
import java.util.ArrayList;

public class DataIOUtility {

	/**
	 * 父路径
	 */
	public String fatherPath = "data/currentdata/";

	/**
	 * 子路径
	 */
	private String path;

	/**
	 * 列表的每行元素间的分隔符
	 */
	public static final String splitStr = "#";

	/**
	 * 构造函数
	 * @param path
	 */
	public DataIOUtility(String path) {
		this.path = path;
	}

	/**
	 * 将数据写入文件（覆盖）
	 * @param lists
	 */
	public void writeData(ArrayList<String> lists) {
		try {
			FileWriter fw = new FileWriter(fatherPath + path + ".txt", false);
			for(String s: lists) {
				fw.write(s + "\n");
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将数据写入文件（追加一行）
	 */
	public void writeDataAdd(String s) {
		try {
			FileWriter fw = new FileWriter(fatherPath + path + ".txt", true);
			fw.write(s + "\n");
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 从文件中读取数据
	 * @return
	 */
	public ArrayList<String> readData() {
		ArrayList<String> lists = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(fatherPath + path + ".txt")));
			String temp = null;
			while((temp = br.readLine()) != null) {
				lists.add(temp);
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lists;
	}
	
	/**
	 * 将多个String化为分割的String
	 * @param strs
	 * @return
	 */
	public ArrayList<String[]> stringToArrayAll(ArrayList<String> strs) {
		ArrayList<String[]> lists = new ArrayList<String[]>();
		for(String s: strs) {
			lists.add(s.split(";"));
		}
		return lists;
	}
	
	/**
	 * 将多个分割的String转化为String
	 * @param lists
	 * @return
	 */
	public ArrayList<String> arrayToStringAll(ArrayList<String[]> lists) {
		ArrayList<String> strs = new ArrayList<String>();
		for(String[] s: lists) {
			String temp = "";
			for(int i = 0; i < s.length; i++) {
				temp = temp + s[i] + ";";
			}
			strs.add(temp);
		}
		return strs;
	}

	/**
	 * 复制文件
	 * @param oldPath
	 * @param newPath
	 */
	public void copyFile(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) {
				InputStream inStream = new FileInputStream(oldPath);
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				int length;
				while ( (byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread;
					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		}
		catch (Exception e) {
			System.out.println("复制单个文件操作出错");
			e.printStackTrace();
		}
	}

	/**
	 * 复制文件夹
	 * @param oldPath
	 * @param newPath
	 */
	public void copyFolder(String oldPath, String newPath) {
		try {
			(new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if(oldPath.endsWith(File.separator)){
					temp = new File(oldPath+file[i]);
				}
				else{
					temp = new File(oldPath+File.separator+file[i]);
				}
				if(temp.isFile()){
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath + "/" +
							(temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ( (len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if(temp.isDirectory()){//如果是子文件夹
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		}
		catch (Exception e) {
			System.out.println("复制整个文件夹内容操作出错");
			e.printStackTrace();
		}
	}

	/**
	 * 清空文件内容
	 */
	public void clearData(String path) {
		try {
			FileWriter fw = new FileWriter(fatherPath + path + ".txt", false);
			fw.write("");
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
