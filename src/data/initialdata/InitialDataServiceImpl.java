/**
 * 期初建账数据层操作的实现
 * @author Vboar
 * @date 2014/11/15
 */

package data.initialdata;

import data.dataioutility.DataIOUtility;
import dataservice.initialdataservice.InitialDataService;
import po.InitialPO;

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class InitialDataServiceImpl extends UnicastRemoteObject implements InitialDataService {

	private static final long serialVersionUID = 1L;

	/**
	 * 子路径
	 */
	private String path = "initial";

	/**
	 * 通用类实例
	 */
	private DataIOUtility d;

	/**
	 * 构造方法
	 * @throws RemoteException
	 */
	public InitialDataServiceImpl() throws RemoteException {
		super();
		d = new DataIOUtility(path);
	}
	
	/**
	 * 增加一个PO对象
	 */
	@Override
	public void insert(InitialPO po) throws RemoteException {
		print();
		d.writeDataAdd(this.poToString(po));
		// 保存currentyear-INI
		String path1 = "data/" + po.getId();
		File f1 = new File(path1);
		f1.mkdirs();
		d.copyFile("data/currentdata/category.txt", path1 + "/category.txt");
		d.copyFile("data/currentdata/commodity.txt", path1 + "/commodity.txt");
		d.copyFile("data/currentdata/account.txt", path1 + "/account.txt");
		d.copyFile("data/currentdata/customer.txt", path1 + "/customer.txt");
		// 重写无关文件
		d.clearData("cash");
		d.clearData("customergift");
		d.clearData("exception");
		d.clearData("log");
		d.clearData("message");
		d.clearData("payment");
		d.clearData("present");
		d.clearData("purchase");
		d.clearData("sale");
		d.clearData("specialoffer");
		d.clearData("stock");
		d.clearData("system");
		d.clearData("totalgift");
		d.clearData("warning");
	}

	/**
	 * 增加期末账PO对象和保存期末账
	 * @throws RemoteException
	 */
	@Override
	public void saveEnd() throws RemoteException {
		// 复制currentdata为oldyear-END
		ArrayList<InitialPO> list = show();
		String oldYear = list.get(list.size() - 1).getId().substring(0, 4);
		String path1 = "data/" + oldYear + "-END";
		File f1 = new File(path1);
		f1.mkdirs();
		d.copyFolder("data/currentdata", path1);
		d.writeDataAdd(this.poToString(new InitialPO(oldYear + "-END", oldYear + "年期末账")));
	}

	/**
	 * 根据ID模糊查找PO对象
	 */
	@Override
	public ArrayList<InitialPO> findById(String id) throws RemoteException {
		print();
		ArrayList<InitialPO> tLists = this.stringToPoAll(d.readData());
		ArrayList<InitialPO> lists = new ArrayList<InitialPO>();
		for(InitialPO po: tLists) {
			if(po.getId().contains(id)) {
				lists.add(po);
			}
		}
		return lists;
	}

	/**
	 * 根据ID准确查找PO对象
	 */
	@Override
	public InitialPO getById(String id) throws RemoteException {
		print();
		ArrayList<InitialPO> lists = this.stringToPoAll(d.readData());
		for(InitialPO po: lists) {
			if(id.equals(po.getId())) {
				return po;
			}
		}
		return null;
	}
	
	/**
	 * 返回所有PO对象
	 */
	@Override
	public ArrayList<InitialPO> show() throws RemoteException {
		print();
		return this.stringToPoAll(d.readData());
	}
	
	/**
	 * 将一个PO对象转化为String
	 * @param po
	 * @return
	 */
	private String poToString(InitialPO po) {
		return po.getId() + ";" + po.getName() + ";";
	}
	
	/**
	 * 将一个String转化为PO对象
	 * @param s
	 * @return
	 */
	private InitialPO stringToPo(String s) {
		String[] strs = s.split(";");
		return new InitialPO(strs[0], strs[1]);
	}
	
	/**
	 * 将多个String转化为PO对象
	 * @param strs
	 * @return
	 */
	private ArrayList<InitialPO> stringToPoAll(ArrayList<String> strs) {
		ArrayList<InitialPO> lists = new ArrayList<InitialPO>();
		for(String s: strs) {
			lists.add(this.stringToPo(s));
		}
		return lists;
	}

	/**
	 * 输出执行的类名和方法名
	 */
	private void print() {
		System.out.println(Thread.currentThread().getStackTrace()[1].getClassName() + ": executing " +
				Thread.currentThread().getStackTrace()[2].getMethodName());
	}

}
