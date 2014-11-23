/**
 * 数据层操作的公共类
 * @author Vboar
 * @date 2014/11/15
 */

package data.dataioutility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DataIOUtility {
	
	private String fatherPath = "data/currentdata/";
	
	private String path;
	
	public DataIOUtility(String path) {
		this.path = path;
	}

	/**
	 * 将数据写入文件（覆盖）
	 * @param lists
	 * @param path
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
	 * @param lists
	 * @param path
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
	 * @param path
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
	
}
